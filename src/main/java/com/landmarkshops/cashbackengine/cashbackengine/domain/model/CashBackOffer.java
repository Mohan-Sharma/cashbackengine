package com.landmarkshops.cashbackengine.cashbackengine.domain.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.EntityListeners;
import javax.persistence.Id;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
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
@EqualsAndHashCode(of="offerCode")
@AllArgsConstructor
@Document
@EntityListeners(AuditingEntityListener.class)
public class CashBackOffer implements Serializable
{
	@Id
	private CashbackId cashbackId;
	private OfferStatus offerStatus;
	private LocalDate claimDate;
	private Price claimAmount;
	private String customerPk;

	public enum OfferStatus
	{
		CREATED, READY, AVAILED;
	}
}


