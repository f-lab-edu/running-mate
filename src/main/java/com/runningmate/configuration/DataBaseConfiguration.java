package com.runningmate.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


@Configuration
public class DataBaseConfiguration {

    @Bean(name="dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource(){
        return DataSourceBuilder
                .create()
                .type(HikariDataSource.class)
                .build();
    }


    @Bean(name = "sessionFactory")
    public SqlSessionFactory sessionFactory(DataSource dataSource,
                                            ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mapper/**/**.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "sessionTemplate")
    public SqlSessionTemplate sessionTemplate(SqlSessionFactory sessionFactory) {
        return new SqlSessionTemplate(sessionFactory);
    }


}
