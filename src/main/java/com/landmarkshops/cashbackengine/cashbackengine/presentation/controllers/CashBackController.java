package com.landmarkshops.cashbackengine.cashbackengine.presentation.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import javax.annotation.Resource;

import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.landmarkshops.cashbackengine.cashbackengine.application.service.CashBackService;
import com.landmarkshops.cashbackengine.cashbackengine.domain.rules.OrderTargetMileStoneRuleListener;
import com.landmarkshops.cashbackengine.cashbackengine.domain.rules.OrderTargetMilestonesRule;
import com.landmarkshops.cashbackengine.cashbackengine.presentation.data.OrdersData;
import com.landmarkshops.cashbackengine.cashbackengine.presentation.data.PriceData;

@Controller
@RequestMapping(value = "/cashback")
public class CashBackController
{

	@Resource
	private CashBackService cashBackService;
	
	@RequestMapping(value = "/postEvent", method = RequestMethod.POST)
	public @ResponseBody String publishOrderPlaceEvent(@RequestBody final OrdersData ordersData)
	{
		cashBackService.pushTodb(ordersData);
		return "Success";
	}

	@RequestMapping(value = "/getOrders", method = RequestMethod.GET)
	public @ResponseBody List<OrdersData> findAllOrders()
	{
		return cashBackService.fetchAllOrders();
	}
	
	
	@RequestMapping(value = "/testRule", method = RequestMethod.GET)
	public @ResponseBody List<OrdersData> testRule()
	{
			List<OrdersData> fetchAllOrders = new ArrayList<>();
			OrdersData data = new OrdersData();
			data.setOrderCode("2312");
			data.setOrderStatus("delivered");
			PriceData price = new PriceData();
			price.setCurrencyISO("INR");
			price.setValue(new BigDecimal("500"));
			data.setPrice(price);
			
			IntStream.range(1, 20).forEach( i ->fetchAllOrders.add(data) );
			
			Facts facts = new Facts();
			facts.put("orders", fetchAllOrders);
			facts.put("thresholdQuantity", 10);
			facts.put("orderStatus", "delivered");
			facts.put("minOrderValue", 500.0);
			
			Rules rules = new Rules();
			rules.register(new OrderTargetMilestonesRule());
			
			RulesEngine rulesEngine = new DefaultRulesEngine();
			rulesEngine.getRuleListeners().add(new OrderTargetMileStoneRuleListener());
		    rulesEngine.fire(rules, facts);
	        
		
		return fetchAllOrders;
	}
}
