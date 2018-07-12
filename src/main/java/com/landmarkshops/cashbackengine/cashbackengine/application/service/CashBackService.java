package com.landmarkshops.cashbackengine.cashbackengine.application.service;

import java.util.List;

import com.landmarkshops.cashbackengine.cashbackengine.presentation.data.OrdersData;

public interface CashBackService
{

	void persistOrderDetails(final OrdersData ordersData);

	List<OrdersData> fetchAllOrders();
}

