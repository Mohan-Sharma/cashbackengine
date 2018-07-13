package com.landmarkshops.cashbackengine.cashbackengine.persistence;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.landmarkshops.cashbackengine.cashbackengine.domain.model.CashBackOffer;

/**
 * @author Mohan Sharma Created on 13/07/18.
 */
public interface CashBackOfferRepository extends MongoRepository<CashBackOffer, Long>
{
	List<CashBackOffer> findAllByActive(Boolean active, Sort priority);
}
