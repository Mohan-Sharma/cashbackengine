package com.landmarkshops.cashbackengine.cashbackengine.application.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import com.landmarkshops.cashbackengine.cashbackengine.domain.model.CashBackOffer;
import com.landmarkshops.cashbackengine.cashbackengine.presentation.data.CashBackOfferData;
import com.landmarkshops.cashbackengine.cashbackengine.presentation.data.OrdersData;
import com.landmarkshops.cashbackengine.cashbackengine.presentation.data.PriceData;

public interface CashBackService
{

	void persistOrderDetails(final OrdersData ordersData);

	List<OrdersData> fetchAllOrdersForCustomer(final String customerPk, final Integer durationInDays);

	List<OrdersData> fetchAllOrdersForCustomerGreaterThanThresholdPrice(final String customerPk, final Integer durationInDays, final BigDecimal minOrderValue, String currencyISO);

	Set<String> getAllCategoryForCustomer(String customerPK, int durationInDays);

	List<CashBackOffer> getAllActiveCashBackOffersNotClaimedByUser(String customerPk);

	void persistCashbackOffer(final CashBackOfferData cashBackOfferData);
}

