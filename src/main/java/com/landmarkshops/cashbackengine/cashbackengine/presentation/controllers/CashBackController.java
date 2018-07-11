package com.landmarkshops.cashbackengine.cashbackengine.presentation.controllers;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.landmarkshops.cashbackengine.cashbackengine.application.service.CashBackService;
import com.landmarkshops.cashbackengine.cashbackengine.presentation.data.OrdersData;

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
}
