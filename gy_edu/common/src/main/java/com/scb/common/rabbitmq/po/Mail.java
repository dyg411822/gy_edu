package com.scb.common.rabbitmq.po;

import java.io.Serializable;
import java.util.Map;

public class Mail implements Serializable{
	
	private static final long serialVersionUID = -8140693840257585779L;
	private Map<String,String> mailMap;
	
	
	public Map<String, String> getMailMap() {
		return mailMap;
	}
	public void setMailMap(Map<String, String> mailMap) {
		this.mailMap = mailMap;
	}
	@Override
	public String toString() {
		return " mailMap="+mailMap.get("name")+"id"+ mailMap.get("id")+"]";
	}
	
}
