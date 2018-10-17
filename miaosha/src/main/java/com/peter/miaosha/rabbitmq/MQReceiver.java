package com.peter.miaosha.rabbitmq;

import com.peter.miaosha.domain.MiaoshaOrder;
import com.peter.miaosha.domain.MiaoshaUser;
import com.peter.miaosha.redis.RedisService;
import com.peter.miaosha.service.GoodsService;
import com.peter.miaosha.service.MiaoshaService;
import com.peter.miaosha.service.OrderService;
import com.peter.miaosha.vo.GoodsVo;
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

	@Autowired
	GoodsService goodsService;

	@Autowired
	OrderService orderService;

	@Autowired
	MiaoshaService miaoshaService;

	@RabbitListener(queues=MQConfig.MIAOSHA_QUEUE)
	public void receiveMiaoshaMsg(String message) {
		log.info("receive message:"+message);
		MiaoshaMessage mm  = RedisService.stringToBean(message, MiaoshaMessage.class);
		MiaoshaUser user = mm.getUser();
		long goodsId = mm.getGoodsId();

		GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
		int stock = goods.getStockCount();
		if(stock <= 0) {
			return;
		}
		//判断是否已经秒杀到了
		MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
		if(order != null) {
			return;
		}
		//减库存 下订单 写入秒杀订单
		miaoshaService.miaosha(user, goods);
	}
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

	@RabbitListener(queues=MQConfig.HEADER_QUEUE)
	public void receiveHeaderQueue(byte[] message) {
		log.info(" header  queue message:"+new String(message));
	}
}
