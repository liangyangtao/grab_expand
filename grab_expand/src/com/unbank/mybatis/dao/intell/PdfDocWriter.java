package com.unbank.mybatis.dao.intell;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.unbank.classify.entity.PtfDoc;
import com.unbank.mybatis.entity.SQLAdapter;
import com.unbank.mybatis.factory.DynamicConnectionFactory;
import com.unbank.mybatis.mapper.SQLAdapterMapper;
import com.unbank.mybatis.maxid.MaxIdFinder;
import com.unbank.pipeline.entity.Information;

public class PdfDocWriter extends MaxIdFinder {

	public void savePdfDoc(PtfDoc ptfDoc, Information information,
			String environment) {
		String temp[] = environment.split("@@@");
		SqlSession sqlSession = DynamicConnectionFactory
				.getInstanceSessionFactory(temp[0]).openSession();
		ptfDoc.setDocId((int) findMaxId("doc_id", "ptf_doc", temp[0]) + 1);

		try {

			SQLAdapterMapper sqlAdapterMapper = sqlSession
					.getMapper(SQLAdapterMapper.class);
			SQLAdapter sqlAdapter = fillpdfDocSQLAdapter(ptfDoc, information);
			sqlAdapterMapper.insertPdfDoc(sqlAdapter);
			if (ptfDoc.getDocId() > 0) {
				sqlAdapter = fillpdfDocTextSQLAdapter(ptfDoc.getDocId(),
						information);
				sqlAdapterMapper.insertPdfDocText(sqlAdapter);
			}
			sqlSession.commit();
		} catch (Exception e) {
			logger.info("插入智能编辑平台doc表出错", e);
			e.printStackTrace();
			sqlSession.rollback();
		} finally {
			sqlSession.close();
		}
	}

	private SQLAdapter fillpdfDocTextSQLAdapter(int docid,
			Information information) {
		SQLAdapter sqlAdapter = new SQLAdapter();
		String sql = " INSERT INTO  ptf_doc_text";
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("doc_id", docid);
		maps.put("text", information.getText());
		sqlAdapter.setSql(sql);
		sqlAdapter.setObj(maps);
		return sqlAdapter;
	}

	private SQLAdapter fillpdfDocSQLAdapter(PtfDoc ptfDoc,
			Information information) {
		SQLAdapter sqlAdapter = new SQLAdapter();
		String sql = "INSERT INTO  ptf_doc";
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("doc_id", ptfDoc.getDocId());
		maps.put("forum_id", ptfDoc.getForumId());
		maps.put("template_id", ptfDoc.getTemplateId());
		maps.put("structure_id", ptfDoc.getStrucutureId());
		maps.put("doc_type", "PASSED");
		maps.put("doc_title", information.getCrawl_title());
		maps.put("doc_brief", information.getCrawl_brief());
		maps.put("doc_views", 0);
		maps.put("doc_edit_time", new Date());
		maps.put("display_order", 0);
		maps.put("deny", 0);
		maps.put("file_index", 0);
		maps.put("c_id", 1);
		maps.put("c_name", "系统");
		maps.put("c_date", new Date());
		maps.put("c_ip", "12711");
		maps.put("flag", 0);
		maps.put("flag_ubk", 0);
		maps.put("flag_banker", 2);
		maps.put("web_name", information.getWeb_name());
		maps.put("url", information.getUrl());
		sqlAdapter.setSql(sql);
		sqlAdapter.setObj(maps);
		return sqlAdapter;
	}

}
