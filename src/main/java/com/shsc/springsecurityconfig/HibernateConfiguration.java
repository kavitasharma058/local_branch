package com.shsc.springsecurityconfig;

import java.util.*;

//import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
 
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
//import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
 
@Configuration
@EnableTransactionManagement
@ComponentScan({ "com.shsc.springsecurityconfig" })
@PropertySource(value = { "classpath:application.properties" })
public class HibernateConfiguration {
 
    @Autowired
    private Environment environment;
 
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[] { "com.shsc.model" });
        sessionFactory.setHibernateProperties(hibernateJpaProperties());
        return sessionFactory;
     }
      
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
        dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
        dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
        return dataSource;
    }

    private Properties hibernateJpaProperties() {
        //HashMap<String, String> properties = new HashMap<>();
    	Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
        properties.put("hibernate.hbm2ddl.import_files", "insert-data.sql");
        properties.put("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");         
        return properties;
      }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
      LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
      entityManagerFactory.setPersistenceUnitName("hibernate-persistence");
      entityManagerFactory.setDataSource(dataSource);
      entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
      entityManagerFactory.setJpaDialect(new HibernateJpaDialect());
      entityManagerFactory.setPackagesToScan("com.shsc.model");
       
      entityManagerFactory.setJpaProperties(hibernateJpaProperties());
      return entityManagerFactory;
    }
    
    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory s) {
       HibernateTransactionManager txManager = new HibernateTransactionManager();
       txManager.setSessionFactory(s);
       return txManager;
    }
    
}
