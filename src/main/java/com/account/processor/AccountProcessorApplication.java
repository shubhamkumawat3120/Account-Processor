package com.account.processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class AccountProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountProcessorApplication.class, args);
	}

}
