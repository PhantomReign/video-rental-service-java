package com.videorentalservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.dialect.springdata.SpringDataDialect;

@SpringBootApplication
@ComponentScan({"com.videorentalservice"})
@EntityScan("com.videorentalservice.models")
@EnableJpaRepositories("com.videorentalservice.repositories")
public class VideoRentalServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VideoRentalServiceApplication.class, args);
	}


}
