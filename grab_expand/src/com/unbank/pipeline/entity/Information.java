package com.unbank.pipeline.entity;

import java.util.Date;

public class Information {

	private Integer crawl_id;
	private Integer website_id;
	private String crawl_title;
	private String crawl_brief;
	private Integer crawl_views;
	private String web_name;
	private String url;
	private Byte file_index;
	private Date news_time;
	private Date crawl_time;
	private Byte task;
	private String text;
	private Object object;

	public Integer getCrawl_id() {
		return crawl_id;
	}

	public void setCrawl_id(Integer crawl_id) {
		this.crawl_id = crawl_id;
	}

	public Integer getWebsite_id() {
		return website_id;
	}

	public void setWebsite_id(Integer website_id) {
		this.website_id = website_id;
	}

	public String getCrawl_title() {
		return crawl_title;
	}

	public void setCrawl_title(String crawl_title) {
		this.crawl_title = crawl_title;
	}

	public String getCrawl_brief() {
		return crawl_brief;
	}

	public void setCrawl_brief(String crawl_brief) {
		this.crawl_brief = crawl_brief;
	}

	public Integer getCrawl_views() {
		return crawl_views;
	}

	public void setCrawl_views(Integer crawl_views) {
		this.crawl_views = crawl_views;
	}

	public String getWeb_name() {
		return web_name;
	}

	public void setWeb_name(String web_name) {
		this.web_name = web_name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Byte getFile_index() {
		return file_index;
	}

	public void setFile_index(Byte file_index) {
		this.file_index = file_index;
	}

	public Date getNews_time() {
		return news_time;
	}

	public void setNews_time(Date news_time) {
		this.news_time = news_time;
	}

	public Date getCrawl_time() {
		return crawl_time;
	}

	public void setCrawl_time(Date crawl_time) {
		this.crawl_time = crawl_time;
	}

	public Byte getTask() {
		return task;
	}

	public void setTask(Byte task) {
		this.task = task;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

}
