package com.landmarkshops.cashbackengine.cashbackengine.domain.model;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "claimId")
@AllArgsConstructor
@Document
@Builder(toBuilder = true)
public class ClaimCashBack
{
	@Id
	@NotNull
	private long claimId;
	private int stagePosition;
	private List<Stage> stages;
	private String status;
	private long customerPk;
	private long cashbackId;
}
