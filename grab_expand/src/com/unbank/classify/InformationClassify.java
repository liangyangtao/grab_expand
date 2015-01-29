package com.unbank.classify;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.unbank.classify.entity.PtfDoc;
import com.unbank.classify.entity.TempRelation;
import com.unbank.mybatis.dao.TempRelationReader;
import com.unbank.pipeline.entity.Information;

public class InformationClassify {

	public static Map websitMap = new HashMap();
	public static Map classMap = new HashMap();
	public static List<Integer> websitList = new ArrayList<Integer>();
	private static Logger logger = Logger.getLogger(InformationClassify.class);
	private static final QName SERVICE_NAME = new QName(
			"http://cxfinterface.unbank.com/", "AutoclassinterfaceImplService");

	public static void init() {
		List<TempRelation> classlist = new TempRelationReader()
				.selectTempRelation();
		if (classlist != null && classlist.size() > 0) {
			for (TempRelation temp : classlist) {
				if (temp.getState() == 1) {
					// 同步分类
					classMap.put(String.valueOf(temp.getClassid()), temp);
				} else if (temp.getState() == 3) {
					// 过滤不需要分类的网站来源
					websitList.add(temp.getStrucutureid());
					websitMap.put(temp.getStrucutureid(), temp);
				}
			}
		}

	}

	public void transInformation(Information information) {
		boolean isTrue = informationIsClassfy(information);
		if (!isTrue) {
			informationClassfy(information);
		}

	}

	private void informationClassfy(Information information) {
		try {
			URL wsdlURL = AutoclassinterfaceImplService.WSDL_LOCATION;
			AutoclassinterfaceImplService ss = new AutoclassinterfaceImplService(
					wsdlURL, SERVICE_NAME);
			Autoclassinterface port = ss.getAutoclassinterfaceImplPort();
			JSONObject inJson = new JSONObject();
			inJson.put("id", information.getCrawl_id());
			inJson.put("content", information.getText());
			inJson.put("title", information.getCrawl_title());
			JSONObject outJson = null;
			String resulltStr = null;
			resulltStr = port.comparesimilarity(inJson.toString());
			if (resulltStr != null && new JSONObject(resulltStr) != null) {
				outJson = new JSONObject(resulltStr);
				if (outJson != null && "0".equals(outJson.get("errcode"))) {
					if (classMap != null
							&& classMap.containsKey(outJson.get("class"))
							&& classMap.get(outJson.get("class")) != null) {
						// 分词
						PtfDoc ptfDoc = new PtfDoc();
						TempRelation temp = (TempRelation) classMap.get(outJson
								.get("class"));
						ptfDoc.setStrucutureId(temp.getStrucutureid());
						ptfDoc.setTemplateId(temp.getTemplateid());
						ptfDoc.setForumId(temp.getForumid());
						information.setObject(ptfDoc);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private boolean informationIsClassfy(Information information) {
		if (websitMap != null && websitList != null && websitList.size() > 0) {
			TempRelation tempTR = null;
			for (Integer strid : websitList) {
				if (websitMap.containsKey(strid)
						&& websitMap.get(strid) != null) {
					tempTR = (TempRelation) websitMap.get(strid);
					List<String> tempWebSit = Arrays.asList(tempTR
							.getWebtitleList().split(","));
					if (tempWebSit.contains(information.getWebsite_id() + "")) {
						PtfDoc ptfDoc = new PtfDoc();
						ptfDoc.setStrucutureId(tempTR.getStrucutureid());
						ptfDoc.setTemplateId(tempTR.getTemplateid());
						ptfDoc.setForumId(tempTR.getForumid());
						information.setObject(ptfDoc);
						return true;
					}
				}
			}
		}
		return false;
	}

}
