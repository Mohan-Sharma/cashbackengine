package com.landmarkshops.cashbackengine.cashbackengine.presentation.data;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Mohan Sharma Created on 11/07/18.
 */
@Getter
@Setter
@NoArgsConstructor
public class PriceData
{
	private BigDecimal value;
	private String currencyISO;
}
