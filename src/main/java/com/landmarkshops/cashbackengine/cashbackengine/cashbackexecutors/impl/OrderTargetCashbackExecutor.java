package com.landmarkshops.cashbackengine.cashbackengine.cashbackexecutors.impl;

import java.util.ArrayList;
import java.util.List;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.springframework.stereotype.Component;

import com.landmarkshops.cashbackengine.cashbackengine.cashbackexecutors.CashBackExecutor;
import com.landmarkshops.cashbackengine.cashbackengine.domain.model.CashBackFacts;
import com.landmarkshops.cashbackengine.cashbackengine.domain.model.Orders;
import com.landmarkshops.cashbackengine.cashbackengine.domain.rules.OrderTargetMileStoneRuleListener;
import com.landmarkshops.cashbackengine.cashbackengine.domain.rules.OrderTargetMilestonesRule;

@Component
public class OrderTargetCashbackExecutor implements CashBackExecutor {

	
	@Override
	public void execute(String caskbackCode,String customerPk) {
		
		// Load facts
		
		List<CashBackFacts> cbfacts = new ArrayList<>();
		
		Facts facts = new Facts();
		cbfacts.stream().forEach(fact -> {
			
			facts.put(fact.getName(), fact.getValue());
			
		});
		
		List<Orders> orders = new ArrayList<>();
		facts.put("orders",orders);
		
		 Double minOrderVal = (Double) facts.get("minOrderValue");
		 
		 Integer duration = (Integer) facts.get("duration");
		 
		 // Register Rule
		 
		 Rules rules = new Rules();
		
		 rules.register(new OrderTargetMilestonesRule());
		 
		 RulesEngine rulesEngine = new DefaultRulesEngine();
		 rulesEngine.getRuleListeners().add(new OrderTargetMileStoneRuleListener());
		 rulesEngine.fire(rules, facts);
		
	}
	

}
