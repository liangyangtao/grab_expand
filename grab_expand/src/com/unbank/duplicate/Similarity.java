package com.unbank.duplicate;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.unbank.Constants;
import com.unbank.duplicate.entity.SimBean;
import com.unbank.duplicate.util.Parameter;
import com.unbank.duplicate.util.Shingling;
import com.unbank.mybatis.dao.InformationReader;
import com.unbank.pipeline.entity.Information;

public class Similarity {
	public static List<SimBean> simHashlist = new ArrayList<SimBean>();
	// 日志
	public static Logger logger = Logger.getLogger(Similarity.class);

	public static void loaddate(Integer fileindex, Integer task, Integer num) {
		simHashlist.clear();
		List<Information> informations = new InformationReader()
				.readInformationByFileIndexAndTask(fileindex, task, num);
		for (Information information : informations) {
			String title = information.getCrawl_title().replaceAll("[\\pP' ']",
					"");
			String text = information.getText();
			text = delHTMLTag(text);
			// 内容去掉标点、空格、回车
			text = text.replaceAll("[\\pP' '\n]", "");

			SimBean sb = new SimBean();
			sb.setId(information.getCrawl_id());
			sb.setTitle(title);

			Set<String> mergedSet = new HashSet<String>();
			mergedSet.addAll(Shingling.getNGramList(text,
					Parameter.shinglingwordsvalue));
			sb.setText(mergedSet);

			simHashlist.add(sb);
		}
		informations.clear();
	}

	private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
	private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
	private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

	public static String delHTMLTag(String htmlStr) {
		Pattern p_script = Pattern.compile(regEx_script,
				Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll(""); // 过滤script标签

		Pattern p_style = Pattern
				.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(htmlStr);
		htmlStr = m_style.replaceAll(""); // 过滤style标签

		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);
		htmlStr = m_html.replaceAll(""); // 过滤html标签

		return htmlStr.trim(); // 返回文本字符串
	}

	/**
	 * 两个字符串的相同率
	 */
	public static float RSAC(String str1, String str2) {
		List<String> list = new ArrayList<String>();
		for (int k = 0; k < str1.length(); k++) {
			for (int j = 0; j < str2.length(); j++) {
				if (str1.charAt(k) == str2.charAt(j)
						&& !isExist(list, str1.substring(k, k + 1))) {
					list.add(str1.substring(k, k + 1));
					break;
				}
			}
		}

		float size = (float) (list.size() * 2)
				/ (str1.length() + str2.length());
		DecimalFormat df = new DecimalFormat("0.00");// 格式化小数，不足的补0
		String ratiostr = df.format(size);// 返回的是String类型的
		float ratio = Float.parseFloat(ratiostr);
		return ratio;
	}

	/**
	 * 是否存在相同
	 */
	private static boolean isExist(List<String> list, String dest) {
		for (String s : list) {
			if (dest.equals(s))
				return true;
		}
		return false;
	}

	public static void panchong(Information information) {
		try {
			Integer websiteId = information.getWebsite_id();
			if (Constants.WHITELIST.contains(websiteId + "")) {
				information.setFile_index((byte) 0);
				return;
			}

			String title = information.getCrawl_title();
			String text = information.getText();
			title = title.replaceAll("[\\pP' ']", "");
			text = delHTMLTag(text);
			text = text.replaceAll("[\\pP' '\n]", "");
			boolean stopflag = false;
			SimBean sb = new SimBean();
			sb.setId(information.getCrawl_id());
			sb.setTitle(title);
			Set<String> mergedSet = new HashSet<String>();
			mergedSet.addAll(Shingling.getNGramList(text,
					Parameter.shinglingwordsvalue));
			sb.setText(mergedSet);
			SimBean sb1 = null;
			double bijiaozhi = 0;
			for (int i = 0; i < simHashlist.size(); i++) {
				sb1 = simHashlist.get(i);
				// 标题相同字数打到20%可以比较
				if (RSAC(sb1.getTitle(), sb.getTitle()) >= 0.2) {

					bijiaozhi = Shingling.jaccardIndex(sb1.getText(),
							sb.getText());
					// 相似比例打到阀值0.3视为相同
					if (bijiaozhi > 0.3) {
						stopflag = true;
						break;
					}
				}
				if (sb1.getTitle().equals(sb.getTitle())) {
					bijiaozhi = 10;
					stopflag = true;
					break;
				}
			}

			if (stopflag) {
				information.setFile_index((byte) 8);
			} else {
				information.setFile_index((byte) 0);
				// 始终保持比较源3万条数据
				if (simHashlist.size() >= 1) {
					simHashlist.remove(0);
				}
				simHashlist.add(sb);
			}

		} catch (Exception e) {
			logger.info("同步数据出错", e);
		}

	}
}
