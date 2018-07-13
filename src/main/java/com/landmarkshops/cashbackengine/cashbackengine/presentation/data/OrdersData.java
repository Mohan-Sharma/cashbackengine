package com.landmarkshops.cashbackengine.cashbackengine.presentation.data;

import org.joda.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Mohan Sharma Created on 11/07/18.
 */
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OrdersData
{
	private long orderCode;
	private String customerPk;
	private PriceData price;
	private String orderStatus;
	private String[] categories;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate orderCreationTime;
}
