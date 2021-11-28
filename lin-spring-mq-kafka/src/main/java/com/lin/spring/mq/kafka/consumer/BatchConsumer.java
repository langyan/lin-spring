package com.lin.spring.mq.kafka.consumer;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

//@Component
public class BatchConsumer {

	@Autowired
	private KafkaListenerEndpointRegistry registry;

	ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
	
	private static final Logger LOG = LoggerFactory.getLogger(BatchConsumer.class);
	
	@KafkaListener(topics = "michael-test", containerGroup = "michaelGroup1", containerFactory = "kafkaListenerContainerFactory")
	public void consumer2(List<String> message, Acknowledgment ack) {

		 LOG.info("beginning to consume batch messages");
			if(message!=null) {
				for (String string : message) {
					System.out.println("batch consumer1  : " + string);
				}
			}
		ack.acknowledge();
		 LOG.info("end to consume batch messages");
	}
	
}
