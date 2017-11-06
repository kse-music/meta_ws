package com.hiekn.meta.exception;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hiekn.meta.bean.result.ErrorCode;
import com.hiekn.meta.bean.result.RestResp;

@Provider
public class ExceptionHandler implements ExceptionMapper<Exception> {
	
	private static Logger log = LogManager.getLogger(ExceptionHandler.class);  
	
	@Context  
    private HttpServletRequest request;  
	
	@Override
	public Response toResponse(Exception exception) {
		String t = request.getParameter("tt");
		long tt = StringUtils.isBlank(t)?0L:Long.parseLong(t);
		ErrorCode code = null;
		RestResp<Integer> resp = null;
		Status statusCode = Status.OK;
		if(exception instanceof BaseException){
			code = ((BaseException) exception).getCode();
		}else if(exception instanceof WebApplicationException){
			code = ErrorCode.HTTP_ERROR;
			if(exception instanceof NotFoundException){
				statusCode = Status.NOT_FOUND;
			}else if(exception instanceof NotAllowedException){
				statusCode = Status.METHOD_NOT_ALLOWED;
			}else if(exception instanceof NotAcceptableException){
				statusCode = Status.NOT_ACCEPTABLE;
			}else if(exception instanceof InternalServerErrorException){
				statusCode = Status.INTERNAL_SERVER_ERROR;
			}
		}else{
			code = ErrorCode.SERVICE_ERROR;
		}
		String errMsg = code.toString();
		resp = new RestResp<Integer>(code.getErrorCode(),errMsg,tt);
		log.error(errMsg, exception);  
		return Response.ok(resp).status(statusCode).build();  
	}
	
}
