package com.landmarkshops.cashbackengine.cashbackengine.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Orders {
	
	private String customerPk;
	@Id
	private String orderNumber;
	
	
	public String getCustomerPk() {
		return customerPk;
	}

	public void setCustomerPk(String customerPk) {
		this.customerPk = customerPk;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	
	
}
