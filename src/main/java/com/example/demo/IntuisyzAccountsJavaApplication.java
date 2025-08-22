package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.example.demo.fileService.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({
	FileStorageProperties.class
})
public class IntuisyzAccountsJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntuisyzAccountsJavaApplication.class, args);
	}

}
