package com.unbank.classify.entity;

public class TempRelation {
	private int classid;
	private int strucutureid;
	private int templateid;
	private int forumid;

	private int webtitle;
	private int folderid;
	private String webtitleList;
	private int state;

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getWebtitleList() {
		return webtitleList;
	}

	public void setWebtitleList(String webtitleList) {
		this.webtitleList = webtitleList;
	}

	public int getWebtitle() {
		return webtitle;
	}

	public void setWebtitle(int webtitle) {
		this.webtitle = webtitle;
	}

	public int getFolderid() {
		return folderid;
	}

	public void setFolderid(int folderid) {
		this.folderid = folderid;
	}

	public int getClassid() {
		return classid;
	}

	public void setClassid(int classid) {
		this.classid = classid;
	}

	public int getStrucutureid() {
		return strucutureid;
	}

	public void setStrucutureid(int strucutureid) {
		this.strucutureid = strucutureid;
	}

	public int getTemplateid() {
		return templateid;
	}

	public void setTemplateid(int templateid) {
		this.templateid = templateid;
	}

	public int getForumid() {
		return forumid;
	}

	public void setForumid(int forumid) {
		this.forumid = forumid;
	}

}
