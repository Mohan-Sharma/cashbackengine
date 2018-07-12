package com.landmarkshops.cashbackengine.cashbackengine.presentation.data;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Mohan Sharma Created on 11/07/18.
 */
@Getter
@Setter
@NoArgsConstructor
public class OrdersData
{
	private String orderCode;
	private long customerPk;
	private PriceData price;
	private String orderStatus;
	private String[] categories;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate orderCreationTime;
}
