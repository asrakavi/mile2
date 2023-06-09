package com.example.Mile2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication(scanBasePackages = {"com.example.Mile2","com.example.Mile2.Controller","com.example.Mile2.Entity","com.example.Mile2.Repository","com.example.Mile2.Services","com.example.Mile2.Config","com.example.Mile2.Methods"})
public class Mile2Application {
	public static void main(String[] args) {
		SpringApplication.run(Mile2Application.class, args);
	}

	CommandLineRunner commandLineRunner(KafkaTemplate<String,String> kafkaTemplate){
		return args -> {
			kafkaTemplate.send("wallet","hlo");
		};
	}

}
