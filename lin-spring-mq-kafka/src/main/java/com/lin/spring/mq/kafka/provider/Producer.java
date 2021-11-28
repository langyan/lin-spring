package com.lin.spring.mq.kafka.provider;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class Producer {

	@Autowired
	private KafkaTemplate kafkaTemplate;
	
	int i=0;

	/**
	 * 发送消息到kafka,主题为test
	 */
	 @SuppressWarnings("unchecked")
	@Scheduled(fixedDelay=10000)
	public void sendTest() {
		 i++;
		kafkaTemplate.send("michael-test","key",i+
				"-kafka-1  " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		 i++;
		kafkaTemplate.send("michael-test","key",i+
				"-kafka-2  " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		 i++;
		kafkaTemplate.send("michael-test1","key",i+
				"-kafka-2  " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		 i++;
		kafkaTemplate.send("michael-test1","key",i+
				"-kafka-2  " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
	}
	 
//	 @SuppressWarnings("unchecked")
//		@Scheduled(fixedDelay=10000)
//		public void sendTest1() {
//			 i++;
//			kafkaTemplate.send("c3i-eai-maint-change","key",i+
//					"-kafka-1  " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//			 i++;
//			kafkaTemplate.send("michael-test","key",i+
//					"-kafka-2  " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//			 i++;
//			kafkaTemplate.send("michael-test1","key",i+
//					"-kafka-2  " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//			 i++;
//			kafkaTemplate.send("michael-test1","key",i+
//					"-kafka-2  " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//		}

}
