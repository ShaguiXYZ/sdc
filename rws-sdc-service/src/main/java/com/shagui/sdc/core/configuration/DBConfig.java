package com.shagui.sdc.core.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import lombok.extern.slf4j.Slf4j;


/**
 *  DB EntityManagerFactory config example
 * @author E552226
 *
 */
@Slf4j
//@Configuration
//@EnableJpaRepositories(basePackages = "com.allianz.sdc.repository")
public class DBConfig {
	@Value("${spring.datasource.url}")
	private String dbUrl;

	@Value("${spring.datasource.username}")
	private String dbUser;

	@Value("${spring.datasource.password}")
	private String dbPassword;

	@Value("${spring.datasource.driverClassName}")
	private String dbDriver;

	@Value("${spring.jpa.properties.hibernate.dialect}")
	private String dialect;

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String ddlAuto;

	@Value("${spring.jpa.show-sql}")
	private String showSql;

    @Bean
    DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dbDriver);
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUser);
        dataSource.setPassword(dbPassword);
        log.info("DBConfig - DataSource properties => URL: " + dbUrl + ", User: " + dbUser);
        return dataSource;
    }

    @Bean(name = "entityManagerFactory")
    LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        log.info("DBConfig - try to create LocalContainerEntityManagerFactoryBean");
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        log.info("DBConfig - LocalContainerEntityManagerFactoryBean created");
        entityManagerFactory.setDataSource(dataSource);
        Properties jpaProperties = new Properties();

        entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactory.setPackagesToScan("com.shagui.analysis.model");
        entityManagerFactory.setPersistenceUnitName("sdc-database");
        log.info("DBConfig - entityManagerFactory");

        jpaProperties.setProperty("hibernate.dialect", dialect);
        jpaProperties.setProperty("javax.persistence.sharedCache.mode", "ENABLE_SELECTIVE");
//		jpaProperties.setProperty("hibernate.archive.autodetection", "class");
        jpaProperties.setProperty("hibernate.hbm2ddl.auto", ddlAuto);
        jpaProperties.setProperty("hibernate.flushMode", "FLUSH_AUTO");
        jpaProperties.setProperty("hibernate.show_sql", showSql);
        jpaProperties.setProperty("javax.persistence.lock.timeout", "0");
        entityManagerFactory.setJpaProperties(jpaProperties);
        log.info("DBConfig - entityManagerFactory Properties");

        return entityManagerFactory;
    }
}
