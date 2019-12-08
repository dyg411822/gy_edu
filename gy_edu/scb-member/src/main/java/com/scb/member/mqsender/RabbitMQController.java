package com.scb.member.mqsender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scb.common.rabbitmq.po.Mail;
import com.scb.common.rabbitmq.service.impl.ProducerImpl;
import com.scb.common.rabbitmq.service.impl.PublisherImpl;

@Controller
public class RabbitMQController {
//	
//	
//	@Autowired
//	private ProducerImpl producer;
//	
//	@Autowired
//	private PublisherImpl publisher;
//	
//	@RequestMapping(value="/produce",produces = {"application/json;charset=UTF-8"})
//	@ResponseBody
//	public void produce(@ModelAttribute("mail")Mail mail) throws Exception{
////		producer.sendMail("myqueue",mail);
//	}
//	
//	@RequestMapping(value="/topic",produces = {"application/json;charset=UTF-8"})
//	@ResponseBody
//	public void topic(@ModelAttribute("mail")Mail mail) throws Exception{
////		publisher.publishMail(mail);
//	}
//	
//	@RequestMapping(value="/direct",produces = {"application/json;charset=UTF-8"})
//	@ResponseBody
//	public void direct(@ModelAttribute("mail")Mail mail){
////		publisher.senddirectMail(m, mail.getRoutingkey());
//	}
//	
//	@RequestMapping(value="/mytopic",produces = {"application/json;charset=UTF-8"})
//	@ResponseBody
//	public void mytopic(@ModelAttribute("mail")Mail mail){
////		publisher.sendtopicMail(m, mail.getRoutingkey());
//	}
//	
//	
//	@RequestMapping("demo")
//	public String demo(){
//		return "demo";
//	}
//	
//	
}
