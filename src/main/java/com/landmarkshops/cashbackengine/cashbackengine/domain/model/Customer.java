package com.landmarkshops.cashbackengine.cashbackengine.domain.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Embeddable;
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
@Embeddable
@EqualsAndHashCode(of = "customerPK")
@Builder
@AllArgsConstructor
public class Customer implements Serializable
{
	@NotNull
	private long customerPk;

	public Customer withCustomerPk(final long customerPk)
	{
		this.customerPk = customerPk;
		return this;
	}
}
