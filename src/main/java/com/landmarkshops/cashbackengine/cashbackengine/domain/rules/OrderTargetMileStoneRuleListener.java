package com.landmarkshops.cashbackengine.cashbackengine.domain.rules;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.RuleListener;

public class OrderTargetMileStoneRuleListener implements RuleListener {

	@Override
	public boolean beforeEvaluate(Rule rule, Facts facts) {
		
		return true;
	}

	@Override
	public void afterEvaluate(Rule rule, Facts facts, boolean evaluationResult) {
		
		System.out.println("after "+evaluationResult);
		
	}

	@Override
	public void beforeExecute(Rule rule, Facts facts) {
		
		System.out.println("before");
		
	}

	@Override
	public void onSuccess(Rule rule, Facts facts) {
		
		System.out.println("send event to infrom customer");
		
	}

	@Override
	public void onFailure(Rule rule, Facts facts, Exception exception) {
		
		exception.printStackTrace();
		
	}

	
}
