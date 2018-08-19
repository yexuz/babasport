package com.zlj.core.message;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;

import com.zlj.core.service.SearchService;

public class CustomMessageListener implements MessageListener {

	@Autowired
	private SearchService searchService;

	@Override
	public void onMessage(Message message) {
		ActiveMQTextMessage am = (ActiveMQTextMessage) message;
		try {
			System.out.println("ActiveMq中的消息是solr:" + am.getText());
			searchService.insertProductTOSolr(Long.parseLong(am.getText()));
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
