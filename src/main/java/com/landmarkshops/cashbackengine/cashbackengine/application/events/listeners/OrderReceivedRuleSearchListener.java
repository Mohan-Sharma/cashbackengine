package com.landmarkshops.cashbackengine.cashbackengine.application.events.listeners;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.landmarkshops.cashbackengine.cashbackengine.application.events.OrderReceivedEvent;

@Component
public class OrderReceivedRuleSearchListener  {

	@EventListener
	public void handEvent(OrderReceivedEvent event) {
		System.out.println("recieve events for customer"+event.getCustomerPk());
	}

}
