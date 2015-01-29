package com.unbank.distribute.entity;

import java.util.Map;

public class Platform {

	public String platformId;
	// 表名，
	public Map<String, Map<String,String>> fields;
	public Map<String, String> filters;



	public Map<String, Map<String, String>> getFields() {
		return fields;
	}

	public void setFields(Map<String, Map<String, String>> fields) {
		this.fields = fields;
	}

	public Map<String, String> getFilters() {
		return filters;
	}

	public void setFilters(Map<String, String> filters) {
		this.filters = filters;
	}

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

}
