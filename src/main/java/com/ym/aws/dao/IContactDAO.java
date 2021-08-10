/**
 * 
 */
package com.ym.aws.dao;

import java.util.List;

import com.ym.aws.entity.ContactUs;
import com.ym.aws.exception.YMException;

/**
 * @author Pranit.Mhatre
 *
 */
public interface IContactDAO {

	ContactUs save(ContactUs contactUs) throws YMException;

	List<ContactUs> findAll() throws YMException;
}
