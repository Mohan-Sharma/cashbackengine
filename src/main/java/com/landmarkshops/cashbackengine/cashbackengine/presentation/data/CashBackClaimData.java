package com.landmarkshops.cashbackengine.cashbackengine.presentation.data;

import java.util.List;

import com.landmarkshops.cashbackengine.cashbackengine.domain.model.Stage;

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
public class CashBackClaimData
{
	private String claimId;
	private int stagePosition;
	private List<Stage> stages;
	private String status;
	private String customerPk;
	private String cashbackId;
}
