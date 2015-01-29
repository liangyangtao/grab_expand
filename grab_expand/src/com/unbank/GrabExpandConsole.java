package com.unbank;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.unbank.classify.InformationClassify;
import com.unbank.duplicate.Similarity;
import com.unbank.fetch.Fetcher;
import com.unbank.fetch.HttpClientBuilder;
import com.unbank.keyword.InformationKeywordExtractor;
import com.unbank.pipeline.builder.ArticleBuilderFactory;
import com.unbank.pipeline.builder.ChainedArticleBuilder;
import com.unbank.pipeline.builder.ClassifyBuilder;
import com.unbank.pipeline.builder.DistributeBuilder;
import com.unbank.pipeline.builder.KeywordBuilder;
import com.unbank.pipeline.builder.UniqBuilder;
import com.unbank.pipeline.queue.InformationConsume;
import com.unbank.pipeline.queue.InformationProduct;

public class GrabExpandConsole {

	private static Log logger = LogFactory.getLog(GrabExpandConsole.class);

	static {
		// 启动日志
		try {
			PropertyConfigurator.configure(GrabExpandConsole.class
					.getClassLoader().getResource("").toURI().getPath()
					+ "log4j.properties");
			logger.info("---日志系统启动成功---");
		} catch (Exception e) {
			logger.error("日志系统启动失败:", e);
		}
	}

	static ChainedArticleBuilder chainedArticleBuilder;

	public static ChainedArticleBuilder buildArticleChainedTask1() {
		ChainedArticleBuilder chainedArticleBuilder = new ChainedArticleBuilder();
		ArticleBuilderFactory articleBuilderFactory = ArticleBuilderFactory
				.getInstance();
		UniqBuilder uniqBuilder = new UniqBuilder();
		DistributeBuilder distributeBuilder = new DistributeBuilder();
		// 注意顺序，别还没去重就同步了
		articleBuilderFactory.register(uniqBuilder);
		articleBuilderFactory.register(distributeBuilder);
		chainedArticleBuilder.addBuilders(articleBuilderFactory.getBuilders());
		return chainedArticleBuilder;
	}

	public static ChainedArticleBuilder buildArticleChainedTask2() {

		ChainedArticleBuilder chainedArticleBuilder = new ChainedArticleBuilder();
		ArticleBuilderFactory articleBuilderFactory = ArticleBuilderFactory
				.getInstance();
		// 去重
		UniqBuilder uniqBuilder = new UniqBuilder();

		// 提取关键词

		KeywordBuilder keywordBuilder = new KeywordBuilder();

		// 分类
		ClassifyBuilder classifyBuilder = new ClassifyBuilder();
		// 同步
		DistributeBuilder distributeBuilder = new DistributeBuilder();
		// 注意顺序，别还没去重就同步了
		articleBuilderFactory.register(uniqBuilder);
		articleBuilderFactory.register(keywordBuilder);
		articleBuilderFactory.register(classifyBuilder);
		articleBuilderFactory.register(distributeBuilder);
		chainedArticleBuilder.addBuilders(articleBuilderFactory.getBuilders());
		return chainedArticleBuilder;
	}

	public static void init() {
		Constants.init();
		Similarity.loaddate(Constants.DUPLICATEFILEINDEX,
				Constants.DUPLICATETASK, Constants.DUPLICATELIMITNUM);
		PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
		BasicCookieStore cookieStore = new BasicCookieStore();
		HttpClientBuilder httpClientBuilder = new HttpClientBuilder(false,
				poolingHttpClientConnectionManager, cookieStore);
		CloseableHttpClient httpClient = httpClientBuilder.getHttpClient();
		Fetcher fetcher = new Fetcher(cookieStore, httpClient);
		InformationKeywordExtractor.fetcher = fetcher;

		if (Constants.CLENTTASK == 1) {
			chainedArticleBuilder = buildArticleChainedTask1();
		} else {
			InformationClassify.init();
			chainedArticleBuilder = buildArticleChainedTask2();
		}

	}

	public static void main(String[] args) {

		new ClassPathXmlApplicationContext(
				new String[] { "applicationContext.xml" });
		init();

		LinkedBlockingQueue<Object> informationQueue = new LinkedBlockingQueue<Object>();
		ExecutorService executor = Executors.newFixedThreadPool(12);
		executor.execute(new InformationProduct(informationQueue));
		for (int i = 0; i < 2; i++) {
			executor.execute(new InformationConsume(informationQueue,
					chainedArticleBuilder));
		}
		executor.shutdown();

	}

}
