package com.wuchaooooo.service.zjutsds.config;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

//import com.moxie.cloud.services.tenant.client.TenantManagerClient;

@Configuration
@EnableTransactionManagement
public class DatasourceConfig {
	@Autowired
	private Environment env;

	//生产环境
	@Value("${prod.mysql.url}")
	private String db1Url;
	@Value("${prod.mysql.user}")
	private String db1User;
	@Value("${prod.mysql.pass}")
	private String db1Pass;

//本地环境
//    @Value("${local.mysql.url}")
//    private String db1Url;
//    @Value("${local.mysql.user}")
//    private String db1User;
//    @Value("${local.mysql.pass}")
//    private String db1Pass;

	private static final Logger LOGGER = LoggerFactory.getLogger(DatasourceConfig.class);
	private Map<String, Object> datasourceMap;

	@PostConstruct
	public void init() {
		LOGGER.info("打包时间: {}", env.getProperty("package.time"));

		datasourceMap = new HashMap<>();
		for (Iterator<PropertySource<?>> it = ((AbstractEnvironment) env).getPropertySources().iterator(); it.hasNext();) {
			PropertySource<?> propertySource = it.next();
			this.getPropertiesFromSource(propertySource, datasourceMap);
		}
	}

    @Bean(name="dataSource1")
    public DataSource dataSourceTenant() {
        return this.getDataSource(db1Url, db1User, db1Pass);
    }

    @Bean(name="jdbcTemplate")
    public JdbcTemplate templateProd() {
        return new JdbcTemplate(this.getDataSource(db1Url, db1User, db1Pass));
    }

    private void getPropertiesFromSource(PropertySource<?> propertySource, Map<String, Object> map) {
        if (propertySource instanceof MapPropertySource) {
            for (String key : ((MapPropertySource) propertySource).getPropertyNames()) {
                map.put(key, propertySource.getProperty(key));
            }
        }
        if (propertySource instanceof CompositePropertySource) {
            for (PropertySource<?> s : ((CompositePropertySource) propertySource).getPropertySources()) {
                getPropertiesFromSource(s, map);
            }
        }
    }

    private DataSource getDataSource(String url, String user, String pass) {
        datasourceMap.put(DruidDataSourceFactory.PROP_URL, url);
        datasourceMap.put(DruidDataSourceFactory.PROP_USERNAME, user);
        datasourceMap.put(DruidDataSourceFactory.PROP_PASSWORD, pass);

        try{
            return DruidDataSourceFactory.createDataSource(datasourceMap);
        } catch (Exception e) {
            LOGGER.error("无法获得数据源'{}':'{}'", url, ExceptionUtils.getStackTrace(e));
            throw new RuntimeException("无法获得数据源.");
        }
    }
}