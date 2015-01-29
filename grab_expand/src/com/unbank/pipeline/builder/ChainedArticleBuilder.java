package com.unbank.pipeline.builder;

import java.util.LinkedList;
import java.util.List;

import com.unbank.pipeline.entity.Information;

public class ChainedArticleBuilder extends AbstractArticleInfoBuilder {

	private LinkedList<ArticleBuilder> builders;

	public ChainedArticleBuilder() {
		builders = new LinkedList<ArticleBuilder>();
	}

	public void addBuilder(ArticleBuilder builder) {
		builders.add(builder);
	}

	public void addBuilders(List<ArticleBuilder> builder) {
		builders.addAll(builder);
	};

	public void createArticleEntity(Information information) {
		if (builders.size() >= 1) {
			for (ArticleBuilder builder : builders) {
				builder.createArticleEntity(information);
			}
		}
	}

}
