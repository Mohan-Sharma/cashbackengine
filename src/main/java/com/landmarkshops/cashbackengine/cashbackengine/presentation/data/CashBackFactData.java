package com.landmarkshops.cashbackengine.cashbackengine.presentation.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Mohan Sharma Created on 13/07/18.
 */
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CashBackFactData
{
	private String name;
	private String value;
	private String operator;
}
