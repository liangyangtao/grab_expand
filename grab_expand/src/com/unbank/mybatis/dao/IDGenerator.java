package com.unbank.mybatis.dao;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 
 * @author quzile
 * 
 */
public class IDGenerator {

	/**
	 * Use volatile to force JVM Do Not Reordering Instruction!
	 */
	private static volatile Map<String, AtomicLong> generator = new ConcurrentHashMap<String, AtomicLong>();

	/**
	 * double check, up to version jdk1.5!
	 * 
	 * @param tablename
	 * @return
	 */
	public static long generateSerialNumber(String idName, String tableName,
			String environment, IDGen gen) {
		if (!generator.containsKey(tableName))
			synchronized (generator) {
				if (!generator.containsKey(tableName))
					generator.put(
							tableName,
							new AtomicLong(gen.findMaxId(idName, tableName,
									environment) + 1));
			}
		return generator.get(tableName).getAndIncrement();
	}

}
