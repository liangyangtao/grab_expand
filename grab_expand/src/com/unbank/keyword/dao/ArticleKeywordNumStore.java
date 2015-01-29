package com.unbank.keyword.dao;

import org.apache.ibatis.session.SqlSession;

import com.unbank.mybatis.entity.ArticleKeywordNum;
import com.unbank.mybatis.factory.DynamicConnectionFactory;
import com.unbank.mybatis.mapper.ArticleKeywordNumMapper;

public class ArticleKeywordNumStore {

	public void saveArticleKeywordNum(ArticleKeywordNum articleKeywordNum) {

//		String sql = "INSERT INTO `ptf_crawl_keyword_num`(crawl_time,keyword) VALUES(curdate(),#{keyword}) ON DUPLICATE KEY UPDATE num = num +1";

		SqlSession sqlSession = DynamicConnectionFactory
				.getInstanceSessionFactory("development").openSession();
		try {
			ArticleKeywordNumMapper articleKeywordNumMapper = sqlSession
					.getMapper(ArticleKeywordNumMapper.class);
			articleKeywordNumMapper.insertSelective(articleKeywordNum);
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
			sqlSession.rollback();
		} finally {
			sqlSession.close();
		}

	}

}
