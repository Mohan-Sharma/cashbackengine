package com.landmarkshops.cashbackengine.cashbackengine.application.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.landmarkshops.cashbackengine.cashbackengine.domain.model.Orders;
import com.landmarkshops.cashbackengine.cashbackengine.application.service.CashBackService;
import com.landmarkshops.cashbackengine.cashbackengine.persistence.OrdersRepository;
import com.landmarkshops.cashbackengine.cashbackengine.presentation.data.OrdersData;
import com.mongodb.DBCollection;

@Component(value = "cashBackService")
public class CashBackServiceImpl implements CashBackService
{

	@Resource
	private MongoTemplate mongoTemplate;

	@Autowired
	private OrdersRepository ordersRepository;

	private Function<Orders, OrdersData> ordersMapper =
			order ->
			{
				OrdersData ordersData = new OrdersData();
				BeanUtils.copyProperties(order, ordersData);
				return ordersData;
			};
	
	@Override
	public void pushTodb(final OrdersData ordersData)
	{
		Orders orders = new Orders();
		BeanUtils.copyProperties(ordersData, orders);
		ordersRepository.save(orders);
	}

	@Override
	public List<OrdersData> fetchAllOrders()
	{
		List<Orders> orders = ordersRepository.findAll();
		if(CollectionUtils.isNotEmpty(orders))
		{
			return orders.stream().map(ordersMapper).collect(Collectors.toList());
		}
		return Collections.EMPTY_LIST;
	}

}
