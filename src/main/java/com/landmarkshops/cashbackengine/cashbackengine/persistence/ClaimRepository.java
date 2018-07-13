package com.landmarkshops.cashbackengine.cashbackengine.persistence;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.landmarkshops.cashbackengine.cashbackengine.domain.model.ClaimCashBack;

/**
 * @author Mohan Sharma Created on 13/07/18.
 */
public interface ClaimRepository extends MongoRepository<ClaimCashBack, Long>
{
	ClaimCashBack findOneByCashbackIdAndCustomerPk(final long cashbackId, final long customerPk);

	List<ClaimCashBack> findAllByCustomerPkAndStatusIn(long customerPk, List<String> status);
}
