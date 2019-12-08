package com.scb.common.rabbitmq.service;

import org.springframework.amqp.core.Queue;

import com.scb.common.rabbitmq.po.Mail;
import com.scb.common.rabbitmq.sendResult.SendResultForm;


public interface Producer {
	/**
	 * 向队列queue发送消息
	 * @param queue
	 * @param mail
	 * @return
	 */
	public SendResultForm sendMail(Queue queue,Mail mail);
}
