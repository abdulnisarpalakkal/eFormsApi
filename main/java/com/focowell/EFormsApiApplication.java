package com.focowell;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.focowell.datasource.CustomRoutingDataSource;
import com.focowell.service.TenantDBService;

@SpringBootApplication( exclude = { 
		 DataSourceAutoConfiguration.class
//		 ,HibernateJpaAutoConfiguration.class
//		 ,DataSourceTransactionManagerAutoConfiguration.class 
		 })	
@EnableTransactionManagement
public class EFormsApiApplication extends SpringBootServletInitializer  {
	private static final Logger logger = LogManager.getLogger(EFormsApiApplication.class);
	@Autowired
	TenantDBService tenantDBService;
//	@Bean
//    public MessageSource messageSource() {
//        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
//        messageSource.setBasename("classpath:messages");
//        messageSource.setDefaultEncoding("UTF-8");
//        return messageSource;
//    }
	
	@Bean
	public LocalValidatorFactoryBean validator(MessageSource messageSource) {
	    LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
	    validatorFactoryBean.setValidationMessageSource(messageSource);
	    return validatorFactoryBean;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(EFormsApiApplication.class, args);
		logger.debug("--Application Started--");
	}
	
	
	@Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
	@Bean
	public Module datatypeHibernateModule() {
	  return new Hibernate5Module();
	}
	@Bean
    public DataSource dataSource(){
        CustomRoutingDataSource customDataSource=new CustomRoutingDataSource();
        customDataSource.setTargetDataSources(tenantDBService.getDataSourceHashMap());
        return customDataSource;
    }
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
}
