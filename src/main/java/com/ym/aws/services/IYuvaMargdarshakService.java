/**
 * 
 */
package com.ym.aws.services;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.ym.aws.entity.ContactUs;
import com.ym.aws.entity.Registration;
import com.ym.aws.exception.YMException;

/**
 * @author Pranit.Mhatre
 *
 */
public interface IYuvaMargdarshakService {

	void register(String jsonBody) throws YMException;
	
	List<Registration> getAllRegistrations()throws YMException;
	
	void saveContactUs(String jsonBody) throws YMException;
	
	List<ContactUs> getAllQueries()throws YMException;
	
	String authenticate(String jsonBody)throws YMException;

}
