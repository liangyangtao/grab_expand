package com.unbank.mybatis.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;

import com.unbank.mybatis.entity.SQLAdapter;
import com.unbank.mybatis.factory.DynamicConnectionFactory;
import com.unbank.mybatis.mapper.SQLAdapterMapper;
import com.unbank.pipeline.entity.Information;

public class InformationModifier {
	private static Log logger = LogFactory.getLog(InformationModifier.class);

	public void updateFileIndex(List<Information> informations) {
		List<Integer> informationids = new ArrayList<Integer>();
		SqlSession sqlSession = DynamicConnectionFactory
				.getInstanceSessionFactory("development").openSession();
		try {
			for (Information information : informations) {
				informationids.add(information.getCrawl_id());
			}
			updateFileIndex(informationids, sqlSession);
			sqlSession.commit(true);
		} catch (Exception e) {
			logger.info("", e);
			sqlSession.rollback();
		} finally {
			sqlSession.close();
		}
		informationids.clear();
	}

	private void updateFileIndex(List<Integer> informationids,
			SqlSession sqlSession) {
		SQLAdapterMapper sqlAdapterMaper = sqlSession
				.getMapper(SQLAdapterMapper.class);
		SQLAdapter sqlAdapter = new SQLAdapter(informationids);
		sqlAdapterMaper.updateFileIndex(sqlAdapter);
	}

	public void updateInformationFileIndex(Information information) {
		String sql = "	update ptf_crawl set file_index="
				+ information.getFile_index() + " where crawl_id   ="
				+ information.getCrawl_id();
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
			logger.error("更新file_index失败", e);
			sqlSession.rollback();
		} finally {
			sqlSession.close();
		}
	}

}
