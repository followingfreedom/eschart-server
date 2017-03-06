package com.wy.escharts.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.postgresql.util.PGobject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Created by wangyang on 2017/2/28.
 */

@Configuration
@MapperScan("com.wy.eschart.model")
public class DataSourceConfiguration {

    @Bean
    public ObjectMapper objectMapper(){
        return  new ObjectMapper();
    }

    @Bean
    public DataSourceTransactionManager transactionManager(){
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        return sessionFactory.getObject();
    }


    @Bean
    public DataSource dataSource() {
       DriverManagerDataSource ds = new DriverManagerDataSource();
       ds.setDriverClassName("org.postgresql.Driver");
       ds.setUrl("jdbc:postgresql://127.0.0.1:5432/eschart");
       ds.setUsername("eschart");
       ds.setPassword("eschart");
       return ds;
    }


}
