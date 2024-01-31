package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.auth.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@EnableTransactionManagement
@EnableAsync
@EnableScheduling
@ComponentScan({ "ar.edu.itba.paw.webapp.controller","ar.edu.itba.paw.webapp.exceptions.mappers","ar.edu.itba.paw.service","ar.edu.itba.paw.persistence","ar.edu.itba.paw.webapp.config"})
@Configuration
public class WebConfig {

    @Value("classpath:schema.sql")
    private Resource schemaSql;

    @Bean
    public DataSource dataSource() {
        final SimpleDriverDataSource ds = new SimpleDriverDataSource();
        ds.setDriverClass(org.postgresql.Driver.class);
        //ds.setUrl("jdbc:postgresql://localhost/paw");
        //ds.setUsername("postgres");
        //ds.setPassword("postgres");
        //ds.setPassword("123456"); //Solo para Joji

        ds.setUrl("jdbc:postgresql:paw-2021b-7");
        ds.setUsername("paw-2021b-7");
        ds.setPassword("zIg1Bo5hw");
        return ds;
    }

    @Bean
    public JwtTokenUtil jwtUtil(@Value("classpath:jwt.key") Resource resource) throws IOException {
        return new JwtTokenUtil(resource);
    }

    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean
    public MessageSource messageSource(){
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
        messageSource.setCacheSeconds(5);
        return messageSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

        factoryBean.setPackagesToScan("ar.edu.itba.paw.model");
        factoryBean.setDataSource(dataSource());

        final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        factoryBean.setJpaVendorAdapter(vendorAdapter);

        final Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL92Dialect");

        factoryBean.setJpaProperties(properties);
        return factoryBean;
    }

    @Autowired
    private String appHost;

    @Autowired
    private String appProtocol;

    @Autowired
    private String appWebContext;

    @Autowired
    private int appPort;

    @Bean(name = "appBaseUrl")
    public URL appBaseUrl() throws MalformedURLException {
        return new URL(appProtocol, appHost, appPort, appWebContext);
    }

}