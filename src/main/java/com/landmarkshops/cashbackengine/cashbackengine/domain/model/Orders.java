package com.landmarkshops.cashbackengine.cashbackengine.domain.model;

import java.io.Serializable;
import java.util.stream.Stream;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "orderCode")
@AllArgsConstructor
@Document
@Builder(toBuilder = true)
public class Orders implements Serializable
{
	@Id
	@NotNull
	private long orderCode;
	@NotEmpty
	private String[] categories;
	@NotNull
	private Price price;
	@NotNull
	private OrderStatus orderStatus;
	@NotNull
	private LocalDate orderCreationTime;
	@NotNull
	private long customerPk;

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

		public String getDescription()
		{
			return description;
		}
	}
}
