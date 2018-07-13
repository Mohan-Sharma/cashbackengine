package com.landmarkshops.cashbackengine.cashbackengine.cashbackexecutors.impl;

import java.util.Set;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;
import com.landmarkshops.cashbackengine.cashbackengine.application.service.CashBackService;
import com.landmarkshops.cashbackengine.cashbackengine.cashbackexecutors.CashBackExecutor;
import com.landmarkshops.cashbackengine.cashbackengine.domain.model.CashBackOffer;
import com.landmarkshops.cashbackengine.cashbackengine.domain.rules.CategoryTargetMileStoneRule;
import com.landmarkshops.cashbackengine.cashbackengine.persistence.CashBackOfferRepository;

@Component
public class CategoryTargetExecutor implements CashBackExecutor{
	
	@Autowired
	private CashBackOfferRepository cashBackOfferRepository;
	@Autowired
	private CashBackService cashBackService;
	@Autowired
	private CategoryTargetMileStoneRule categoryTargetMileStoneRule;

	@Override
	public void execute(final String caskbackId, final String customerPk) {
		
		CashBackOffer cashBackOffer = cashBackOfferRepository.findOne(caskbackId);
		Facts facts = new Facts();
		cashBackOffer.getCashBackFacts().forEach(fact -> facts.put(fact.getName(), fact.getValue()));
		Integer duration = Integer.parseInt(facts.get("duration").toString());
		Set<String> categories = cashBackService.getAllCategoryForCustomer(customerPk, duration);
		facts.put("usedCategories",categories);
		facts.put("cashbackId", caskbackId);
		facts.put("customerPk", customerPk);
		final String catergoryString = (String) facts.get("categories");
		final String[] categoriesArray = catergoryString.split(",");
		facts.put("categories", Sets.newHashSet(categoriesArray));
		Rules rules = new Rules();
		rules.register(categoryTargetMileStoneRule);
		RulesEngine rulesEngine = new DefaultRulesEngine();
		rulesEngine.fire(rules, facts);
		
	}

}
