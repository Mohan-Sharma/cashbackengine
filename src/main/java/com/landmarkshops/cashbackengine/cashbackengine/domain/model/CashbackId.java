package com.landmarkshops.cashbackengine.cashbackengine.domain.model;

import javax.persistence.Embeddable;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * @author Mohan Sharma Created on 13/07/18.
 */
@Embeddable
@JsonSerialize(using = ToStringSerializer.class)
public class CashbackId
{
	@Id
	private long cashbackId;
}
