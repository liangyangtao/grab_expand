package com.unbank.distribute.fill;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class BaseFiller {
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