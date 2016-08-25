package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

@SpringBootApplication
@EnableJpaRepositories
@EnableCaching
public class SettingConsumerApplication {
	public static void main(String[] args) throws MalformedURLException, UnsupportedEncodingException {
		SpringApplication.run(SettingConsumerApplication.class, args);
	}
}
