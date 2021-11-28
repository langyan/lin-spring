package com.lin.spring.mq.kafka.consumer;

import java.util.ArrayList;
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
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

	@Autowired
	private KafkaListenerEndpointRegistry registry;

	ScheduledExecutorService service = Executors.newScheduledThreadPool(10);

	private static final Logger LOG = LoggerFactory.getLogger(Consumer.class);

	@KafkaListener(topics = {
			"michael-test1" }, containerGroup = "michaelGroup2", containerFactory = "ackContainerFactory")
	public void consumer2(List<String> message, Acknowledgment ack) {
		if (message != null) {
			for (String string : message) {
				System.out.println("consumer2  : " + string);
			}
		}
		ack.acknowledge();
	}

	@KafkaListener(topics = {
			"c3i-eai-maint-change2" }, containerGroup = "michaelGroup2", containerFactory = "kafkaListenerContainerFactory")
	public void consumer3(List<String> message, Acknowledgment ack) {
		if (message != null) {
			for (String string : message) {
				System.out.println("consumer2  : " + string);
			}
		}
		ack.acknowledge();
	}
}
