package com.scb.common.rabbitmq.service;

import java.util.Map;

import org.springframework.amqp.core.AbstractExchange;
import org.springframework.amqp.core.Queue;

import com.scb.common.rabbitmq.po.Mail;
import com.scb.common.rabbitmq.sendResult.SendResultForm;

public interface Publisher {
//	public void publishMail(Mail mail);//使用fanout交换机发布消息给所有队列
	
	public SendResultForm sendMail(Mail mail,String routingkey,String exchangeName);
	/**
	 * 使用topic 或direct，或者Fanout交换机发送消息
	 * @param mail消息内容
	 * @param queenNameAndQueenMap消息队列集合
	 * @param routingkey 路由钥匙
	 * @param Exchange 交换机类型，可以传DirectExchange或者TopicExchange，或者FanoutExchange三种类型的区别就是
	 * DirectExchange的routingkey必须完全匹配，TopicExchange可以用通配符替代，FanoutExchange可以发送给所有匹配的队列
	 */
	public SendResultForm sendExchangeMail(Mail mail,Map<String,Queue> queenNameAndQueenMap,AbstractExchange exchange,String routingkey);
	/**
	 * 由业务实现者实现
	 * @param queenNameAndQueenMap
	 * @param routingkey
	 */
	public void bandingQueueAndRoutKey(Map<String,Queue> queenNameAndQueueMap,String routingkey);
}
