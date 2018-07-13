package com.landmarkshops.cashbackengine.cashbackengine.application.events.listeners;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.landmarkshops.cashbackengine.cashbackengine.application.events.OrderReceivedEvent;
import com.landmarkshops.cashbackengine.cashbackengine.application.service.CashBackService;
import com.landmarkshops.cashbackengine.cashbackengine.cashbackexecutors.CashBackExecutor;
import com.landmarkshops.cashbackengine.cashbackengine.domain.model.CashBackOffers;

@Component
public class OrderReceivedRuleSearchListener  {

	
	@Value("${rules.basepackage}")
	private String basepackage;
	
	@Resource
	CashBackService cashBackService;
	
	@Autowired
	private Map<String, CashBackExecutor> executors;
	
	@EventListener
	public void handEvent(OrderReceivedEvent event) {
		
		List<CashBackOffers> allCashBackOffers = cashBackService.getAllActiveCashBackOffersNotClaimedByUser(event.getCustomerPk());
		
		Optional<CashBackOffers> findFirst = allCashBackOffers.stream().findFirst();
		if(findFirst.isPresent()) {
			
		String ruleFileName = findFirst.get().getRuleFileName();
		
		CashBackExecutor executor = executors.get(ruleFileName);
		
		executor.execute(findFirst.get().getCashBackOfferCode(),String.valueOf(event.getCustomerPk()));
	
		}
		
		System.out.println("recieve events for customer"+event.getCustomerPk());
	}

}
