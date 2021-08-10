/**
 * 
 */
package com.ym.aws.entity;

import java.io.Serializable;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * @author Pranit.Mhatre
 *
 */
@DynamoDBTable(tableName = "tb_user")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2091665850917768306L;
	@DynamoDBAttribute(attributeName = "user_id")
	private Long userId;
	@DynamoDBHashKey(attributeName = "user_name")
	private String userName;
	@DynamoDBAttribute(attributeName = "password")
	private String password;
	@DynamoDBAttribute(attributeName = "is_admin")
	private Boolean admin;
	@DynamoDBAttribute(attributeName = "user_token")
	private String token;

	public User() {

	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [userId=");
		builder.append(userId);
		builder.append(", userName=");
		builder.append(userName);
		builder.append(", password=");
		builder.append(password);
		builder.append(", admin=");
		builder.append(admin);
		builder.append(", token=");
		builder.append(token);
		builder.append("]");
		return builder.toString();
	}

}
