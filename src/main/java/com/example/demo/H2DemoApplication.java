package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.example.demo.repositories")
public class H2DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(H2DemoApplication.class, args);
	}

}
