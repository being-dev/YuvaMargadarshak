/**
 * 
 */
package com.ym.aws.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.databind.JsonNode;
import com.ym.aws.auth.jwt.JWTTokenGenerator;
import com.ym.aws.constant.YMConstants;
import com.ym.aws.dao.ContactDAOImpl;
import com.ym.aws.dao.IContactDAO;
import com.ym.aws.dao.IRegistrationDAO;
import com.ym.aws.dao.IUserDAO;
import com.ym.aws.dao.RegistrationDAOImpl;
import com.ym.aws.dao.UserDAOImpl;
import com.ym.aws.entity.ContactUs;
import com.ym.aws.entity.Registration;
import com.ym.aws.entity.User;
import com.ym.aws.exception.YMException;
import com.ym.aws.notification.eception.NotificatioException;
import com.ym.aws.notification.entity.SMSNotification;
import com.ym.aws.notification.service.INotificationService;
import com.ym.aws.notification.service.NotificationServiceImpl;
import com.ym.aws.security.PasswordManager;
import com.ym.aws.utils.Utility;

/**
 * @author Pranit.Mhatre
 *
 */
public class YuvaMargdarshakServiceImpl implements IYuvaMargdarshakService {

	private static final String REG_TB_NAME = "tb_candidate";
	private static final String CONT_TB_NAME = "tb_contactus";
	private static final String USER_TB_NAME = "tb_user";

	private IRegistrationDAO iRegistrationDAO = new RegistrationDAOImpl(REG_TB_NAME);
	private IContactDAO iContactDAO = new ContactDAOImpl(CONT_TB_NAME);
	private IUserDAO iUserDAO = new UserDAOImpl(USER_TB_NAME);

	private INotificationService notificationService = new NotificationServiceImpl();

	@Override
	public void register(String jsonBody) throws YMException {
		try {
			Registration registration = new Registration();

			JsonNode node = Utility.textToNode(jsonBody);

			Map<String, String> details = new HashMap<>();
			details.put("name",
					node.get("txt_first_name").asText().concat(YMConstants.SPACE)
							.concat(node.get("txt_middle_name").asText()).concat(YMConstants.SPACE)
							.concat(node.get("txt_last_name").asText()));
			details.put("mobileNo", node.get("txt_mobile_no").asText());
			details.put("birthDate",
					Utility.formatDate(node.get("txt_birth_date").asText(), Utility.DD_MM_YYYY_DATE_PATTERN));
			details.put("address", node.get("txt_address").asText());
			details.put("education", node.get("txt_education").asText());
			details.put("lookingFor", node.get("txt_look_for").asText());
			details.put("additionalInfo", node.get("txt_add_info").asText());
			details.put("reg_date", Utility.dateToText(Utility.DD_MM_YYYY_HH_MM_DATE_PATTERN));

			registration.setDetails(details);

			iRegistrationDAO.save(registration);

			SMSNotification sms = new SMSNotification();
			sms.setId(Utility.dateToMillis());
			sms.setCreatedDate(Utility.dateToText());
			sms.setEmployee(registration.getId());
			sms.setMessage(buildSMS(registration));
			sms.setMobile(registration.getDetails().get("mobileNo"));
			sms.setType("REG");

			notificationService.sendSMSPushNotification(sms);

		} catch (YMException ex) {
			throw new YMException(YMConstants.REG_FAILURE);
		} catch (NotificatioException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			throw new YMException(YMConstants.REG_FAILURE);
		}
	}

	@Override
	public List<Registration> getAllRegistrations() throws YMException {
		List<Registration> registrations = new ArrayList<>(iRegistrationDAO.findAll());
		Collections.sort(registrations, (reg1, reg2) -> {
			return Utility.compareDates(reg2.getDetails().get("reg_date"), reg1.getDetails().get("reg_date"));
		});
		return registrations;
	}

	@Override
	public void saveContactUs(String jsonBody) throws YMException {
		try {
			ContactUs contactUs = new ContactUs();

			JsonNode node = Utility.textToNode(jsonBody);

			Map<String, String> details = new HashMap<>();
			details.put("name", node.get("txt_name").asText());
			details.put("mobileNo", node.get("txt_mobile").asText());
			details.put("message", node.get("txt_message").asText());
			details.put("query_date", Utility.dateToText(Utility.DD_MM_YYYY_HH_MM_DATE_PATTERN));

			contactUs.setDetails(details);

			iContactDAO.save(contactUs);

			SMSNotification sms = new SMSNotification();
			sms.setId(Utility.dateToMillis());
			sms.setCreatedDate(Utility.dateToText());
			sms.setEmployee(contactUs.getId());
			sms.setMessage(buildSMS(contactUs));
			sms.setMobile(contactUs.getDetails().get("mobileNo"));
			sms.setType("REG");

			notificationService.sendSMSPushNotification(sms);

		} catch (YMException ex) {
			throw new YMException(YMConstants.CONTACT_FAILURE);
		} catch (Exception ex) {
			throw new YMException(YMConstants.CONTACT_FAILURE);
		}
	}

	@Override
	public List<ContactUs> getAllQueries() throws YMException {
		List<ContactUs> contactList = new ArrayList<>(iContactDAO.findAll());
		Collections.sort(contactList, (qd1, qd2) -> {
			return Utility.compareDates(qd2.getDetails().get("query_date"), qd1.getDetails().get("query_date"));
		});
		return contactList;
	}

	@Override
	public String authenticate(String jsonBody) throws YMException {
		try {
			JsonNode node = Utility.textToNode(jsonBody);
			User user = iUserDAO.loadUser(node.get("txt_username").asText(), node.get("txt_password").asText());
			if (Objects.nonNull(user)) {
				boolean isValid = PasswordManager.isPasswordValid(user.getPassword(),
						node.get("txt_password").asText());
				if (isValid) {
					user.setToken(JWTTokenGenerator.createToken(user.getUserName()));
					iUserDAO.updateToken(user);
					return user.getToken();
				} else {
					throw new YMException(
							"Username and/or password is incorrect. Please enter valid username and/or password. ");
				}
			}

		} catch (YMException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new YMException(YMConstants.USER_LOGIN_FAILED);
		}
		return null;
	}

	private String buildSMS(Registration registration) {
		StringBuilder builder = new StringBuilder();
		builder.append("Hi Candiate,\n");
		// builder.append(registration.getDetails().get("name")).append(",\n");
		builder.append("You have been successfully registered with YuvaMargadarshak.\nWe will contact you soon.\n");
		builder.append("Thank you.");
		return builder.toString();
	}

	private String buildSMS(ContactUs contactUs) {
		StringBuilder builder = new StringBuilder();
		builder.append("Hi Candidate,\n");
		// builder.append(contactUs.getDetails().get("name")).append(" ").append(",\n");
		builder.append("Thanks for providing your queries to us. We will resolve your query very soon.\n");
		builder.append("Thank you.");
		return builder.toString();
	}
}
