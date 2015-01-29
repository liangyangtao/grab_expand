package com.unbank.pipeline.builder;

import com.unbank.keyword.InformationKeywordExtractor;
import com.unbank.pipeline.entity.Information;

public class KeywordBuilder extends AbstractArticleInfoBuilder {

	@Override
	public void createArticleEntity(Information information) {
		if (information.getFile_index() == 0) {

			InformationKeywordExtractor.extractKeyword(information);
		}

	}
}
