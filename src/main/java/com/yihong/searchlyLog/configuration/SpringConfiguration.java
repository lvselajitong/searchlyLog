package com.yihong.searchlyLog.configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;

@Configuration  
@EnableWebMvc
public class SpringConfiguration extends WebMvcConfigurerAdapter {
	@Bean
    public JestClient jestClient() throws Exception {

        String connectionUrl;

        if (System.getenv("SEARCHBOX_URL") != null) {
            // Heroku
            connectionUrl = System.getenv("SEARCHBOX_URL");

        } else if (System.getenv("VCAP_SERVICES") != null) {
            // CloudFoundry
            Map result = new ObjectMapper().readValue(System.getenv("VCAP_SERVICES"), HashMap.class);
            connectionUrl = (String) ((Map) ((Map) ((List)
                    result.get("my-search")).get(0)).get("credentials")).get("uri");
        } else {
            connectionUrl = "http://112.74.15.26:9200";
        }

        // Configuration
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig
                .Builder(connectionUrl)
                .multiThreaded(true)
                .readTimeout(60000)
                .build());
        return factory.getObject();
    }
	@Override  
    public void addCorsMappings(CorsRegistry registry) {  
        registry.addMapping("/**")  
                .allowedOrigins("*")  
                .allowCredentials(false)  
                .allowedMethods("GET", "POST", "DELETE", "PUT")  
                .maxAge(3600);  
    }
}
