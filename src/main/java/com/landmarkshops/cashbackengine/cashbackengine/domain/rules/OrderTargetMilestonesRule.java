package com.landmarkshops.cashbackengine.cashbackengine.domain.rules;

import java.util.ArrayList;
import java.util.List;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;

import com.landmarkshops.cashbackengine.cashbackengine.domain.model.ClaimCashBack;
import com.landmarkshops.cashbackengine.cashbackengine.domain.model.Stage;
import com.landmarkshops.cashbackengine.cashbackengine.presentation.data.OrdersData;

@Rule(name="orderTargetMilesstone", description="make target number of orders")
public class OrderTargetMilestonesRule {

	
	@Condition
    public boolean itChecks(@Fact("orders") List<OrdersData> orders,@Fact("thresholdQuantity") Integer thresholdQuantity,@Fact("minOrderValue") Double minOrderValue, @Fact("orderStatus") String orderStatus ) {
		
		Long numberOfOrder = orders.stream().filter(order ->order.getOrderStatus().equals(orderStatus)).filter(order -> order.getPrice().getValue().doubleValue()>=minOrderValue).count();
		
		if(numberOfOrder>0) {
			// update Stage.
			// Search Cash back.
			ClaimCashBack cashBack = new ClaimCashBack();
			cashBack.setStagePosition(numberOfOrder.intValue());
			List<Stage> stages = new ArrayList<>();
			orders.stream().filter(order ->order.getOrderStatus().equals(orderStatus)).filter(order -> order.getPrice().getValue().doubleValue()>=minOrderValue).forEach( order ->{
				Stage stage = new Stage();
				String orderCode = String.valueOf( order.getOrderCode());
				stage.setIdentifier(orderCode);
				stage.setStatus("completed");
				stages.add(stage);
				
			});
			cashBack.setStages(stages);
			cashBack.setStatus("In Progress");
			
			
		}
		if(numberOfOrder>=thresholdQuantity) {
			
			return true;
			
		}else {
			return false;
		}
		
    }
    
   @Action
    public void updateClaimStatus(@Fact("cashbackCode") String cashbackCode,@Fact("customerPk") String customerPk) {
    	
	   ClaimCashBack cashBack = new ClaimCashBack();
	   cashBack.setStatus("Ready To Claim");
	   
    }
}
