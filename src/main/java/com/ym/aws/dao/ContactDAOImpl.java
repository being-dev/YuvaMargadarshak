/**
 * 
 */
package com.ym.aws.dao;

import com.ym.aws.entity.ContactUs;

/**
 * @author Pranit.Mhatre
 *
 */
public class ContactDAOImpl extends AbstractDAO<ContactUs> implements IContactDAO {

	public ContactDAOImpl(String tableName) {
		super(tableName);
	}

}
