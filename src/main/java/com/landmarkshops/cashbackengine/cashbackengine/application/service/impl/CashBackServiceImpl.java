package com.landmarkshops.cashbackengine.cashbackengine.application.service.impl;

import java.math.BigDecimal;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.landmarkshops.cashbackengine.cashbackengine.application.service.CashBackService;
import com.landmarkshops.cashbackengine.cashbackengine.domain.model.CashBackOffer;
import com.landmarkshops.cashbackengine.cashbackengine.domain.model.ClaimCashBack;
import com.landmarkshops.cashbackengine.cashbackengine.domain.model.Orders;
import com.landmarkshops.cashbackengine.cashbackengine.domain.model.Orders.OrderStatus;
import com.landmarkshops.cashbackengine.cashbackengine.domain.model.Price;
import com.landmarkshops.cashbackengine.cashbackengine.persistence.CashBackOfferRepository;
import com.landmarkshops.cashbackengine.cashbackengine.persistence.ClaimRepository;
import com.landmarkshops.cashbackengine.cashbackengine.persistence.OrdersRepository;
import com.landmarkshops.cashbackengine.cashbackengine.presentation.data.CashBackClaimData;
import com.landmarkshops.cashbackengine.cashbackengine.presentation.data.CashBackOfferData;
import com.landmarkshops.cashbackengine.cashbackengine.presentation.data.CustomerData;
import com.landmarkshops.cashbackengine.cashbackengine.presentation.data.OrdersData;
import com.landmarkshops.cashbackengine.cashbackengine.presentation.data.PriceData;

@Component(value = "cashBackService")
public class CashBackServiceImpl implements CashBackService
{

	@Resource
	private MongoTemplate mongoTemplate;

	@Autowired
	private OrdersRepository ordersRepository;

	@Autowired
	private CashBackOfferRepository cashBackOfferRepository;

	@Autowired
	private ClaimRepository claimRepository;

	private Function<Price, PriceData> priceDataMapper =
			price -> PriceData
					.builder()
					.value(price.getValue())
					.currencyISO(price.getCurrencyISO())
					.build();
	private Function<PriceData, Price> priceMapper =
			price -> Price
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
							.price(priceDataMapper.apply(order.getPrice()))
							.build();

	private Function<CashBackOffer, CashBackOfferData> cashBackOfferMapper =
			cashBackOffer ->
					CashBackOfferData
							.builder()
							.cashbackId(cashBackOffer.getCashbackId())
							.active(cashBackOffer.isActive())
							.claimAmount(priceDataMapper.apply(cashBackOffer.getClaimAmount()))
							.description(cashBackOffer.getDescription())
							.offerName(cashBackOffer.getOfferName())
							.priority(cashBackOffer.getPriority())
							.ruleFileName(cashBackOffer.getRuleFileName())
							.cashBackFacts(cashBackOffer.getCashBackFacts())
							.build();


	private Function<ClaimCashBack, CashBackClaimData> claimCashBackMapper =
			claimCashBack -> CashBackClaimData
					.builder()
					.claimId(claimCashBack.getClaimId())
					.stagePosition(claimCashBack.getStagePosition())
					.status(claimCashBack.getStatus())
					.customerPk(claimCashBack.getCustomerPk())
					.cashbackId(claimCashBack.getCashbackId())
					.stages(claimCashBack.getStages())
					.build();

	@Override public void persistOrderDetails(final OrdersData ordersData)
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

	@Override public List<OrdersData> fetchAllOrdersForCustomer(final String customerPk, final Integer durationInDays)
	{
		List<Orders> orders;
		if(Objects.isNull(durationInDays))
			orders = ordersRepository.findAll();
		else
		{
			LocalDate currentDate = LocalDate.now();
			LocalDate pastDate = currentDate.minusDays(durationInDays);
			orders = ordersRepository.findAllByCustomerPkAndOrderCreationTimeBetween(customerPk, pastDate, currentDate);
		}
		if(CollectionUtils.isNotEmpty(orders))
		{
			return orders.stream().map(ordersMapper).collect(Collectors.toList());
		}
		return Collections.EMPTY_LIST;
	}

