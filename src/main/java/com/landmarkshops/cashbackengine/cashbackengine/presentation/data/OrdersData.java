package com.landmarkshops.cashbackengine.cashbackengine.presentation.data;

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
	private String customerPk;
	private String[] categories;
	private PriceData price;
}
