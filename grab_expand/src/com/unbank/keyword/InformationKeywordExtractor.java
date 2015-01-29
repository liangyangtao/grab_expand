package com.unbank.keyword;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;

import com.unbank.fetch.Fetcher;
import com.unbank.keyword.dao.ArticleKeywordNumStore;
import com.unbank.keyword.dao.ArticleKeywordStore;
import com.unbank.mybatis.entity.ArticleKeyword;
import com.unbank.mybatis.entity.ArticleKeywordNum;
import com.unbank.pipeline.entity.Information;

public class InformationKeywordExtractor {
	public static Fetcher fetcher;

	public static void extractKeyword(Information information) {
		String title = information.getCrawl_title();
		String content = information.getText();
		String url = "http://"+com.unbank.Constants.KEYWORDIP+"/keywords/extract.htm";
		Map<String, String> params = new HashMap<String, String>();
		params.put("title", title);
		params.put("content", content);
		String html = fetcher.post(url, params, null, "utf-8");

		JSONArray jsonArray = JSONArray.fromObject(html);

		for (Object object : jsonArray) {
			try {
				String keyword = (String) object;
				if (keyword.trim().isEmpty()) {
					continue;
				}
				ArticleKeyword articleKeyword = new ArticleKeyword();
				articleKeyword.setCrawlId(information.getCrawl_id());
				articleKeyword.setCrawlTime(information.getCrawl_time());
				articleKeyword.setKeyword(keyword.trim());
				ArticleKeywordStore articleKeywordStore = new ArticleKeywordStore();
				articleKeywordStore.saveArticleKeyword(articleKeyword);
				ArticleKeywordNum articleKeywordNum = new ArticleKeywordNum();
				articleKeywordNum.setKeyword(keyword.trim());
				ArticleKeywordNumStore articleKeywordNumStore = new ArticleKeywordNumStore();
				articleKeywordNumStore.saveArticleKeywordNum(articleKeywordNum);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}

		}

	}

}
