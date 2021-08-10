/**
 * 
 */
package com.ym.aws.exception;

/**
 * @author Pranit.Mhatre
 *
 */
public class YMException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3557909080386104688L;

	public YMException() {
		super();
	}

	public YMException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public YMException(String message, Throwable cause) {
		super(message, cause);
	}

	public YMException(String message) {
		super(message);
	}

	public YMException(Throwable cause) {
		super(cause);
	}
	

}
