package com.ym.aws.function;

import java.util.List;

import org.apache.http.HttpStatus;

import com.ym.aws.constant.YMConstants;
import com.ym.aws.entity.ContactUs;
import com.ym.aws.entity.Registration;
import com.ym.aws.exception.YMException;
import com.ym.aws.services.IYuvaMargdarshakService;
import com.ym.aws.services.YuvaMargdarshakServiceImpl;
import com.ym.aws.utils.Utility;

public class YMRequestHandler extends AbstractHandler {

	private IYuvaMargdarshakService iYuvaMargdarshakService = new YuvaMargdarshakServiceImpl();

	@Override
	public void handlePostRequest() {

		switch (module) {
		case YMConstants.Modules.REG:
			if (YMConstants.Actions.SAVE.equals(action)) {
				try {
					iYuvaMargdarshakService.register(request.getBody());
					buildResponse(HttpStatus.SC_OK, YMConstants.REG_SUCCESS);
				} catch (Exception ex) {
					context.getLogger().log(ex.getMessage());
					buildResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
				}
			}
			break;

		case YMConstants.Modules.CONTACT:
			if (YMConstants.Actions.SAVE.equals(action)) {
				try {
					iYuvaMargdarshakService.saveContactUs(request.getBody());
					buildResponse(HttpStatus.SC_OK, YMConstants.CONTACT_SUCCESS);
				} catch (Exception ex) {
					context.getLogger().log(ex.getMessage());
					buildResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
				}
			} else if (YMConstants.Actions.GET_ALL.equals(action)) {
				try {
					List<ContactUs> queries = iYuvaMargdarshakService.getAllQueries();
					buildResponse(HttpStatus.SC_OK, Utility.toJson(queries));
				} catch (YMException ex) {
					buildResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR, YMConstants.REG_LIST_ERROR);
				}
			}
			break;

		case YMConstants.Modules.LOGIN:
			if (YMConstants.Actions.AUTH.equals(action)) {
				try {
					String token = iYuvaMargdarshakService.authenticate(request.getBody());
					buildResponse(HttpStatus.SC_OK, token);
				} catch (Exception ex) {
					context.getLogger().log(ex.getMessage());
					buildResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
				}
			}
			break;
		case YMConstants.Modules.ADMIN:
			if (YMConstants.Actions.GET_ALL.equals(action)) {
				try {
					// authenticateUser();
					List<Registration> registrations = iYuvaMargdarshakService.getAllRegistrations();
					buildResponse(HttpStatus.SC_OK, Utility.toJson(registrations));
				} catch (YMException ex) {
					buildResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
				}
			} else if (YMConstants.Actions.QUERY_ALL.equals(action)) {
				try {
					// authenticateUser();
					List<ContactUs> queries = iYuvaMargdarshakService.getAllQueries();
					buildResponse(HttpStatus.SC_OK, Utility.toJson(queries));
				} catch (YMException ex) {
					buildResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
				}
			}
			break;
		}

	}
}