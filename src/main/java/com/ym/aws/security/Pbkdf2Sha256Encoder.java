/**
 * 
 */
package com.ym.aws.security;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import com.amazonaws.util.StringUtils;

/**
 * @author Pranit.Mhatre
 *
 */
public class Pbkdf2Sha256Encoder implements IEncoder {

	private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
	private static final int PBKDF2_ITERATIONS = 10000;
	private static final int KEY_LENGTH = 512;
	private static final int SALT_BYTES = 16;

	private static final int ITERATION_INDEX = 0;
	private static final int SALT_INDEX = 1;
	private static final int PBKDF2_INDEX = 2;

	private static final int MIN_ENCRYPTED_HASH_LENGTH = 128;

	@Override
	public String encrypt(String password) throws Exception {
		return generatePasswordHash(password);
	}

	@Override
	public boolean isPasswordValid(String encryptedPassword, String password) throws Exception {
		return isPasswordTheSameAsEncrypted(password, encryptedPassword);
	}

	private boolean isPasswordTheSameAsEncrypted(String password, String encryptedPassword) throws Exception {
		return validatePassword(password, encryptedPassword);
	}

	private String generatePasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		char[] chars = password.toCharArray();
		byte[] salt = getSalt();

		PBEKeySpec spec = new PBEKeySpec(chars, salt, PBKDF2_ITERATIONS, KEY_LENGTH);
		SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
		byte[] hash = skf.generateSecret(spec).getEncoded();
		return PBKDF2_ITERATIONS + ":" + toHex(salt) + ":" + toHex(hash);
	}

	private byte[] getSalt() throws NoSuchAlgorithmException {
		Base64.Encoder encoder = Base64.getEncoder();
		// SecureRandom sr = new SecureRandom();
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[SALT_BYTES];
		sr.nextBytes(salt);
		// return encoder.encode(salt);
		return salt;
	}

	private String toHex(byte[] array) {
		BigInteger bi = new BigInteger(1, array);
		String hex = bi.toString(16);
		int paddingLength = (array.length * 2) - hex.length();
		if (paddingLength > 0) {
			return String.format("%0" + paddingLength + "d", 0) + hex;
		} else {
			return hex;
		}
	}

	private boolean validatePassword(String password, String storedPasswordHash)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		if (StringUtils.isNullOrEmpty(password) || StringUtils.isNullOrEmpty(storedPasswordHash)) {
			return false;
		}
		if (storedPasswordHash.length() < MIN_ENCRYPTED_HASH_LENGTH) {
			return false;
		}

		String[] parts = storedPasswordHash.split(":");
		int iterations = Integer.parseInt(parts[ITERATION_INDEX]);
		byte[] salt = fromHex(parts[SALT_INDEX]);
		byte[] hash = fromHex(parts[PBKDF2_INDEX]);

		PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, hash.length * 8);
		SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
		byte[] testHash = skf.generateSecret(spec).getEncoded();

		int diff = hash.length ^ testHash.length;
		for (int i = 0; i < hash.length && i < testHash.length; i++) {
			diff |= hash[i] ^ testHash[i];
		}
		return diff == 0;
	}

	/**
	 * Converts hex string to byte array.
	 *
	 * @param hex
	 * @return
	 */
	private byte[] fromHex(String hex) {
		byte[] bytes = new byte[hex.length() / 2];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return bytes;
	}

}
