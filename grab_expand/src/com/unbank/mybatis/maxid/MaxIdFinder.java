package com.unbank.mybatis.maxid;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.unbank.Constants;
import com.unbank.mybatis.dao.IDGen;

public class MaxIdFinder implements IDGen {
	public static Log logger = LogFactory.getLog(MaxIdFinder.class);

	@Override
	public long findMaxId(String idName, String tableName, String environment) {
		synchronized (this) {
			HttpURLConnection connection = null;
			try {
				String ticket = "";// 登录凭证
				String url_str = "http://"+Constants.IDIP+"/ubkintell/platform/getPtfDocID.action";// 获取用户认证的帐号URL
				String ticket_url = url_str + ticket;
				URL url = new URL(ticket_url);
				 connection = (HttpURLConnection) url
						.openConnection();
				connection.connect();
				connection.setConnectTimeout(30000);  
				connection.setReadTimeout(30000); 
				int code = connection.getResponseCode();
				InputStream is = connection.getInputStream();
				byte[] response = new byte[is.available()];
				if (code == 404) {
					throw new Exception("认证无效，找不到此次认证的会话信息！");
				}
				if (code == 500) {
					throw new Exception("认证服务器发生内部错误！");
				}
				if (code != 200) {
					throw new Exception("发生其它错误，认证服务器返回 " + code);
				}
				is.read(response);
				is.close();
				if (response == null || response.length == 0) {
					throw new Exception("认证无效，找不到此次认证的会话信息！");
				}
				String userId = new String(response, "GBK");
				userId = StringUtils
						.substringBetween(userId, "\"docId\":", "}");
				return Long.parseLong(userId);
			} catch (Exception e) {
				logger.info("获取智能编辑平台失败", e);
			}finally{
				connection.disconnect();
			}
			return 0;
		}

	}
}
