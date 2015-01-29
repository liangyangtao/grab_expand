package com.unbank;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Constants {
	public static Integer CLENTFILEINDEX;
	public static Integer CLENTTASK;
	public static Integer CLENTLIMITNUM;
	public static Integer DUPLICATEFILEINDEX;
	public static Integer DUPLICATETASK;
	public static Integer DUPLICATELIMITNUM;
	public static String WHITELISTSTR;
	public static String IDIP;
	public static List<String> WHITELIST;

	public static String KEYWORDIP;

	public static void main(String[] args) {
		init();
		System.out.println(CLENTLIMITNUM);
	}

	public static void init() {

		try {
			String path = Constants.class.getClassLoader().getResource("")
					.toURI().getPath();
			parseExpandConfigData(path + "expandConfig.xml");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	private static void parseExpandConfigData(String xmlFilePath) {
		Document doc = parse2Document(xmlFilePath);
		Element root = doc.getRootElement();
		Element duplicateElement = root.element("duplicate");
		DUPLICATEFILEINDEX = Integer.parseInt(duplicateElement
				.elementText("fileindex"));
		DUPLICATETASK = Integer.parseInt(duplicateElement.elementText("task"));
		DUPLICATELIMITNUM = Integer.parseInt(duplicateElement
				.elementText("limitNum"));
		Element clentElement = root.element("clent");
		CLENTFILEINDEX = Integer
				.parseInt(clentElement.elementText("fileindex"));
		CLENTTASK = Integer.parseInt(clentElement.elementText("task"));
		CLENTLIMITNUM = Integer.parseInt(clentElement.elementText("limitNum"));
		IDIP = clentElement.elementText("doc_ip");
		Element uniqElement = root.element("uniq");
		WHITELISTSTR = uniqElement.elementText("white");
		WHITELIST = Arrays.asList(WHITELISTSTR.split(","));

		Element keywordElement = root.element("keyword");
		KEYWORDIP = keywordElement.elementText("keyword_ip");

	}

	public static Document parse2Document(String xmlFilePath) {
		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
			doc = reader.read(new File(xmlFilePath));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return doc;
	}
}
