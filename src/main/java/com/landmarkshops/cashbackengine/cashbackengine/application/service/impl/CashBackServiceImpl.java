package com.landmarkshops.cashbackengine.cashbackengine.application.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.landmarkshops.cashbackengine.cashbackengine.application.service.CashBackService;
import com.landmarkshops.cashbackengine.cashbackengine.domain.model.Customer;
import com.landmarkshops.cashbackengine.cashbackengine.domain.model.Offer;
import com.landmarkshops.cashbackengine.cashbackengine.domain.model.Orders;
import com.landmarkshops.cashbackengine.cashbackengine.domain.model.Orders.OrderStatus;
import com.landmarkshops.cashbackengine.cashbackengine.domain.model.Price;
import com.landmarkshops.cashbackengine.cashbackengine.persistence.CustomerRepository;
import com.landmarkshops.cashbackengine.cashbackengine.presentation.data.OrdersData;
import com.landmarkshops.cashbackengine.cashbackengine.presentation.data.PriceData;

@Component(value = "cashBackService")
public class CashBackServiceImpl implements CashBackService
{

	@Resource
	private MongoTemplate mongoTemplate;

	@Autowired
	private CustomerRepository customerRepository;

	private Function<Orders, OrdersData> ordersMapper =
			order ->
			{
				OrdersData ordersData = new OrdersData();
				BeanUtils.copyProperties(order, ordersData);
				return ordersData;
			};

	@Override
	public void persistOrderDetails(final OrdersData ordersData)
	{
		Customer customer = customerRepository.findOne(ordersData.getCustomerPk());
		if(Objects.isNull(customer))
			customer = new Customer();
		Orders order = constructOrder(ordersData);
		customer
				.withCustomerPk(ordersData.getCustomerPk())
				.withOrders(order)
				.withOffers(new Offer());
		customerRepository.save(customer);
	}

	private Orders constructOrder(OrdersData ordersData)
	{
		final Price price = constructPrice(ordersData);
		final OrderStatus orderStatus = OrderStatus.getOrderStatusForDescription(ordersData.getOrderStatus());
		return Orders
				.builder()
				.orderCode(ordersData.getOrderCode())
				.categories(ordersData.getCategories())
				.orderStatus(orderStatus)
				.price(price)
				.orderCreationTime(ordersData.getOrderCreationTime())
				.build();
	}

	private Price constructPrice(final OrdersData ordersData)
	{
		final PriceData priceData = ordersData.getPrice();
		return Price
				.builder()
				.value(priceData.getValue())
				.currencyISO(priceData.getCurrencyISO())
				.build();
	}

	@Override
	public List<OrdersData> fetchAllOrders()
	{
		List<Customer> orders = customerRepository.findAll();
		if(CollectionUtils.isNotEmpty(orders))
		{
			//return orders.stream().map(ordersMapper).collect(Collectors.toList());
		}
		return Collections.EMPTY_LIST;
	}

}
