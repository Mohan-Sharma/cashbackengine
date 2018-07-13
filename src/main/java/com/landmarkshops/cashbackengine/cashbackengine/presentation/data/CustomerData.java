package com.landmarkshops.cashbackengine.cashbackengine.presentation.data;

import java.util.List;

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
public class CustomerData
{
	private String customerPk;
	private List<CashBackClaimData> cashBackClaimData;
	private List<CashBackOfferData> cashBackOfferData;
}
