/**
 * 
 */
package com.ym.aws.dao;

import com.ym.aws.entity.User;
import com.ym.aws.exception.YMException;

/**
 * @author Pranit.Mhatre
 *
 */
public interface IUserDAO {

	User loadUser(String userName, String password) throws YMException;

	void validateToken(String userName,String token) throws YMException;

	void updateToken(User user) throws YMException;
}
