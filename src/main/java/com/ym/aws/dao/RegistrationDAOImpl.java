/**
 * 
 */
package com.ym.aws.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.ym.aws.entity.Registration;

/**
 * @author Pranit.Mhatre
 *
 */
public class RegistrationDAOImpl extends AbstractDAO<Registration> implements IRegistrationDAO {

	private static final String TABLE_NAME = "tb_candidate";
	private static final Table TABLE = DYNAMO_DB.getTable(TABLE_NAME);
	private static final DynamoDBMapper DYNAMODB_MAPPER = new DynamoDBMapper(CLIENT);
	
	public RegistrationDAOImpl(String tableName) {
		super(tableName);
	}
	
}
