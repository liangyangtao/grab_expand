package com.unbank.pipeline.builder;

import com.unbank.distribute.sender.InformationSender;
import com.unbank.pipeline.entity.Information;

public class DistributeBuilder extends AbstractArticleInfoBuilder {

	@Override
	public void createArticleEntity(Information information) {
		if (information.getFile_index() == 1) {
			information.setFile_index((byte) 0);
			new InformationSender().sendInformation(information);
			information.setFile_index((byte) 1);
		}

	}
}
