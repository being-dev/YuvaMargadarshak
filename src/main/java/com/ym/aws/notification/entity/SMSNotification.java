/**
 * 
 */
package com.ym.aws.notification.entity;

import java.io.Serializable;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * @author Pranit.Mhatre
 *
 */
@DynamoDBTable(tableName = "tb_notification")
public class SMSNotification implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6739583912695850451L;

	@DynamoDBAttribute(attributeName = "id")
	private Long id;
	@DynamoDBRangeKey(attributeName = "emp_uuid")
	private String employee;
	@DynamoDBRangeKey(attributeName = "emp_mobile")
	private String mobile;
	@DynamoDBAttribute(attributeName = "sms_type")
	private String type;
	@DynamoDBAttribute(attributeName = "sms_text")
	private String message;
	@DynamoDBAttribute(attributeName = "created_date")
	private String createdDate;

	public SMSNotification() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SMSNotification [id=");
		builder.append(id);
		builder.append(", employee=");
		builder.append(employee);
		builder.append(", mobile=");
		builder.append(mobile);
		builder.append(", type=");
		builder.append(type);
		builder.append(", message=");
		builder.append(message);
		builder.append(", createdDate=");
		builder.append(createdDate);
		builder.append("]");
		return builder.toString();
	}

}
