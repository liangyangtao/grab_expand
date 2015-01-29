package com.unbank.distribute.filter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.unbank.distribute.entity.Platform;
import com.unbank.pipeline.entity.Information;

public class DistributeFilter {

	public boolean checkInformation(Platform platform, Information information) {
		Map<String, String> filter = platform.getFilters();
		if (filter == null) {
			return true;
		}
		Set<String> keyset = filter.keySet();
		boolean pass = true;
		for (String string : keyset) {
			String value = filter.get(string);
			if (value == null || value.isEmpty()) {
				continue;
			}
			List<String> values = Arrays.asList(value.split(","));
			DistributeBaseFilter distributeBaseFilter = DistributeFilterLocator
					.getInstance().getFilter(string);
			pass = distributeBaseFilter.isPass(information, values);
			if (!pass) {
				// 如果有条件不符合就不保存了。
				break;
			}
		}
		return pass;
	}
}
