package com.unbank.pipeline.queue;

import java.util.concurrent.LinkedBlockingQueue;

import com.unbank.mybatis.dao.InformationModifier;
import com.unbank.mybatis.dao.InformationWriter;
import com.unbank.pipeline.builder.ChainedArticleBuilder;
import com.unbank.pipeline.entity.Information;

public class InformationConsume extends BaseQueue implements Runnable {
	protected LinkedBlockingQueue<Object> informationQueue;
	protected ChainedArticleBuilder chainedArticleBuilder;

	public InformationConsume(LinkedBlockingQueue<Object> informationQueue,
			ChainedArticleBuilder chainedArticleBuilder) {
		this.chainedArticleBuilder = chainedArticleBuilder;
		this.informationQueue = informationQueue;
	}

	@Override
	public void run() {
		while (true) {
			try {
				if (informationQueue.size() <= 10000
						&& informationQueue.size() > 0) {

					Information information = null;

					information = (Information) take(informationQueue);

					if (information != null) {
						consumeInformation(information);
					}
				}
				sleeping(5000);
			} catch (Exception e) {
				logger.info("", e);
				continue;
			}

		}
	}

	private void consumeInformation(Information information) {
		chainedArticleBuilder.createArticleEntity(information);
		logger.info(information.getCrawl_id() + " ===================="
				+ information.getFile_index());
		new InformationWriter().insertLog(information);
		new InformationModifier().updateInformationFileIndex(information);
	}

}
