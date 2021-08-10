package com.ym.aws.vo;

import java.io.Serializable;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

/**
 * @author Pranit.Mhatre
 *
 */
public class ResponseVO extends APIGatewayProxyResponseEvent implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1798042927776325831L;
    private Object data;

    /**
     * @return the data
     */
    public Object getData() {
	return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Object data) {
	this.data = data;
    }

}
