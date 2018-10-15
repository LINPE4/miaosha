package com.peter.miaosha.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {
	

	public static final String QUEUE = "queue";

	
	/**
	 * Direct模式 交换机Exchange
	 * */
	@Bean
	public Queue queue() {
		return new Queue(QUEUE, true);
	}
	

}