	@Override public List<OrdersData> fetchAllOrdersForCustomerGreaterThanThresholdPrice(String customerPk,
			Integer durationInDays, BigDecimal minOrderValue, String currencyISO)
	{
		List<Orders> orders;
		if(Objects.isNull(durationInDays))
			orders = ordersRepository.findAll();
		else
		{
			LocalDate currentDate = LocalDate.now();
			LocalDate pastDate = currentDate.minusDays(durationInDays);
			orders = ordersRepository.findAllByCustomerPkAndOrderCreationTimeBetweenAndPriceValueAndPriceCurrencyISO(customerPk, pastDate, currentDate, minOrderValue, currencyISO);
		}
		if(CollectionUtils.isNotEmpty(orders))
		{
			return orders.stream().map(ordersMapper).collect(Collectors.toList());
		}
		return Collections.EMPTY_LIST;
	}

	@Override public Set<String> getAllCategoryForCustomer(String customerPK, int durationInDays)
	{
		LocalDate currentDate = LocalDate.now();
		LocalDate pastDate = currentDate.minusDays(durationInDays);
		List<Orders> orders = ordersRepository.findAllByCustomerPkAndOrderCreationTimeBetween(customerPK, pastDate, currentDate.plusDays(1));
		if(CollectionUtils.isNotEmpty(orders))
			return orders.stream().map(Orders::getCategories).flatMap(Stream::of).collect(Collectors.toSet());
		return Collections.EMPTY_SET;
	}

	@Override public List<CashBackOffer> getAllActiveCashBackOffersNotClaimedByUser(String customerPk) {
		final Set<String> claimedOffers = Sets.newHashSet();
		List<CashBackOffer> cashBackOffers = cashBackOfferRepository.findAllByActive(Boolean.TRUE, new Sort(new Sort.Order(Sort.Direction.ASC, "priority")));
		List<ClaimCashBack> claims = claimRepository.findAllByCustomerPkAndStatusIn(customerPk, Lists.newArrayList("CLAIMED", "READY_TO_CLAIM"));
		if(CollectionUtils.isNotEmpty(claims))
			claims
					.stream()
					.forEach(claim -> claimedOffers.add(claim.getCashbackId()));
		if(CollectionUtils.isNotEmpty(cashBackOffers))
			cashBackOffers = cashBackOffers.stream().filter(offer -> !claimedOffers.contains(offer.getCashbackId())).collect(Collectors.toList());
		return cashBackOffers;
	}

	@Override public void persistCashbackOffer(CashBackOfferData cashBackOfferData)
	{
		CashBackOffer cashBackOffer = CashBackOffer.builder().build();
		BeanUtils.copyProperties(cashBackOfferData, cashBackOffer);
		cashBackOffer.setClaimAmount(priceMapper.apply(cashBackOfferData.getClaimAmount()));
		cashBackOfferRepository.save(cashBackOffer);
	}

	@Override public CustomerData getClaimsAndOffersForCustomer(String customerPk)
	{
		final CustomerData customerData = new CustomerData();
		customerData.setCustomerPk(customerPk);
		List<CashBackOffer> cashBackOffers = cashBackOfferRepository.findAllByActive(Boolean.TRUE, new Sort(new Sort.Order(Sort.Direction.ASC, "priority")));
		List<ClaimCashBack> claims = claimRepository.findAllByCustomerPk(customerPk);
		if(CollectionUtils.isNotEmpty(cashBackOffers))
		{
			List<CashBackOfferData> cashBackOfferData = cashBackOffers.stream().map(cashBackOfferMapper::apply).collect(Collectors.toList());
			customerData.setCashBackOfferData(cashBackOfferData);
		}
		if(CollectionUtils.isNotEmpty(claims))
		{
			List<CashBackClaimData> cashBackClaimData =  claims.stream().map(claimCashBackMapper).collect(Collectors.toList());
			customerData.setCashBackClaimData(cashBackClaimData);
		}
		return customerData;
	}

	@Override public PriceData getClaimAmountFromCustomer(final String claimId, final String customerPk)
	{
		ClaimCashBack claimCashBack = claimRepository.findOneByClaimIdAndCustomerPk(claimId, customerPk);
		if(Objects.nonNull(claimCashBack))
		{
			CashBackOffer cashBackOffer = cashBackOfferRepository
					.findOneByCashbackIdAndActive(claimCashBack.getCashbackId(), Boolean.TRUE);
			if (Objects.nonNull(cashBackOffer))
			{
				Price price = cashBackOffer.getClaimAmount();
				return priceDataMapper.apply(price);
			}
		}
		return null;
	}

}
