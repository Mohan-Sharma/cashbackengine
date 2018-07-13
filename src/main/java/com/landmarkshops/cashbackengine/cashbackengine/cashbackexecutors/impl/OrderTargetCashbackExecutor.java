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
import com.landmarkshops.cashbackengine.cashbackengine.domain.rules.OrderTargetMileStoneRuleListener;
import com.landmarkshops.cashbackengine.cashbackengine.domain.rules.OrderTargetMilestonesRule;
import com.landmarkshops.cashbackengine.cashbackengine.persistence.CashBackOfferRepository;
import com.landmarkshops.cashbackengine.cashbackengine.presentation.data.OrdersData;

@Component
public class OrderTargetCashbackExecutor implements CashBackExecutor {

	@Autowired
	private CashBackOfferRepository cashBackOfferRepository;
	@Autowired
	private CashBackService cashBackService;
	@Autowired
	private OrderTargetMilestonesRule orderTargetMilestonesRule;

	@Override
	public void execute(final long caskbackId, final long customerPk) {

		CashBackOffer cashBackOffer = cashBackOfferRepository.findOne(caskbackId);
		Facts facts = new Facts();
		cashBackOffer.getCashBackFacts().forEach(fact -> facts.put(fact.getName(), fact.getValue()));

		String minOrderVal = facts.get("minOrderValue").toString();
		String currencyISO = (String) facts.get("currencyISO");
		Integer duration = (Integer) facts.get("duration");
		List<OrdersData> orders = cashBackService.fetchAllOrdersForCustomerGreaterThanThresholdPrice(customerPk, duration, new BigDecimal(minOrderVal), currencyISO);
		facts.put("orders",orders);
		facts.put("cashbackId", caskbackId);
		facts.put("customerPk", customerPk);
		Rules rules = new Rules();
		rules.register(orderTargetMilestonesRule);
		RulesEngine rulesEngine = new DefaultRulesEngine();
		rulesEngine.getRuleListeners().add(new OrderTargetMileStoneRuleListener());
		rulesEngine.fire(rules, facts);

	}


}
