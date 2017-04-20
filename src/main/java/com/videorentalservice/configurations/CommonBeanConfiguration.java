package com.videorentalservice.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.dialect.springdata.SpringDataDialect;

/**
 * Created by Rave on 18.02.2017.
 */

@Configuration
public class CommonBeanConfiguration {

    @Autowired
    private TemplateEngine templateEngine;

    @Bean
    SpringDataDialect springDataDialect() {
        return new SpringDataDialect();
    }

    @Bean
    public TemplateEngine templateEngine(){
        return templateEngine;
    }

}