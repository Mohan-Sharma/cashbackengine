package com.landmarkshops.cashbackengine.cashbackengine.domain.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Stage implements Serializable
{
	private String status;
	private String identifier;
}
