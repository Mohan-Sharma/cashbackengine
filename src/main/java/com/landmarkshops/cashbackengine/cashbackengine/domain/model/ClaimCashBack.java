package com.landmarkshops.cashbackengine.cashbackengine.domain.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClaimCashBack {

	
	private int stagePosition;
	
	private String cashbackCode;
	
	private List<Stage> stages;
	
	private String status;
}
