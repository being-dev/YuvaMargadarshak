/**
 * 
 */
package com.ym.aws.dao;

import java.util.List;

import com.ym.aws.entity.Registration;
import com.ym.aws.exception.YMException;

/**
 * @author Pranit.Mhatre
 *
 */
public interface IRegistrationDAO {
	
	Registration save(Registration registration) throws YMException;
	
	List<Registration> findAll() throws YMException;

}
