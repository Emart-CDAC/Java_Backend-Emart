package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.config.SSLConfig;

@SpringBootApplication
@ComponentScan(basePackages = { "com.example" })
@EnableJpaRepositories(basePackages = "com.example.repository")
@EntityScan(basePackages = "com.example.model")
@EnableAsync
public class EmartCdacApplication {

	public static void main(String[] args) {
		SSLConfig.disableSSLVerification();
		System.out.println(new BCryptPasswordEncoder().encode("admin123"));
		SpringApplication.run(EmartCdacApplication.class, args);
	}
}
