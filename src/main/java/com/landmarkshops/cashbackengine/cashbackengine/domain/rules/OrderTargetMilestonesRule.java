package com.landmarkshops.cashbackengine.cashbackengine.domain.rules;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.landmarkshops.cashbackengine.cashbackengine.domain.model.ClaimCashBack;
import com.landmarkshops.cashbackengine.cashbackengine.domain.model.Stage;
import com.landmarkshops.cashbackengine.cashbackengine.persistence.ClaimRepository;
import com.landmarkshops.cashbackengine.cashbackengine.presentation.data.OrdersData;

@Rule(name="orderTargetMilesstone", description="make target number of orders")
@Component
public class OrderTargetMilestonesRule {

	@Autowired
	private ClaimRepository claimRepository;

	@Condition
	public boolean itChecks(@Fact("orders") List<OrdersData> orders,@Fact("thresholdQuantity") String thresholdQuantity,
			@Fact("minOrderValue") BigDecimal minOrderValue, @Fact("orderStatus") String orderStatus, @Fact("cashbackId") long cashbackId, @Fact("customerPk") long customerPk) {

		Long numberOfOrder = orders.stream().filter(order ->order.getOrderStatus().equals(orderStatus)).filter(order -> order.getPrice().getValue().doubleValue()>=minOrderValue.doubleValue()).count();

		if(numberOfOrder>0)
		{
			// update Stage.
			// Search Cash back.
			ClaimCashBack claim = claimRepository.findOneByCashbackIdAndCustomerPk(cashbackId, customerPk);
			if(Objects.isNull(claim))
			{
				claim = ClaimCashBack.builder().build();
			}
			List<Stage> stages = new ArrayList<>();
			orders.stream().filter(order ->order.getOrderStatus().equals(orderStatus)).filter(order -> order.getPrice().getValue().doubleValue()>=minOrderValue.doubleValue()).forEach( order ->{
				Stage stage = new Stage();
				String orderCode = String.valueOf( order.getOrderCode());
				stage.setIdentifier(orderCode);
				stage.setStatus("completed");
				stages.add(stage);

			});
			claim = claim
					.toBuilder()
					.cashbackId(cashbackId)
					.customerPk(customerPk)
					.status("IN_PROGRESS")
					.stagePosition(numberOfOrder.intValue())
					.stages(stages)
					.build();
			claimRepository.save(claim);
		}
		if(numberOfOrder>=Integer.parseInt(thresholdQuantity)) {

			return true;

		}else {
			return false;
		}

	}

	@Action
	public void updateClaimStatus(@Fact("cashbackCode") long cashbackId,@Fact("customerPk") long customerPk)
	{
		ClaimCashBack claim = claimRepository.findOneByCashbackIdAndCustomerPk(cashbackId, customerPk);
		claim.setStatus("READY_TO_CLAIM");
		claimRepository.save(claim);
	}
}
