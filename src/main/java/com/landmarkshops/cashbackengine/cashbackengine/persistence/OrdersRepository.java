package com.landmarkshops.cashbackengine.cashbackengine.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.landmarkshops.cashbackengine.cashbackengine.domain.model.Orders;

/**
 * @author Mohan Sharma Created on 11/07/18.
 */
public interface OrdersRepository extends MongoRepository<Orders, String>
{
}
