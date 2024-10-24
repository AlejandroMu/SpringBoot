package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Base64;


@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.example.demo.repositories")
public class H2DemoApplication {

	public static void main(String[] args) {
		byte[] keyBytes = Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded();
        String base64Key = Base64.getEncoder().encodeToString(keyBytes);
        System.out.println(base64Key);
		SpringApplication.run(H2DemoApplication.class, args);
	}

}
