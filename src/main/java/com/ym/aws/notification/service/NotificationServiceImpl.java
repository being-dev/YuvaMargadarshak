/**
 * 
 */
package com.ym.aws.notification.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;

import com.ym.aws.notification.eception.NotificatioException;
import com.ym.aws.notification.entity.SMSNotification;
import com.ym.aws.utils.Utility;

/**
 * @author Pranit.Mhatre
 *
 */
public class NotificationServiceImpl implements INotificationService {

	public final static String AUTH_KEY_FCM = "AAAAC0Vq3Ws:APA91bFd4Jt2ce4A5LWUkqOiUxlzoIUcMTyliiqggnJDWOsjr60CUl-pPea3uI1-vEvfe7j8kf4cTyxVAjfwutxulI5leowH8Rkjf6t9t0b-LbuiydzHHeu6LEWYM8v4Q4uLYSmlTtFq";
	public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";

	private final static String SMS_NOTIFICATION_SECRET_KEY = "cMH_u2zZTJaiSbuIYUOcN6:APA91bHYtmcYsK2Tzob4bbUa62ui5Fa7Bl-IfUubJ3Y6IiZY0gasmD9FnRzxYHirCEtjK2YT2w2gEbycPDN9yIqzwo70ghN7PdMOPkDeS35lCauF10dvnrHN85NnfHjbSqbiqdnaaPps";

	public static HttpURLConnection getHTTPConn() throws Exception {
		URL url = new URL(API_URL_FCM);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		httpURLConnection.setRequestProperty("Authorization", "key=" + AUTH_KEY_FCM);
		httpURLConnection.setRequestProperty("Content-Type", "application/json; UTF-8");
		httpURLConnection.setDoOutput(true);
		return httpURLConnection;
	}

	@Override
	public void sendSMSPushNotification(SMSNotification... messages) throws NotificatioException {
		sendSMSPushNotification(Arrays.asList(messages));
	}

	@Override
	public void sendSMSPushNotification(List<SMSNotification> messages) throws NotificatioException {
		try {

			List<String> dataList = messages.stream().map(message -> {
				return message.getMobile().concat("##").concat(message.getMessage());
			}).collect(Collectors.toList());

			JSONObject json = new JSONObject();
			json.put("to", SMS_NOTIFICATION_SECRET_KEY);

			JSONObject info = new JSONObject();
			info.put("title", "SEND_SMS"); // Notification title
			info.put("body", dataList); // Notification body
			json.put("data", info);

			HttpURLConnection conn = getHTTPConn();
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(json.toString());
			wr.flush();
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			while (br.readLine() != null)
				;
		} catch (Exception ex) {
			throw new NotificatioException(ex.getMessage());
		}
	}

}
