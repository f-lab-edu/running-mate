package com.runningmate.runningmate.common.configuration;

import com.runningmate.runningmate.dataSource.DataSourceType;
import com.runningmate.runningmate.dataSource.ReplicationRoutingDataSource;
import com.runningmate.runningmate.image.domain.entity.ImageStatus;
import com.runningmate.runningmate.project.domain.entity.ProjectApplyStatus;
import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import java.util.Map;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;


@Configuration
public class DataBaseConfiguration {

    @Bean(name = "masterDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder
            .create()
            .type(HikariDataSource.class)
            .build();
    }

    @Bean(name = "slaveDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveDataSource() {
        return DataSourceBuilder
            .create()
            .type(HikariDataSource.class)
            .build();
    }


    @Bean(name = "routingDataSource")
    public DataSource routingDataSource(
        @Qualifier("masterDataSource") final DataSource masterDataSource,
        @Qualifier("slaveDataSource") final DataSource slaveDataSource) {

        ReplicationRoutingDataSource routingDataSource = new ReplicationRoutingDataSource();

        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceType.Master, masterDataSource);
        targetDataSources.put(DataSourceType.Slave, slaveDataSource);

        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.setDefaultTargetDataSource(masterDataSource);
        return routingDataSource;
    }

    @Primary
    @Bean(name = "routingLazyDataSource")
    public DataSource routingLazyDataSource(@Qualifier("routingDataSource") DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

    @Bean(name = "sessionFactory")
    public SqlSessionFactory sessionFactory(DataSource dataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mapper/**/**.xml"));
        sqlSessionFactoryBean.setTypeHandlers(new TypeHandler[]{new ImageStatus.TypeHandler(), new ProjectApplyStatus.TypeHandler()});
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "sessionTemplate")
    public SqlSessionTemplate sessionTemplate(SqlSessionFactory sessionFactory) {
        sessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);
        return new SqlSessionTemplate(sessionFactory);
    }
}
