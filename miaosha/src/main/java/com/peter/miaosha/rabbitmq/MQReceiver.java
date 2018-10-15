package com.peter.miaosha.rabbitmq;

import com.peter.miaosha.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQReceiver {

	private static Logger log = LoggerFactory.getLogger(MQReceiver.class);

	@Autowired
	RedisService redisService;


	/**
	 * 交换机模式：direct模式
	 * @param message
	 */
	@RabbitListener(queues=MQConfig.QUEUE)
	public void receive(String message) {
		log.info("receive message:"+message);
	}


	/**
	 * Topic模式 交换机Exchange
	 * */
	@RabbitListener(queues=MQConfig.TOPIC_QUEUE1)
	public void receiveTopic1(String message) {
		log.info(" topic  queue1 message:"+message);
	}

	@RabbitListener(queues=MQConfig.TOPIC_QUEUE2)
	public void receiveTopic2(String message) {
		log.info(" topic  queue2 message:"+message);
	}
}
