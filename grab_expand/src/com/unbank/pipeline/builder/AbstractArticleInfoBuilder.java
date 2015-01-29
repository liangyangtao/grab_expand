package com.unbank.pipeline.builder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.unbank.pipeline.entity.Information;

public abstract class AbstractArticleInfoBuilder implements ArticleBuilder {
	public static Log logger = LogFactory
			.getLog(AbstractArticleInfoBuilder.class);

	public void createArticleEntity(Information information) {
	}

}
