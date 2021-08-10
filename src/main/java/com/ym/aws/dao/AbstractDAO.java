/**
 * 
 */
package com.ym.aws.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.ym.aws.exception.YMException;

/**
 * @author Pranit.Mhatre
 *
 */
abstract class AbstractDAO<T extends Serializable> {

	public static final AmazonDynamoDB CLIENT = AmazonDynamoDBClientBuilder.standard().build();

	public static final DynamoDB DYNAMO_DB = new DynamoDB(CLIENT);
	private Table TABLE = null;
	private DynamoDBMapper DYNAMODB_MAPPER = null;

	protected AbstractDAO(String tableName) {
		TABLE = DYNAMO_DB.getTable(tableName);
		DYNAMODB_MAPPER = new DynamoDBMapper(CLIENT);
	}

	public T save(T obj) throws YMException {
		try {
			DYNAMODB_MAPPER.save(obj);
			return obj;
		} catch (Exception ex) {
			throw new YMException(ex);
		}
	}

	public List<T> findAll() throws YMException {
		Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		DynamoDBScanExpression scanExp = new DynamoDBScanExpression();
		return DYNAMODB_MAPPER.scan(clazz, scanExp);
	}
	
	public Table getTable() {
		return TABLE;
	}
}
