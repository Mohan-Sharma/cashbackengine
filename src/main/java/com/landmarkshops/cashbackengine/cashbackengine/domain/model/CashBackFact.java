package com.landmarkshops.cashbackengine.cashbackengine.domain.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Embeddable
@EqualsAndHashCode(of = "name")
public class CashBackFact implements Serializable
{
	@NotNull
	private String name;
	@NotNull
	private String value;
	private String operator;
}
