package com.agrp.dev.ukol.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author SebelaM
 *
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {
		"com.agrp.dev.ukol.controllers",
		"com.agrp.dev.ukol.services"		
	}
)
@EntityScan("com.agrp.dev.ukol.entities")
@EnableJpaRepositories("com.agrp.dev.ukol.repositories")
public class UkolApplication {

	public static void main(String[] args) {
		SpringApplication.run(UkolApplication.class, args);
	}

}