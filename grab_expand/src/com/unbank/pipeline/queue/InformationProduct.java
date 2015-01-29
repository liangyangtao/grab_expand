package com.unbank.pipeline.queue;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import com.unbank.Constants;
import com.unbank.mybatis.dao.InformationReader;
import com.unbank.pipeline.entity.Information;

public class InformationProduct extends BaseQueue implements Runnable {
	protected LinkedBlockingQueue<Object> informationQueue;

	public InformationProduct(LinkedBlockingQueue<Object> informationQueue) {
		this.informationQueue = informationQueue;
	}

	@Override
	public void run() {
		while (true) {
			try {
				if (informationQueue.size() <= 0) {
					fillQueue();
				}
				sleeping(1000);
			} catch (Exception e) {
				logger.info("", e);
				continue;
			}

		}
	}

	private void fillQueue() {
		List<Information> informations = new InformationReader()
				.readInformationByFileIndexAndTask(Constants.CLENTFILEINDEX,
						Constants.CLENTTASK, Constants.CLENTLIMITNUM);
		if (informations.size() == 0) {
			sleeping(1000 * 30);
		}
		for (Information information : informations) {
			put(informationQueue, information);
		}

		informations.clear();
	}

}
