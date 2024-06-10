package com.demo.folder;

import com.demo.folder.service.TestTypeService;
import com.demo.folder.test_service.K6Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class PerformanceTestingServiceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(PerformanceTestingServiceApplication.class, args);
	}

	@Autowired
	private K6Service k6Service;

	@Override
	public void run(String... args) throws Exception {
		k6Service.runK6Test();
	}
}
