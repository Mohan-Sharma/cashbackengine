package com.landmarkshops.cashbackengine.cashbackengine.application.service;

import java.util.List;
import java.util.Set;

import com.landmarkshops.cashbackengine.cashbackengine.presentation.data.OrdersData;

public interface CashBackService
{

	void persistOrderDetails(final OrdersData ordersData);

	List<OrdersData> fetchAllOrders(final Integer durationInDays);

	Set<String> getAllCategoryForCustomer(long customerPK, int durationInDays);
}

