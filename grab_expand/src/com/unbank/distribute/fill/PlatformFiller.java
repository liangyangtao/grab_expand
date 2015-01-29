package com.unbank.distribute.fill;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

import com.unbank.distribute.entity.Platform;

public class PlatformFiller extends BaseFiller {

	public List<Platform> parsePlatformData() {
		List<Platform> platforms = null;
		try {
			String path = PlatformFiller.class.getClassLoader().getResource("")
					.toURI().getPath();
			platforms = parsePlatformData(path + "distribute.xml");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return platforms;
	}

	public List<Platform> parsePlatformData(String xmlFilePath) {
		Document doc = parse2Document(xmlFilePath);
		Element root = doc.getRootElement();
		List<Platform> platforms = new ArrayList<Platform>();
		for (Iterator f_terator = root.elementIterator(); f_terator.hasNext();) {

			Element platform_element = (Element) f_terator.next();
			String platformId = platform_element.attributeValue("id");
			Platform platform = new Platform();
			platform.setPlatformId(platformId);
			Map<String, String> filters = new HashMap<String, String>();
			for (Iterator attr_iterator = platform_element.elementIterator(); attr_iterator
					.hasNext();) {
				Element attr_Element = (Element) attr_iterator.next();
				if (attr_Element.getName().equals("fields")) {
					Map<String, Map<String, String>> tables = new HashMap<String, Map<String, String>>();
					for (Iterator table_iterator = attr_Element
							.elementIterator(); table_iterator.hasNext();) {
						Element table_Element = (Element) table_iterator.next();
						String tableName = table_Element.getName();
						Map<String, String> fields = new HashMap<String, String>();
						for (Iterator field_iterator = table_Element
								.elementIterator(); field_iterator.hasNext();) {
							Element fieldElement = (Element) field_iterator
									.next();
							fields.put(fieldElement.getName(),
									fieldElement.getTextTrim());
						}
						tables.put(tableName, fields);

					}
					platform.setFields(tables);
				} else if (attr_Element.getName().equals("filter")) {
					Map<String, String> tables = new HashMap<String, String>();
					for (Iterator table_iterator = attr_Element
							.elementIterator(); table_iterator.hasNext();) {
						Element table_Element = (Element) table_iterator.next();
						String tableName = table_Element.getName();
						tables.put(tableName, table_Element.getTextTrim());

					}
					platform.setFilters(tables);
				}

			}
			platforms.add(platform);
		}
		return platforms;

	}

}
