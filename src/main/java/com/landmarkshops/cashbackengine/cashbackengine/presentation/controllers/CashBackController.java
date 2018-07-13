package com.landmarkshops.cashbackengine.cashbackengine.presentation.controllers;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import javax.annotation.Resource;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.landmarkshops.cashbackengine.cashbackengine.application.events.OrderReceivedEventPublisher;
import com.landmarkshops.cashbackengine.cashbackengine.application.service.CashBackService;
import com.landmarkshops.cashbackengine.cashbackengine.domain.model.CashBackOffer;
import com.landmarkshops.cashbackengine.cashbackengine.domain.rules.OrderTargetMileStoneRuleListener;
import com.landmarkshops.cashbackengine.cashbackengine.presentation.data.CashBackOfferData;
import com.landmarkshops.cashbackengine.cashbackengine.presentation.data.CustomerData;
import com.landmarkshops.cashbackengine.cashbackengine.presentation.data.OrdersData;
import com.landmarkshops.cashbackengine.cashbackengine.presentation.data.PriceData;

@Controller
@RequestMapping(value = "/cashback")
public class CashBackController
{

	@Resource
	private CashBackService cashBackService;

	@Resource
	private OrderReceivedEventPublisher OrderReceivedEventPublisher;
	
	@RequestMapping(value = "/postOrder", method = RequestMethod.POST)
	public @ResponseBody String publishOrderPlaceEvent(@RequestBody final OrdersData ordersData)
	{
		cashBackService.persistOrderDetails(ordersData);
		OrderReceivedEventPublisher.publish(ordersData.getCustomerPk());
		return "Success";
	}

	@RequestMapping(value = "/getOrders", method = RequestMethod.GET)
	public @ResponseBody List<OrdersData> findAllOrders(@RequestParam(required = false) final Integer durationInDays)
	{
		return null;
	}

	@RequestMapping(value = "/getClaimsAndOffersForCustomer", method = RequestMethod.GET)
	public @ResponseBody CustomerData getClaimsAndOffersForCustomer(@RequestParam final String customerPk)
	{
		return cashBackService.getClaimsAndOffersForCustomer(customerPk);
	}

	@RequestMapping(value = "/getAllCategoryForCustomer", method = RequestMethod.GET)
	public @ResponseBody Set<String> getAllCategoryForCustomer(@RequestParam final String customerPK, @RequestParam final int durationInDays)
	{
		return cashBackService.getAllCategoryForCustomer(customerPK, durationInDays);
	}
	
	
	@RequestMapping(value = "/getCashBackOffers", method = RequestMethod.GET)
	public @ResponseBody List<CashBackOffer> findCashBackOffers(@RequestParam("customerPk") String customerPk )
	{
		List<CashBackOffer> cbOffers = new ArrayList<>();
		
		return cbOffers;
	}

	@RequestMapping(value = "/createOffer", method = RequestMethod.POST)
	public @ResponseBody String createOffer(@RequestBody final CashBackOfferData cashBackOffer)
	{
		cashBackService.persistCashbackOffer(cashBackOffer);
		return "Success";
	}

	@RequestMapping(value = "/testRule", method = RequestMethod.GET)
	public @ResponseBody List<OrdersData> testRule() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
			List<OrdersData> fetchAllOrders = new ArrayList<>();
			OrdersData data = new OrdersData();
			data.setOrderCode(2312L);
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
			Class<?> clazz = Class.forName("com.landmarkshops.cashbackengine.cashbackengine.domain.rules.OrderTargetMilestonesRule");
			Object obj = clazz.getConstructor().newInstance();
			rules.register(obj);
			
			RulesEngine rulesEngine = new DefaultRulesEngine();
			rulesEngine.getRuleListeners().add(new OrderTargetMileStoneRuleListener());
		    rulesEngine.fire(rules, facts);
	        
		
		return fetchAllOrders;
	}
}
