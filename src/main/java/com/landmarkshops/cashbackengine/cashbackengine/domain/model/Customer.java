package com.landmarkshops.cashbackengine.cashbackengine.domain.model;

import java.io.Serializable;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.google.common.collect.Sets;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Mohan Sharma Created on 12/07/18.
 */
@NoArgsConstructor
@Getter
@Document
@EqualsAndHashCode(of = "customerPK")
public class Customer  implements Serializable
{
	@Id
	@NotNull
	private long customerPk;
	private Set<Orders> orders;
	private Set<Offer> offers;

	public Customer withCustomerPk(final long customerPk)
	{
		this.customerPk = customerPk;
		return this;
	}

	public Customer withOrders(final Orders orders)
	{
		if(CollectionUtils.isEmpty(this.orders))
			this.orders = Sets.newHashSet();
		if(this.orders.contains(orders))
			this.orders.remove(orders);
		this.orders.add(orders);
		return this;
	}

	public Customer withOffers(final Offer offer)
	{
		if(CollectionUtils.isEmpty(this.offers))
			this.offers = Sets.newHashSet();
		if(this.offers.contains(offer))
			this.offers.remove(offer);
		this.offers.add(offer);
		return this;
	}
}
