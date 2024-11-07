package com.happiest.EligibilityMicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
@SpringBootApplication
@EnableFeignClients
public class EligibilityMicroserviceApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(EligibilityMicroserviceApplication.class, args);

	}
}
