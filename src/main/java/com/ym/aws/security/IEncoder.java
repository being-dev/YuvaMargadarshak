/**
 * 
 */
package com.ym.aws.security;

/**
 * @author Pranit.Mhatre
 *
 */
public interface IEncoder {

	String encrypt(String password) throws Exception;

    boolean isPasswordValid(String encryptedPassword, String password) throws Exception;
}
