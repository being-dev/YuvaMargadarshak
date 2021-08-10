/**
 * 
 */
package com.ym.aws.constant;

/**
 * @author Pranit.Mhatre
 *
 */
public interface YMConstants {

	interface Modules {
		String REG = "register";
		String CONTACT = "contact";
		String LOGIN = "login";
		String ADMIN = "admin";
	}

	interface Actions {
		String SAVE = "save";
		String GET_ALL = "list";
		String AUTH = "auth";
		String QUERY_ALL="queries-list";
	}

	String SPACE = " ";

	String USER_LOGIN_FAILED = "Invalid user credential provided. Please provide valid credentials.";

	String REG_SUCCESS = "Conratulations! Your registration has been completed successfully.Our team will contact you as soon as possible.";
	String REG_FAILURE = "We are sorry, due to some internal issue, unable to complete your registration. Please try again later!!!";
	String REG_LIST_ERROR = "We are sorry, due to some internal issue unable to load details. Please try again later!!!";

	String CONTACT_SUCCESS = "Thanks for contacting to us.We will reach you very soon.";
	String CONTACT_FAILURE = "We are sorry, due to some internal issue, unable to submit your query. Please try again later!!!";
	String CONTACT_LIST_ERROR = "We are sorry, due to some internal issue unable to load details. Please try again later!!!";
}
