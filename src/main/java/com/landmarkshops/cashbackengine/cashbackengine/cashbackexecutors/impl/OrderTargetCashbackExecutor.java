package com.landmarkshops.cashbackengine.cashbackengine.cashbackexecutors.impl;

import java.math.BigDecimal;
import java.util.List;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.landmarkshops.cashbackengine.cashbackengine.application.service.CashBackService;
import com.landmarkshops.cashbackengine.cashbackengine.cashbackexecutors.CashBackExecutor;
import com.landmarkshops.cashbackengine.cashbackengine.domain.model.CashBackOffer;
import com.landmarkshops.cashbackengine.cashbackengine.domain.model.Orders;
import com.landmarkshops.cashbackengine.cashbackengine.domain.rules.OrderTargetMileStoneRuleListener;
import com.landmarkshops.cashbackengine.cashbackengine.domain.rules.OrderTargetMilestonesRule;
import com.landmarkshops.cashbackengine.cashbackengine.persistence.CashBackOfferRepository;
import com.landmarkshops.cashbackengine.cashbackengine.presentation.data.OrdersData;
import com.landmarkshops.cashbackengine.cashbackengine.presentation.data.PriceData;

@Component
public class OrderTargetCashbackExecutor implements CashBackExecutor {

	@Autowired
	private CashBackOfferRepository cashBackOfferRepository;
	@Autowired
	private CashBackService cashBackService;

	@Override
	public void execute(long caskbackId,long customerPk) {

		CashBackOffer cashBackOffer = cashBackOfferRepository.findOne(caskbackId);
		Facts facts = new Facts();
		cashBackOffer.getCashBackFacts().stream().forEach(fact -> {
			facts.put(fact.getName(), fact.getValue());
		});

		BigDecimal minOrderVal = new BigDecimal(facts.get("minOrderValue").toString());
		String currencyISO = (String) facts.get("currencyISO");
		Integer duration = (Integer) facts.get("duration");
		List<OrdersData> orders = cashBackService.fetchAllOrdersForCustomerGreaterThanThresholdPrice(customerPk, duration, minOrderVal, currencyISO);
		facts.put("orders",orders);
		facts.put("cashbackId", caskbackId);
		facts.put("customerPk", customerPk);
		Rules rules = new Rules();
		rules.register(new OrderTargetMilestonesRule());
		RulesEngine rulesEngine = new DefaultRulesEngine();
		rulesEngine.getRuleListeners().add(new OrderTargetMileStoneRuleListener());
		rulesEngine.fire(rules, facts);

	}


}
