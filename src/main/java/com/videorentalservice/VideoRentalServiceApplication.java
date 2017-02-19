package com.videorentalservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.videorentalservice"})
@EntityScan("com.videorentalservice.domain")
@EnableJpaRepositories("com.videorentalservice.repositories")
public class VideoRentalServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VideoRentalServiceApplication.class, args);
	}
}
