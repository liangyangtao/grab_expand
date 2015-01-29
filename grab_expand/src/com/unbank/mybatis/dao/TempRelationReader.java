package com.unbank.mybatis.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;

import com.unbank.classify.entity.TempRelation;
import com.unbank.mybatis.entity.SQLAdapter;
import com.unbank.mybatis.factory.DynamicConnectionFactory;
import com.unbank.mybatis.mapper.SQLAdapterMapper;

public class TempRelationReader {
	private static Log logger = LogFactory.getLog(TempRelationReader.class);

	public List<TempRelation> selectTempRelation() {

		SqlSession sqlSession = DynamicConnectionFactory
				.getInstanceSessionFactory("development").openSession();
		List<TempRelation> tempRelations = null;
		try {
			SQLAdapterMapper sqlAdapterMapper = sqlSession
					.getMapper(SQLAdapterMapper.class);
			SQLAdapter sqlAdapter = fillTempRelationsSQLAdapter();
			tempRelations = sqlAdapterMapper.selectTempRelation(sqlAdapter);
			sqlSession.commit();
		} catch (Exception e) {
			sqlSession.rollback();
		} finally {
			sqlSession.close();
		}

		return tempRelations;
	}

	private SQLAdapter fillTempRelationsSQLAdapter() {
		SQLAdapter sqlAdapter = new SQLAdapter();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer
				.append("SELECT t.classid,t.strucutureid,t.templateid,t.forumid,group_concat(t.webtitle) webtitlelist,t.state   from ptf_class_index as t group by t.strucutureid ");
		sqlAdapter.setSql(sqlBuffer.toString());

		return sqlAdapter;
	}
}
