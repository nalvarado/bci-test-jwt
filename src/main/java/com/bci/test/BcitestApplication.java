package com.bci.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@ImportAutoConfiguration({ SecurityConfig.class })
public class BcitestApplication {

	public static void main(String[] args) {
		SpringApplication.run(BcitestApplication.class, args);
	}

}
