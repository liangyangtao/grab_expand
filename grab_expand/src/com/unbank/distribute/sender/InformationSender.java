package com.unbank.distribute.sender;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.unbank.classify.entity.PtfDoc;
import com.unbank.distribute.entity.Platform;
import com.unbank.distribute.fill.PlatformFiller;
import com.unbank.distribute.filter.DistributeFilter;
import com.unbank.mybatis.dao.InformationWriter;
import com.unbank.mybatis.dao.intell.PdfDocWriter;
import com.unbank.pipeline.entity.Information;

public class InformationSender {
	private static Log logger = LogFactory.getLog(InformationSender.class);

	public void sendInformation(Information information) {

		Map<String, Object> informationMap = fillInformation(information);
		List<Platform> platforms = new PlatformFiller().parsePlatformData();
		for (Platform platform : platforms) {
			String environment = platform.getPlatformId();

			if (information.getTask() == 1) {
				if (environment.contains("intell")) {
				} else {
					continue;
				}

			}

			try {
				insertInformation(informationMap, platform, information);

			} catch (Exception e) {
				logger.info("", e);
				logger.info(information.getCrawl_id() + "   在    "
						+ platform.getPlatformId() + "    平台同步失败");
				continue;
			}
		}
		platforms.clear();
		informationMap.clear();

	}

	private void insertInformation(Map<String, Object> informationMap,
			Platform platform, Information information) {

		DistributeFilter distributeFilter = new DistributeFilter();
		boolean ispass = distributeFilter.checkInformation(platform,
				information);
		if (!ispass) {
			return;
		}
		
		String environment = platform.getPlatformId();
		Iterator<String> iterator = platform.getFields().keySet().iterator();
		Map<String, Map<String, Object>> tables = new HashMap<String, Map<String, Object>>();
		boolean isNotIntell = true;
		while (iterator.hasNext()) {
			String tableName = iterator.next();
			if (tableName.equals("intell_pdf_doc")) {
				isNotIntell = false;
				if (information.getObject() != null) {
					new PdfDocWriter().savePdfDoc(
							(PtfDoc) information.getObject(), information,
							environment);
				}
				continue;
			}
			StringBuffer tableSql = new StringBuffer();
			Map<String, Object> objects = new HashMap<String, Object>();
			tableSql.append("insert into " + tableName);
			Map<String, String> fields = platform.getFields().get(tableName);
			Iterator<String> tableiteIterator = fields.keySet().iterator();
			while (tableiteIterator.hasNext()) {
				String fieldName = tableiteIterator.next();
				String fieldValue = fields.get(fieldName);
				if (fieldValue.equals("crawl_id")) {
					environment = environment + "@@@" + fieldName;
					continue;
				}
				objects.put(fieldName, informationMap.get(fieldValue));
			}
			tables.put(tableSql.toString(), objects);
		}
		if (isNotIntell) {
			new InformationWriter().insertInformation(tables, environment);
		}

		tables.clear();
	}

	private Map<String, Object> fillInformation(Information information) {
		Map<String, Object> informationMap = new HashMap<String, Object>();
		informationMap.put("crawl_brief", information.getCrawl_brief());
		informationMap.put("crawl_id", information.getCrawl_id());
		informationMap.put("crawl_title", information.getCrawl_title());
		informationMap.put("crawl_views", information.getCrawl_views());
		informationMap.put("crawl_time", information.getCrawl_time());
		informationMap.put("file_index", information.getFile_index());
		informationMap.put("news_time", information.getNews_time());
		informationMap.put("task", information.getTask());
		informationMap.put("text", information.getText());
		informationMap.put("url", information.getUrl());
		informationMap.put("web_name", information.getWeb_name());
		informationMap.put("website_id", information.getWebsite_id());
		informationMap.put("className", information.getObject());
		return informationMap;
	}

}
