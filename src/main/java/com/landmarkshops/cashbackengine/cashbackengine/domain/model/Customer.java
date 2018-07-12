package com.landmarkshops.cashbackengine.cashbackengine.domain.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Mohan Sharma Created on 12/07/18.
 */
@NoArgsConstructor
@Getter
@Setter
@Document
public class Customer extends Auditable implements Serializable
{
	@Id
	@NotNull
	private long customerPK;

}
