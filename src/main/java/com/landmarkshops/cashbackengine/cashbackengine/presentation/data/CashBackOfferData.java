package com.landmarkshops.cashbackengine.cashbackengine.presentation.data;

import java.util.Set;

import com.landmarkshops.cashbackengine.cashbackengine.domain.model.CashBackFact;

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
public class CashBackOfferData
{
	private String cashbackId;
	private PriceData claimAmount;
	private String ruleFileName;
	private boolean active;
	private Set<CashBackFact> cashBackFacts;
	private int priority;
	private String description;
	private String offerName;
}
