/**
 * 
 */
package com.ym.aws.notification.service;

import java.util.List;

import com.ym.aws.notification.eception.NotificatioException;
import com.ym.aws.notification.entity.SMSNotification;

/**
 * @author Pranit.Mhatre
 *
 */
public interface INotificationService {

	void sendSMSPushNotification(SMSNotification... messages) throws NotificatioException;

    void sendSMSPushNotification(List<SMSNotification> messages) throws NotificatioException;
}
