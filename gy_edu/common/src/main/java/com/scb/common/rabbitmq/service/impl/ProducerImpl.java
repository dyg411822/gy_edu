package com.scb.common.rabbitmq.service.impl;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.scb.common.constant.Constant;
import com.scb.common.exception.SystemBusyException;
import com.scb.common.rabbitmq.po.Mail;
import com.scb.common.rabbitmq.sendResult.SendResultForm;
import com.scb.common.rabbitmq.service.Producer;

@Transactional
//@Service("producer")
public class ProducerImpl implements Producer{
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	
	public SendResultForm sendMail(Queue queue,Mail mail) {
		SendResultForm result=new SendResultForm();
		if(null==queue||null==mail) {
			throw new SystemBusyException("mq消息发送失败");
		}
		try {
			rabbitTemplate.convertAndSend(queue.getName(),mail);
		} catch (Exception e) {
			throw new SystemBusyException("mq消息发送失败");
		}
		return result;
	}

}
