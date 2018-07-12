package com.landmarkshops.cashbackengine.cashbackengine.application.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.landmarkshops.cashbackengine.cashbackengine.application.service.CashBackService;
import com.landmarkshops.cashbackengine.cashbackengine.domain.model.Orders;
import com.landmarkshops.cashbackengine.cashbackengine.domain.model.Orders.OrderStatus;
import com.landmarkshops.cashbackengine.cashbackengine.domain.model.Price;
import com.landmarkshops.cashbackengine.cashbackengine.persistence.OrdersRepository;
import com.landmarkshops.cashbackengine.cashbackengine.presentation.data.OrdersData;
import com.landmarkshops.cashbackengine.cashbackengine.presentation.data.PriceData;

@Component(value = "cashBackService")
public class CashBackServiceImpl implements CashBackService
{

	@Resource
	private MongoTemplate mongoTemplate;

	@Autowired
	private OrdersRepository ordersRepository;

	private Function<Price, PriceData> priceMapper =
			price -> PriceData
					.builder()
					.value(price.getValue())
					.currencyISO(price.getCurrencyISO())
					.build();

	private Function<Orders, OrdersData> ordersMapper =
			order ->
				OrdersData
						.builder()
						.orderCode(order.getOrderCode())
						.categories(order.getCategories())
						.customerPk(order.getCustomerPk())
						.orderCreationTime(order.getOrderCreationTime())
						.orderStatus(order.getOrderStatus().getDescription())
						.price(priceMapper.apply(order.getPrice()))
						.build();

	@Override
	public void persistOrderDetails(final OrdersData ordersData)
	{
		Orders orders = ordersRepository.findOne(ordersData.getOrderCode());
		if(Objects.isNull(orders))
			orders = new Orders();
		orders = populateWithOrderData(orders, ordersData);
		ordersRepository.save(orders);
	}

	private Orders populateWithOrderData(final Orders orders, final OrdersData ordersData)
	{
		final Price price = constructPrice(ordersData);
		final OrderStatus orderStatus = OrderStatus.getOrderStatusForDescription(ordersData.getOrderStatus());
		return orders
				.toBuilder()
				.orderCode(ordersData.getOrderCode())
				.categories(ordersData.getCategories())
				.orderStatus(orderStatus)
				.price(price)
				.orderCreationTime(ordersData.getOrderCreationTime())
				.customerPk(ordersData.getCustomerPk())
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
		List<Orders> orders = ordersRepository.findAll();
		if(CollectionUtils.isNotEmpty(orders))
		{
			return orders.stream().map(ordersMapper).collect(Collectors.toList());
		}
		return Collections.EMPTY_LIST;
	}

	@Override
	public Set<String> getAllCategoryForCustomer(long customerPK, int durationInDays)
	{
		LocalDate currentDate = LocalDate.now();
		LocalDate pastDate = currentDate.minusDays(durationInDays);
		List<Orders> orders = ordersRepository.findAllByCustomerPkAndOrderCreationTimeBetween(customerPK, pastDate, currentDate);
		if(CollectionUtils.isNotEmpty(orders))
			return orders.stream().map(Orders::getCategories).flatMap(Stream::of).collect(Collectors.toSet());
		return Collections.EMPTY_SET;
	}

}
