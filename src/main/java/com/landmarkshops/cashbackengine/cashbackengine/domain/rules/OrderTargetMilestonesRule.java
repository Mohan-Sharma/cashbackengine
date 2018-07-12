package com.landmarkshops.cashbackengine.cashbackengine.domain.rules;

import java.util.List;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;

import com.landmarkshops.cashbackengine.cashbackengine.presentation.data.OrdersData;

@Rule(name="orderTargetMilesstone", description="make target number of orders")
public class OrderTargetMilestonesRule {

	
	@Condition
    public boolean itChecks(@Fact("orders") List<OrdersData> orders,@Fact("thresholdQuantity") Integer thresholdQuantity,@Fact("minOrderValue") Double minOrderValue, @Fact("orderStatus") String orderStatus ) {
		
		return orders.stream().filter(order ->order.getOrderStatus().equals(orderStatus)).filter(order -> order.getPrice().getValue().doubleValue()>=minOrderValue).count()>=thresholdQuantity;
       
    }
    
   @Action
    public void updateClaimStatus() {
    	
        System.out.println("Claim Order");
    }
}
