采集程序同步程序
（一）首先将需要同步的数据库配置到mybatis.xml 中
mybatis.xml 配置的是数据源的链接，
  development 是采集系统的数据库配置
  格式如下：
  	<environment id="development">
			<transactionManager type="jdbc" />
			<dataSource type="POOLED">
				<property name="driver" value="com.mysql.jdbc.Driver" />
				<property name="url"
					value="jdbc:mysql://10.0.0.51:3306/ubk_platform?allowMultiQueries=true" />
				<property name="username" value="user" />
				<property name="password" value="123456" />
			</dataSource>
		</environment>
			<environment id="spidertest">
			<transactionManager type="jdbc" />
			<dataSource type="POOLED">
				<property name="driver" value="com.mysql.jdbc.Driver" />
				<property name="url"
					value="jdbc:mysql://10.0.2.26:3307/ubk_platform?allowMultiQueries=true" />
				<property name="username" value="user" />
				<property name="password" value="123456" />
			</dataSource>
		</environment>
		
（二） 将同步的数据库配置到distribute.xml 中
格式如下: id  和 mybatis.xml 中配置的id 要一致，否则找不到数据库链接的。
    name 节点是对应的表字段， text节点对应的是采集系统的表字段
    
    
  <platform id="spidertest" ip="10.0.2.26">
		<fields>
			<ptf_crawl> <!-- spidertest 的表名  -->
				<crawl_id>crawl_id</crawl_id> 
				<website_id>website_id</website_id>
				<crawl_title>crawl_title</crawl_title>
				<crawl_brief>crawl_brief</crawl_brief>
				<crawl_views>crawl_views</crawl_views>
				<web_name>web_name</web_name>
				<url>url</url>
				<file_index>file_index</file_index>
				<news_time>news_time</news_time>
				<crawl_time>crawl_time</crawl_time>
				<task>task</task>
			</ptf_crawl>
			<ptf_crawl_text>
				<crawl_id>crawl_id</crawl_id>
				<text>text</text>
			</ptf_crawl_text>
			<intell_pdf_doc></intell_pdf_doc> 
		</fields>
	</platform>
		
