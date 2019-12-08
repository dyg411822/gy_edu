package com.scb.common.rabbitmq.service.impl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AbstractExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.scb.common.constant.Constant;
import com.scb.common.exception.SystemBusyException;
import com.scb.common.rabbitmq.po.Mail;
import com.scb.common.rabbitmq.sendResult.SendResultForm;
import com.scb.common.rabbitmq.service.Publisher;
/**
 * 订阅型功能需要继承该类
 * @author Administrator
 *
 */
//@Service("publisher")
public abstract class PublisherImpl implements Publisher{
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	/**
	 * 继承方法需要重写绑定规则,由业务实现者实现rountKey和Queue的绑定规则
	 */
	@Override
	public abstract void bandingQueueAndRoutKey(Map<String,Queue> queenNameAndQueueMap,String routingkey);
	
	@Override
	public SendResultForm sendMail(Mail mail, String routingkey,String exchangeName) {
		// TODO Auto-generated method stub
		SendResultForm result=new SendResultForm();
		if(null==mail||StringUtils.isBlank(exchangeName)) {
			throw new SystemBusyException("mq消息发送失败");
		}
		try {
			rabbitTemplate.convertAndSend(exchangeName, routingkey, mail);
		} catch (Exception e) {
			// TODO: handle exception
			throw new SystemBusyException("mq消息发送失败");
		}
		
		return result;
	}

	@Override
	public SendResultForm sendExchangeMail(Mail mail, Map<String, Queue> queenNameAndQueenMap, AbstractExchange exchange,
			String routingkey) {
		// TODO Auto-generated method stub
		SendResultForm result=new SendResultForm();
		if(null==mail||queenNameAndQueenMap.isEmpty()||null==exchange) {
			throw new SystemBusyException("mq消息发送失败");
		}
		//绑定队列和routingKey，待实现
		bandingQueueAndRoutKey(queenNameAndQueenMap,exchange.getName());
		
		//发送消息到对应的交换机
		result=sendMail( mail,  routingkey, exchange.getName());
		return result;
	}
	
}
