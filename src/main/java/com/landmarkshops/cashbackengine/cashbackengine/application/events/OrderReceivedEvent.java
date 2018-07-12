package com.landmarkshops.cashbackengine.cashbackengine.application.events;

import lombok.Getter;
@Getter
public class OrderReceivedEvent {

	private long customerPk;
	
	public OrderReceivedEvent(long customerPk) {
		
		this.customerPk = customerPk;
	}

}
