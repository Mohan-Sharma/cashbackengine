package com.landmarkshops.cashbackengine.cashbackengine.domain.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class Orders extends Auditable implements Serializable
{
	private String orderCode;
	private String[] categories;
	private Price price;
	private OrderStatus orderStatus;
	private String conceptCode;

	public enum OrderStatus
	{

	}
}
