package com.unbank.distribute.filter;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unbank.pipeline.entity.Information;

@Service
public class NoNeedWebsiteFilter extends DistributeBaseFilter {
	private String domain = "noNeedWebsiteId";

	public NoNeedWebsiteFilter() {
		DistributeFilterLocator.getInstance().register(domain, this);
	}

	@Override
	public boolean isPass(Information information, List<String> values) {
		if (values.contains(information.getWebsite_id() + "")) {
			return false;
		}
		return true;
	}

}
