package com.ym.aws.entity;

import java.io.Serializable;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * @author Pranit.Mhatre
 *
 */
@DynamoDBTable(tableName = "tb_candidate")
public class Registration implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5241348489333229190L;

	@DynamoDBHashKey(attributeName = "uuid")
	@DynamoDBAutoGeneratedKey
	private String id;
	@DynamoDBAttribute(attributeName = "cand_info")
	private Map<String, String> details;

	public Registration() {

	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the details
	 */
	public Map<String, String> getDetails() {
		return details;
	}

	/**
	 * @param details the details to set
	 */
	public void setDetails(Map<String, String> details) {
		this.details = details;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Registration [id=");
		builder.append(id);
		builder.append(", details=");
		builder.append(details);
		builder.append("]");
		return builder.toString();
	}

}