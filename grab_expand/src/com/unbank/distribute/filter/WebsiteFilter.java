package com.unbank.distribute.filter;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unbank.pipeline.entity.Information;

@Service
public class WebsiteFilter extends DistributeBaseFilter {
	private String domain = "website";

	public WebsiteFilter() {
		DistributeFilterLocator.getInstance().register(domain, this);
	}

	@Override
	public boolean isPass(Information information, List<String> values) {
		if (values.contains(information.getWebsite_id() + "")) {
			System.out.println("不需要的WebSITEID" + information.getWebsite_id());
			return true;
		}
		return false;
	}

}
