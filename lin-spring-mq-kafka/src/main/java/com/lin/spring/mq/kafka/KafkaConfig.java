package com.lin.spring.mq.kafka;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;


/**
 * <p>kafka配置</P>
 *
 */
@Configuration
@EnableKafka
public class KafkaConfig {
	
	  
    @Value("${spring.kafka.bootstrap.servers}")
    private String servers;
    @Value("${kafka.consumer.enable.auto.commit:false}")
    private boolean enableAutoCommit;
    @Value("${kafka.consumer.session.timeout:15000}")
    private String sessionTimeout;
    @Value("${kafka.consumer.max.poll.interval.ms:300000}")
    private String maxPollInterval;
    @Value("${kafka.consumer.max.poll.records:50}")
    private String maxPollRecords;
    @Value("${kafka.consumer.max.poll.timeout:3000}")
    private Integer pollTimeout;
    @Value("${kafka.consumer.auto.commit.interval:100}")
    private String autoCommitInterval;
    @Value("${kafka.consumer.group.id:michaelGroup}")
    private String groupId;
    @Value("${kafka.consumer.auto.offset.reset:earliest}")
    private String autoOffsetReset;
    @Value("${kafka.consumer.concurrency:4}")
    private int concurrency;
    
    @Value("${spring.profiles.active:test}")
	private String env;
	
	@Value(value = "${mq.listener.disabled:false}")
	private String disabled = Boolean.FALSE.toString();

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory();
        //设置消费者工厂
        factory.setConsumerFactory(consumerFactory());
        //消费者中线程数
        factory.setConcurrency(concurrency);
        //拉取超时时间
        factory.getContainerProperties().setPollTimeout(pollTimeout);
        factory.setBatchListener(true);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }

    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<String, String>(consumerConfigs());
    }
    
   
 
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> propsMap = new HashMap<>();
        // Kafka地址
        propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        //配置默认分组，这里没有配置+在监听的地方没有设置groupId，多个服务会出现收到相同消息情况
        propsMap.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        // 是否自动提交offset偏移量(默认true)
        propsMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
        // 自动提交的频率(ms)
        propsMap.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitInterval);
        // Session超时设置
        propsMap.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeout);
        propsMap.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG,maxPollInterval);
        propsMap.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,maxPollRecords);
        // 键的反序列化方式
        propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        // 值的反序列化方式
        propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        // offset偏移量规则设置：
        // (1)、earliest：当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，从头开始消费
        // (2)、latest：当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，消费新产生的该分区下的数据
        // (3)、none：topic各分区都存在已提交的offset时，从offset后开始消费；只要有一个分区不存在已提交的offset，则抛出异常
        propsMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        return propsMap;
    }

    @Bean
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer> ackContainerFactory() {
        ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(consumerFactory());
        factory.getContainerProperties().setPollTimeout(pollTimeout);
        //设置ACK模式(手动提交模式，这里有七种)
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        //测试环境关闭监听
        if (Boolean.TRUE.toString().equals(disabled)) {
        	 factory.setAutoStartup(false);
        }
       
        return factory;
    }
    

}
