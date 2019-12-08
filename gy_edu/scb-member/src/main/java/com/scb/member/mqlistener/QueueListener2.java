package com.scb.member.mqlistener;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.scb.common.rabbitmq.po.Mail;
@Component
public class QueueListener2 {
	
	@RabbitListener(queues = "myqueue")
	public void displayMail(Mail mail) throws Exception {
		System.out.println("队列监听器2号收到消息"+mail.toString());
	}
}
