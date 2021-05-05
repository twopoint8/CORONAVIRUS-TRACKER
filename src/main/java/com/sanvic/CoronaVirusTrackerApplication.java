package com.sanvic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.sanvic.service.CoronaVirusURLDataFetchService;

@SpringBootApplication
@EnableScheduling
public class CoronaVirusTrackerApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(CoronaVirusTrackerApplication.class, args);
		
	}

}
