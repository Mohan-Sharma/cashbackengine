package com.landmarkshops.cashbackengine.cashbackengine.domain.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document
@Getter
@Setter
@NoArgsConstructor
public class Orders implements Serializable
{
	@Id
	private String orderCode;
	private String customerPk;
	private String[] categories;
	private Price price;
	
}
