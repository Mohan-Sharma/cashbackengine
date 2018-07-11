package com.landmarkshops.cashbackengine.cashbackengine.service.impl;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.landmarkshops.cashbackengine.cashbackengine.entity.Orders;
import com.landmarkshops.cashbackengine.cashbackengine.service.LMGCashBackService;
import com.mongodb.DBCollection;

@Component
public class LMGCashBackServiceImpl implements LMGCashBackService {

	@Resource
	private MongoTemplate mongoTemplate;
	
	@Override
	public void pushTodb(String orderNumber, String customerPK, String[] categories) {
		
		DBCollection orderCollection = null;
		String collectionName = null;
		
		if (!mongoTemplate.collectionExists(Orders.class)) {

			orderCollection = mongoTemplate.createCollection(Orders.class);
		} else {
			collectionName = mongoTemplate.getCollectionName(Orders.class);

			orderCollection = mongoTemplate.getCollection(collectionName);

		}
		
		Orders doc = new Orders();
		doc.setCustomerPk(customerPK);
		doc.setOrderNumber(orderNumber);
		mongoTemplate.insert(doc,collectionName);
		
	}

}
