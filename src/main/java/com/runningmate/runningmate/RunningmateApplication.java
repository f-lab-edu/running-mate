package com.runningmate.runningmate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RunningmateApplication {

	public static void main(String[] args) {
		SpringApplication.run(RunningmateApplication.class, args);
	}
}
