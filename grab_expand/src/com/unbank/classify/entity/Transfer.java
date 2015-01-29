package com.unbank.classify.entity;

import com.unbank.pipeline.entity.Information;

public class Transfer extends Information {

	private int folder_id;// 栏目id
	private int doc_id;// bk_doc id
	private int fromid;//

	public int getFolder_id() {
		return folder_id;
	}

	public void setFolder_id(int folder_id) {
		this.folder_id = folder_id;
	}

	public int getDoc_id() {
		return doc_id;
	}

	public void setDoc_id(int doc_id) {
		this.doc_id = doc_id;
	}

	public int getFromid() {
		return fromid;
	}

	public void setFromid(int fromid) {
		this.fromid = fromid;
	}

}
