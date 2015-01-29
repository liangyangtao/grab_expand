package com.unbank.distribute.filter;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unbank.pipeline.entity.Information;

@Service
public class KeywordFilter extends DistributeBaseFilter {
	private String domain = "keyword";

	public KeywordFilter() {
		DistributeFilterLocator.getInstance().register(domain, this);
	}

	@Override
	public boolean isPass(Information information, List<String> values) {
//		if (values.contains(information.getWeb_name())) {
//			return true;
//		}
//		如果包含返回ture
		return true;
	}

}
