package com.landmarkshops.cashbackengine.cashbackengine.domain.model;

import javax.persistence.EntityListeners;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Mohan Sharma Created on 13/07/18.
 */
@Getter
@Setter
@NoArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(of="claimCode")
@AllArgsConstructor
@Document
@EntityListeners(AuditingEntityListener.class)
public class Claim
{
	@Id
	@NotNull
	private Long claimCode;
	private ClaimStatus claimStatus;
	private String customerPk;
	private CashbackId cashbackId;

	public enum ClaimStatus
	{
		CREATED, READY, AVAILED
	}
}
