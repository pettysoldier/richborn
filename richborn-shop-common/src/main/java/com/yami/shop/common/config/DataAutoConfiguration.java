//package com.yami.shop.common.config;
//
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//
///**
// * @author ly
// * @desc
// * @date 2021/7/28
// */
//@Configuration
//public class DataAutoConfiguration {
//
//	@Bean("shops")
//	@ConfigurationProperties(prefix = "spring.datasource.shops")
//	public DataSource masterDataSource(){
//		return DataSourceBuilder.create().build();
//	}
//
//
//}
