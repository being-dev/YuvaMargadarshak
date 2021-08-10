/**
 * 
 */
package com.ym.aws.notification.eception;

/**
 * @author Pranit.Mhatre
 *
 */
public class NotificatioException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2826077675907377649L;

	/**
	 * 
	 */
	public NotificatioException() {
	}

	public NotificatioException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NotificatioException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotificatioException(String message) {
		super(message);
	}

	public NotificatioException(Throwable cause) {
		super(cause);
	}

}
