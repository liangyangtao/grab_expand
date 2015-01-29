package com.unbank.mybatis.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;

import com.unbank.mybatis.entity.SQLAdapter;
import com.unbank.mybatis.factory.DynamicConnectionFactory;
import com.unbank.mybatis.mapper.SQLAdapterMapper;
import com.unbank.pipeline.entity.Information;

public class InformationReader {
	private static Log logger = LogFactory.getLog(InformationReader.class);

	public List<Information> readInformationByFileIndexAndTask(
			Integer file_index, Integer task, Integer num) {
		List<Information> informations = null;
		SqlSession sqlSession = DynamicConnectionFactory
				.getInstanceSessionFactory("development").openSession();

		try {
			SQLAdapterMapper sqlAdapterMapper = sqlSession
					.getMapper(SQLAdapterMapper.class);
			SQLAdapter sqlAdapter = fillFileidnexAndTaskSQLAdapter(file_index,
					task, num);
			informations = sqlAdapterMapper
					.selectInformationByTaskAndFieldIndex(sqlAdapter);
			sqlSession.commit();
		} catch (Exception e) {
			logger.info("", e);
		} finally {
			sqlSession.close();
		}
		return informations;

	}

	private SQLAdapter fillFileidnexAndTaskSQLAdapter(Integer file_index,
			Integer task, Integer num) {
		SQLAdapter sqlAdapter = new SQLAdapter();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer
				.append("SELECT ptf_crawl.* , ptf_crawl_text.text from ptf_crawl INNER JOIN ptf_crawl_text on ptf_crawl.crawl_id = ptf_crawl_text.crawl_id");
		if (file_index >= 0) {
			sqlBuffer.append(" and ptf_crawl.file_index =" + file_index);
		}
		if (task >= 0) {
			sqlBuffer.append(" and ptf_crawl.task =" + task);
		}
		sqlBuffer.append(" order by ptf_crawl.crawl_id desc limit " + num);
		sqlAdapter.setSql(sqlBuffer.toString());
		return sqlAdapter;
	}

}
