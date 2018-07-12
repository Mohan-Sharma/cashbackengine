package com.landmarkshops.cashbackengine.cashbackengine.domain.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.stream.Stream;

import javax.persistence.Embeddable;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
@Builder
@EqualsAndHashCode(of = "orderCode")
@AllArgsConstructor
public class Orders extends Auditable implements Serializable
{
	private String orderCode;
	private String[] categories;
	private Price price;
	private OrderStatus orderStatus;
	private LocalDate orderCreationTime;

	public enum OrderStatus
	{
		IN_PROGRESS("In Progress"), COMPLETED("Completed");

		String description;

		OrderStatus(final String description)
		{
			this.description = description;
		}

		public static OrderStatus getOrderStatusForDescription(final String description)
		{
			return Stream
					.of(OrderStatus.values())
					.filter(orderStatus -> orderStatus.description.equalsIgnoreCase(description)).findAny().orElse(OrderStatus.IN_PROGRESS);

		}
	}
}
