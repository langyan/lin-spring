package com.lin.spring.mq.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
public class KafkaApplication {
 


    public static void main(String[] args) { // ProducerConfig
    	SpringApplication.run(KafkaApplication.class, args);
    	
    }

}

