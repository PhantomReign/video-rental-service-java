package com.videorentalservice.configurations;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.dialect.springdata.SpringDataDialect;

/**
 * Created by Rave on 18.02.2017.
 */

@Configuration
public class CommonBeanConfiguration {

    @Bean
    SpringDataDialect springDataDialect() {
        return new SpringDataDialect();
    }

    @Bean
    public StrongPasswordEncryptor strongEncryptor(){
        return new StrongPasswordEncryptor();
    }
}