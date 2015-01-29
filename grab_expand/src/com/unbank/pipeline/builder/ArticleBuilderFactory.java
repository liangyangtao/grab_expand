package com.unbank.pipeline.builder;

import java.util.ArrayList;
import java.util.List;

public class ArticleBuilderFactory {

	static ArticleBuilderFactory articleBuilderFactory = new ArticleBuilderFactory();

	private static List<ArticleBuilder> builders = new ArrayList<ArticleBuilder>();

	public static ArticleBuilderFactory getInstance() {
		return articleBuilderFactory;
	}

	public void register(ArticleBuilder bulider) {
		builders.add(bulider);
	}

	public  List<ArticleBuilder> getBuilders() {
		return builders;
	}


}
