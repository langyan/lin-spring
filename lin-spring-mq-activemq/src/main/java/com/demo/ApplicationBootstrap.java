package com.demo;

import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(ActiveMQProperties.class)
public class ApplicationBootstrap {
	
	@Bean
    public Queue queue() {
       return new ActiveMQQueue("sample.queue");
    }

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ApplicationBootstrap.class, args);
	}
}
