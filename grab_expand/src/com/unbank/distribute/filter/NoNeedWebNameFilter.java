package com.unbank.distribute.filter;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unbank.pipeline.entity.Information;

@Service
public class NoNeedWebNameFilter extends DistributeBaseFilter {
	private String domain = "noNeedWebName";

	public NoNeedWebNameFilter() {
		DistributeFilterLocator.getInstance().register(domain, this);
	}

	@Override
	public boolean isPass(Information information, List<String> values) {
		if (values.contains(information.getWeb_name())) {
			return false;
		}
		return true;
	}

}
