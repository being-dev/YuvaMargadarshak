/**
 * 
 */
package com.ym.aws.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.UpdateItemRequest;
import com.ym.aws.entity.User;
import com.ym.aws.exception.YMException;

/**
 * @author Pranit.Mhatre
 *
 */
public class UserDAOImpl extends AbstractDAO<User> implements IUserDAO {

	public UserDAOImpl(String tableName) {
		super(tableName);
	}

	@Override
	public User loadUser(String userName, String password) throws YMException {

		Map<String, String> keyMap = new HashMap<String, String>();
		keyMap.put("#userName", "user_name");

		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put(":userName", userName);

		try {
			User user = null;
			QuerySpec criteria = new QuerySpec().withKeyConditionExpression("#userName = :userName").withNameMap(keyMap)
					.withValueMap(valueMap);

			ItemCollection<QueryOutcome> items = getTable().query(criteria);
			Iterator<Item> iterator = items.iterator();
			if (iterator.hasNext()) {
				Item item = iterator.next();
				user = new User();
				user.setUserId(item.getLong("user_id"));
				user.setUserName(item.getString("user_name"));
				user.setPassword(item.getString("password"));
				if (item.hasAttribute("is_admin")) {
					user.setAdmin(item.getBoolean("is_admin"));
				}
				if (item.hasAttribute("user_token")) {
					user.setToken(item.getString("user_token"));
				}
			} else {
				throw new YMException(String.format("Username not found %s", userName));
			}

			return user;

		} catch (YMException ex) {
			throw ex;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			throw new YMException(String.format("Username not found %s", userName));
		}
	}

	@Override
	public void updateToken(User user) throws YMException {
		try {
			Map<String, AttributeValue> keys = new HashMap<>();
			keys.put("user_name", new AttributeValue().withS(user.getUserName()));

			Map<String, AttributeValue> values = new HashMap<>();
			values.put(":token", new AttributeValue().withS(user.getToken()));

			UpdateItemRequest updateItemRequest = new UpdateItemRequest().withTableName(getTable().getTableName())
					.withKey(keys);
			updateItemRequest.withUpdateExpression("SET user_token = :token");
			updateItemRequest.withExpressionAttributeValues(values);

			CLIENT.updateItem(updateItemRequest);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new YMException(
					String.format("Unable to update user token in database for user %s", user.getUserName()));
		}
	}

	@Override
	public void validateToken(String userName,String token) throws YMException {
		try {

			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put(":userName", userName);
			valueMap.put(":token", new AttributeValue().withS(token));

			QuerySpec criteria = new QuerySpec().withKeyConditionExpression("user_name = :userName")
					.withFilterExpression("user_token = :token").withValueMap(valueMap);
			ItemCollection<QueryOutcome> items = getTable().query(criteria);
			Iterator<Item> iterator = items.iterator();
			if (!iterator.hasNext()) {
				throw new YMException("Unable to authenticate user, invalid user token provided.");
			}

		} catch (Exception ex) {
			throw new YMException(String.format("Unable to validate user token for user %s. Please try again later.",
					userName));
		}
	}

}
