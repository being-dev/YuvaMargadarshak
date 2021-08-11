package com.ym.aws.function;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.util.StringUtils;
import com.ym.aws.auth.jwt.JWTTokenGenerator;
import com.ym.aws.dao.IUserDAO;
import com.ym.aws.dao.UserDAOImpl;
import com.ym.aws.exception.YMException;
import com.ym.aws.vo.ResponseVO;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;

public abstract class AbstractHandler implements RequestHandler<APIGatewayProxyRequestEvent, ResponseVO> {

	private static final Logger LOG = LogManager.getLogger(AbstractHandler.class);

	private static final String MODULE_PARAM = "module";
	private static final String ACTION_PARAM = "action";
	protected APIGatewayProxyRequestEvent request;
	protected Context context;
	private ResponseVO response = null;
	protected String module = null;
	protected String action = null;

	private IUserDAO iUserDAO = new UserDAOImpl("tb_user");

	@Override
	public ResponseVO handleRequest(APIGatewayProxyRequestEvent request, Context context) {

		this.request = request;
		this.context = context;

		try {
			validateRequest();
		} catch (Exception ex) {
			LOG.error(ex.getMessage(), ex);
			return this.buildResponse(HttpStatus.SC_BAD_REQUEST, String.format(ex.getMessage()));
		}

		switch (request.getHttpMethod()) {

		case "GET":
			handleGetRequest();
			break;
		case "POST":
			handlePostRequest();
			break;
		case "PUT":
			handlePutRequest();
			break;
		case "DELETE":
			handleDeleteRequest();
			break;
		case "OPTIONS":
			buildResponse(HttpStatus.SC_OK, "Ok");
			break;
		}
		return response;
	}

	protected void handleGetRequest() {
	}

	protected void handlePostRequest() {
	}

	protected void handlePutRequest() {
	}

	protected void handleDeleteRequest() {
	}

	public final ResponseVO buildResponse(Integer statusCode, String body) {
		response = new ResponseVO();
		response.setStatusCode(statusCode);
		response.setBody(body);
		Map<String, String> headers = new HashMap<>();
		headers.put("Access-Control-Allow-Origin", "*");
		headers.put("Access-Control-Allow-Methods", "*");
		headers.put("Access-Control-Allow-Headers", "*");
		headers.put("Access-Control-Allow-Credentials", "false");
		headers.put(HttpHeaders.CONTENT_TYPE, "application/json");// Default content
		// type header
		response.setHeaders(headers);
		return response;
	}

	public final ResponseVO buildResponse(Integer statusCode, String body, Map<String, String> headers) {
		response = buildResponse(statusCode, body);
		response.withHeaders(headers);
		return response;
	}

	private void validateRequest() throws Exception {
		// TODO Perform functionality specific validation if required by overriding
		// method
		if (Objects.nonNull(request.getPathParameters())) {
			if (Objects.nonNull(request.getPathParameters().get(MODULE_PARAM))) {
				module = request.getPathParameters().get(MODULE_PARAM);
			} else {
				throw new Exception(String.format("%s Parameter not provided in request", MODULE_PARAM));
			}

			if (Objects.nonNull(request.getPathParameters().get(ACTION_PARAM))) {
				action = request.getPathParameters().get(ACTION_PARAM);
			} else {
				throw new Exception(String.format("%s Parameter not provided in request", ACTION_PARAM));
			}
		}
	}

	public final void authenticateUser() throws YMException {
		String token = request.getHeaders().get("Authorization");
		try {
			if (StringUtils.isNullOrEmpty(token)) {
				throw new YMException(
						"Unable to perform requested operation becuase token value is not present in the request.");
			}
			String userName = JWTTokenGenerator.getUser(token);
			iUserDAO.validateToken(userName, token);
		} catch (ExpiredJwtException ex) {
			throw new YMException(
					String.format("Unable to perform requested operation becuase token has been expired."));
		} catch (SignatureException ex) {
			throw new YMException(
					String.format("Unable to perform requested operation becuase token signature does not match."));
		} catch (YMException ex) {
			throw ex;
		}

	}
}
