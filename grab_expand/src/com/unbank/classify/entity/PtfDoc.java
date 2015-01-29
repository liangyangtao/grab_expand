package com.unbank.classify.entity;

public class PtfDoc {

	private int docId;

	private int forumId;

	private int templateId;

	private int strucutureId;

	private DocType docType;

	private String docTitle;

	private String docBrief;

	private int docViews;

	private String docEditTime;

	private int deny;

	private String text;

	private String webname;

	private String url;

	private int websiteId;

	private boolean formatdoc;

	private boolean ispreview;

	private int displayOrder;

	private int crawltodoc;

	private String msg;

	private String passornot;
	private long seriesnum;

	private int userId;
	private String docids;
	private String crawlids;

	public String getPassornot() {
		return passornot;
	}

	public void setPassornot(String passornot) {
		this.passornot = passornot;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCrawltodoc() {
		return crawltodoc;
	}

	public void setCrawltodoc(int crawltodoc) {
		this.crawltodoc = crawltodoc;
	}

	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	public boolean isIspreview() {
		return ispreview;
	}

	public void setIspreview(boolean ispreview) {
		this.ispreview = ispreview;
	}

	public boolean getFormatdoc() {
		return formatdoc;
	}

	public void setFormatdoc(boolean formatdoc) {
		this.formatdoc = formatdoc;
	}

	public int getWebsiteId() {
		return websiteId;
	}

	public void setWebsiteId(int websiteId) {
		this.websiteId = websiteId;
	}

	public String getWebname() {
		return webname;
	}

	public void setWebname(String webname) {
		this.webname = webname;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getDocId() {
		return docId;
	}

	public void setDocId(int docId) {
		this.docId = docId;
	}

	public int getForumId() {
		return forumId;
	}

	public void setForumId(int forumId) {
		this.forumId = forumId;
	}

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public int getStrucutureId() {
		return strucutureId;
	}

	public void setStrucutureId(int strucutureId) {
		this.strucutureId = strucutureId;
	}

	public DocType getDocType() {
		return docType;
	}

	public void setDocType(DocType docType) {
		this.docType = docType;
	}

	public String getDocTitle() {
		return docTitle;
	}

	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
	}

	public String getDocBrief() {
		return docBrief;
	}

	public void setDocBrief(String docBrief) {
		this.docBrief = docBrief;
	}

	public int getDocViews() {
		return docViews;
	}

	public void setDocViews(int docViews) {
		this.docViews = docViews;
	}

	public String getDocEditTime() {
		return docEditTime;
	}

	public void setDocEditTime(String docEditTime) {
		this.docEditTime = docEditTime;
	}

	public int getDeny() {
		return deny;
	}

	public void setDeny(int deny) {
		this.deny = deny;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public long getSeriesnum() {
		return seriesnum;
	}

	public void setSeriesnum(long seriesnum) {
		this.seriesnum = seriesnum;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getDocids() {
		return docids;
	}

	public void setDocids(String docids) {
		this.docids = docids;
	}

	public String getCrawlids() {
		return crawlids;
	}

	public void setCrawlids(String crawlids) {
		this.crawlids = crawlids;
	}

}
