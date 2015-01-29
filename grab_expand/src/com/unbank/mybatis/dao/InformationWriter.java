package com.unbank.mybatis.dao;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;

import com.unbank.mybatis.entity.SQLAdapter;
import com.unbank.mybatis.factory.DynamicConnectionFactory;
import com.unbank.mybatis.mapper.SQLAdapterMapper;
import com.unbank.pipeline.entity.Information;

public class InformationWriter {
	private static Log logger = LogFactory.getLog(InformationWriter.class);

	public void insertInformation(Map<String, Map<String, Object>> tables,
			String environment) {

		String temp[] = environment.split("@@@");
		SqlSession sqlSession = null;
		sqlSession = DynamicConnectionFactory
				.getInstanceSessionFactory(temp[0]).openSession();
		SQLAdapterMapper sqlAdapterMaper = sqlSession
				.getMapper(SQLAdapterMapper.class);
		Iterator<String> iterator = tables.keySet().iterator();
		int id = 0;
		try {
			while (iterator.hasNext()) {
				String key = iterator.next();
				Map<String, Object> objects = tables.get(key);
				SQLAdapter sqlAdapter = new SQLAdapter(key, objects);

				if (id == 0) {
					sqlAdapterMaper.insertInformation(sqlAdapter);
					id = sqlAdapter.getPrikey();
				} else {
					objects.put(temp[1], id);
					sqlAdapterMaper.insertInformation(sqlAdapter);
				}
			}
			sqlSession.commit();
		} catch (Exception e) {
			logger.info("", e);
			sqlSession.rollback();
		} finally {
			sqlSession.close();
		}

	}

	public void insertLog(Information information) {
		executeALLLog(information);
		executeWebLog(information);

	}

	private void executeWebLog(Information information) {
		String sql = "";
		if (information.getFile_index() == 8) {
			sql = "INSERT INTO `ptf_day_num_web_dup`(crawl_time,website_id) VALUES(curdate(),"
					+ information.getWebsite_id()
					+ ") ON DUPLICATE KEY UPDATE num = num +1";
		} else {
			sql = "INSERT INTO `ptf_day_num_web_syn`(crawl_time,website_id) VALUES(curdate(),"
					+ information.getWebsite_id()
					+ ") ON DUPLICATE KEY UPDATE num = num +1";
		}
		executeSQL(sql);

	}

	private void executeALLLog(Information information) {
		String sql = "";
		if (information.getFile_index() == 8) {
			sql = "INSERT INTO `ptf_day_num_dup`(num_id,crawl_time) VALUES(DEFAULT,curdate()) ON DUPLICATE KEY UPDATE num = num +1";
		} else {
			sql = "INSERT INTO `ptf_day_num_syn`(num_id,crawl_time) VALUES(DEFAULT,curdate()) ON DUPLICATE KEY UPDATE num = num +1";
		}
		executeSQL(sql);

	}

	private void executeSQL(String sql) {
		SqlSession sqlSession = DynamicConnectionFactory
				.getInstanceSessionFactory("development").openSession();
		try {
			SQLAdapterMapper sqlAdapterMaper = sqlSession
					.getMapper(SQLAdapterMapper.class);
			SQLAdapter sqlAdapter = new SQLAdapter();
			sqlAdapter.setSql(sql);
			sqlAdapterMaper.executeSQL(sqlAdapter);
			sqlSession.commit();
		} catch (Exception e) {
			logger.error("保存数据源去重条数信息失败", e);
			sqlSession.rollback();
		} finally {
			sqlSession.close();
		}
	}

}
