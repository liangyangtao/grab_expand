package com.unbank.distribute.filter;

import java.util.HashMap;

public class DistributeFilterLocator {
	private static DistributeFilterLocator filterLocator = new DistributeFilterLocator();
	private HashMap<String, DistributeBaseFilter> filters = new HashMap<String, DistributeBaseFilter>();

	DistributeBaseFilter baseFilter = new DistributeBaseFilter();

	public DistributeBaseFilter getFilter(String host) {
		if (filters.containsKey(host)) {
			return filters.get(host);
		}
		return baseFilter;
	}

	public void register(String host, DistributeBaseFilter filter) {
		filters.put(host, filter);
	}

	public void unregister(String host) {
		filters.remove(host);
	}

	private DistributeFilterLocator() {
	}

	public static DistributeFilterLocator getInstance() {
		return filterLocator;
	}

}
