package com.lin.spring.mq.kafka.consumer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

	@Autowired
	private KafkaListenerEndpointRegistry registry;

	ScheduledExecutorService service = Executors.newScheduledThreadPool(10);

	/**
	 * 监听test主题,有消息就读取
	 * 
	 * @param message
	 */
	@KafkaListener(topics = {
			"michael-test" },  containerGroup = "michaelGroup1", containerFactory = "ackContainerFactory")
	public void consumer1(String message, Acknowledgment ack) {
		System.out.println("consumer1 : " + message);
//		ack.acknowledge();

		service.scheduleWithFixedDelay(() -> {
			MessageListenerContainer michael2 = registry.getListenerContainer("michael2");
			if (!michael2.isRunning()) {
				michael2.stop();
			}

		}, 10, 40, TimeUnit.SECONDS);

	}

	@KafkaListener(topics = {
			"michael-test" }, containerGroup = "michaelGroup2", containerFactory = "ackContainerFactory", autoStartup = "false")
	public void consumer2(String message, ConsumerRecord record, Acknowledgment ack) {

		System.out.println("consumer2  : " + message);
//		ack.acknowledge();
	}
}
