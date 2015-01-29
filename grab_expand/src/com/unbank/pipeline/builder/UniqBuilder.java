package com.unbank.pipeline.builder;

import com.unbank.duplicate.Similarity;
import com.unbank.pipeline.entity.Information;

public class UniqBuilder extends AbstractArticleInfoBuilder {

	@Override
	public void createArticleEntity(Information information) {
		if (information.getFile_index() == 7) {
			Similarity.panchong(information);
		}

	}
}
