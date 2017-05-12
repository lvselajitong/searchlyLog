package com.yihong.searchlyLog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import com.yihong.searchlyLog.cors.CorsFilter;

@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class SearchlyLogApplication {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(SearchlyLogApplication.class, args);
	}
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
}
