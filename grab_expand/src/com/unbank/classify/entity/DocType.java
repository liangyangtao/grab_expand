package com.unbank.classify.entity;

public enum DocType {

	SUBMITTED("已提交"), PASSED("审核通过"), FAILED("未审核通过"), SAVED("已保存");

	private final String name;

	private DocType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
