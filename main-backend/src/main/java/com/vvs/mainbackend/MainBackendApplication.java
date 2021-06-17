package com.vvs.mainbackend;

import com.github.cloudyrock.spring.v5.EnableMongock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableMongock
public class MainBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainBackendApplication.class, args);
	}

}
