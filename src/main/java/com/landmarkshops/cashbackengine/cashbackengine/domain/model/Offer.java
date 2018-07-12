package com.landmarkshops.cashbackengine.cashbackengine.domain.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Embeddable;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Mohan Sharma Created on 12/07/18.
 */
@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class Offer extends Auditable implements Serializable
{
	private long offerCode;
	private OfferStatus offerStatus;
	private LocalDate claimDate;
	private Price claimAmount;

	public enum OfferStatus
	{
		CREATED, READY, AVAILED;
	}
}
