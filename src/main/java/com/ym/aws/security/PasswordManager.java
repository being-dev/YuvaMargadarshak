/**
 * 
 */
package com.ym.aws.security;

/**
 * @author Pranit.Mhatre
 *
 */
public final class PasswordManager {

	private final static IEncoder encoder = new Pbkdf2Sha256Encoder();

	private PasswordManager() {
		throw new IllegalStateException("Cannot access final utility class.");
	}

	public static boolean isPasswordValid(String encryptedPassword, String password) throws Exception {
		return encoder.isPasswordValid(encryptedPassword, password);
	}

	public static String encrypt(String password) throws Exception {
		return encoder.encrypt(password);
	}
}
