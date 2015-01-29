package com.unbank.mybatis.dao;

/**
 * 所有非自动生成主键的 需要继承该接口
 * 
 * @author Administrator
 * 
 */
public interface IDGen {

	long findMaxId(String idName,String tableName,String environment);

}
