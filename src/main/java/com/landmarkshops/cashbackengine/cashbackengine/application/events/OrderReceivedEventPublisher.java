package com.landmarkshops.cashbackengine.cashbackengine.application.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class OrderReceivedEventPublisher {

	  @Autowired
	   private ApplicationEventPublisher applicationEventPublisher;
	  
	  
	  public void publish(final long customerPk) {
	      
		OrderReceivedEvent orderRecievedEvent = new OrderReceivedEvent(customerPk);
		applicationEventPublisher.publishEvent(orderRecievedEvent);
	   }

}
