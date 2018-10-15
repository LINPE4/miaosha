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
		

		

	
		@RabbitListener(queues=MQConfig.QUEUE)
		public void receive(String message) {
			log.info("receive message:"+message);
		}

		
}
