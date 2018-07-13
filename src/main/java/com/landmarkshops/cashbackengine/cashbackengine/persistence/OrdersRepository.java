package com.landmarkshops.cashbackengine.cashbackengine.persistence;

import java.math.BigDecimal;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.landmarkshops.cashbackengine.cashbackengine.domain.model.Orders;

/**
 * @author Mohan Sharma Created on 11/07/18.
 */
public interface OrdersRepository extends MongoRepository<Orders, Long>
{
	List<Orders> findAllByCustomerPkAndOrderCreationTimeBetween(long customerPK, LocalDate orderCreationDateFrom, LocalDate orderCreationDateTo);

	List<Orders> findAllByOrderCreationTimeBetween(LocalDate orderCreationDateFrom, LocalDate orderCreationDateTo);

	List<Orders> findAllByCustomerPkAndOrderCreationTimeBetweenAndPriceValueAndPriceCurrencyISO(long customerPk, LocalDate pastDate,
			LocalDate currentDate, BigDecimal value, String currencyISO);
}
