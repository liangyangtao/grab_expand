package com.unbank.mybatis.mapper;

import java.util.List;

import com.unbank.classify.entity.TempRelation;
import com.unbank.mybatis.entity.SQLAdapter;
import com.unbank.pipeline.entity.Information;

public interface SQLAdapterMapper {

	List<Information> selectInformationByTaskAndFieldIndex(SQLAdapter sqlAdapter);

	int insertInformation(SQLAdapter sqlAdapter);

	void updateFileIndex(SQLAdapter sqlAdapter);

	List<TempRelation> selectTempRelation(SQLAdapter sqlAdapter);

	void insertPdfDoc(SQLAdapter sqlAdapter);

	void insertPdfDocText(SQLAdapter sqlAdapter);

	int selectMaxId(SQLAdapter sqlAdapter);
	
	void executeSQL(SQLAdapter sqlAdapter);
}
