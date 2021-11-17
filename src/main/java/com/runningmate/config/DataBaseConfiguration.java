package com.runningmate.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;


@Configuration
public class DataBaseConfiguration {

    @Bean(name="mysqlDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource mysqlDataSource(){
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "mysqlSessionFactory")
    public SqlSessionFactory mysqlSessionFactory(@Qualifier("mysqlDataSource") DataSource mysqlDataSource,
                                                 ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(mysqlDataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mapper/mysql/**.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "mysqlSessionTemplate")
    public SqlSessionTemplate MariaDBSqlSessionTemplate(@Qualifier("mysqlSessionFactory") SqlSessionFactory mysqlSessionFactory) {
        return new SqlSessionTemplate(mysqlSessionFactory);
    }


}
