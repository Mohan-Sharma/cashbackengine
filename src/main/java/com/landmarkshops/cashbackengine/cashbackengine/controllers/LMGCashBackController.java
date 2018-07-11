package com.landmarkshops.cashbackengine.cashbackengine.controllers;

import javax.annotation.Resource;

import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.landmarkshops.cashbackengine.cashbackengine.service.LMGCashBackService;

@Controller("/")
public class LMGCashBackController {

	@Resource
	private LMGCashBackService lmgCashBackService;
	
	@RequestMapping("/postEvent")
	public @ResponseBody String publishOrderPlaceEvent(String orderCode,String customerPk,String[] categories,String totalprice) throws JSONException {
		
		System.out.println("Recived Order placeEvent");
		
		
		lmgCashBackService.pushTodb(orderCode, customerPk, categories);
		
		return "Success";
	}
}
