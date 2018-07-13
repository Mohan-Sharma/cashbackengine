package com.landmarkshops.cashbackengine.cashbackengine.domain.model;

import java.io.Serializable;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Mohan Sharma Created on 12/07/18.
 */
@Getter
@Setter
@NoArgsConstructor
@Builder
@EqualsAndHashCode(exclude = "cashbackId")
@AllArgsConstructor
@Document
public class CashBackOffer implements Serializable
{
	@Id
	private long cashbackId;
	private Price claimAmount;
	private String ruleFileName;
	private boolean active;
	private Set<CashBackFact> cashBackFacts;
	private int priority;
	private String description;
	private String offerName;
}


