package com.unbank.keyword.dao;

import org.apache.ibatis.session.SqlSession;

import com.unbank.mybatis.entity.ArticleKeyword;
import com.unbank.mybatis.factory.DynamicConnectionFactory;
import com.unbank.mybatis.mapper.ArticleKeywordMapper;

public class ArticleKeywordStore {

	public void saveArticleKeyword(ArticleKeyword articleKeyword) {
		SqlSession sqlSession = DynamicConnectionFactory
				.getInstanceSessionFactory("development").openSession();
		try {
			ArticleKeywordMapper articleKeywordMapper = sqlSession
					.getMapper(ArticleKeywordMapper.class);
			articleKeywordMapper.insertSelective(articleKeyword);
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
			sqlSession.rollback();
		} finally {
			sqlSession.close();
		}

	}

}
