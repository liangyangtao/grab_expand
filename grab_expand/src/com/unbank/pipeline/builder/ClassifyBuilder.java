package com.unbank.pipeline.builder;

import com.unbank.classify.InformationClassify;
import com.unbank.pipeline.entity.Information;

public class ClassifyBuilder extends AbstractArticleInfoBuilder {

	@Override
	public void createArticleEntity(Information information) {
		if (information.getFile_index() == 0) {
			new InformationClassify().transInformation(information);
			information.setFile_index((byte) 1);
		}

	}

}
