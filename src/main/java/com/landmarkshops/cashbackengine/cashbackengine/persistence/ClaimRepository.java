package com.landmarkshops.cashbackengine.cashbackengine.persistence;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.landmarkshops.cashbackengine.cashbackengine.domain.model.ClaimCashBack;

/**
 * @author Mohan Sharma Created on 13/07/18.
 */
public interface ClaimRepository extends MongoRepository<ClaimCashBack, String>
{
	ClaimCashBack findOneByCashbackIdAndCustomerPk(final String cashbackId, final String customerPk);

	List<ClaimCashBack> findAllByCustomerPkAndStatusIn(String customerPk, List<String> status);
}
