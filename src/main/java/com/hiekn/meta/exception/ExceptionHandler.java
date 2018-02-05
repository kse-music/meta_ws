package com.hiekn.meta.exception;

import com.hiekn.meta.bean.result.ErrorCodes;
import com.hiekn.meta.bean.result.RestResp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ExceptionHandler implements ExceptionMapper<Exception> {
	
	private static Logger log = LogManager.getLogger(ExceptionHandler.class);  
	
	@Override
	public Response toResponse(Exception exception) {
		ErrorCodes code = ErrorCodes.SERVICE_ERROR;
		Status statusCode = Status.OK;
		if(exception instanceof BaseException){
			code = ((BaseException) exception).getCode();
		}else if(exception instanceof WebApplicationException){
			code = ErrorCodes.HTTP_ERROR;
			if(exception instanceof NotFoundException){
				statusCode = Status.NOT_FOUND;
			}else if(exception instanceof NotAllowedException){
				statusCode = Status.METHOD_NOT_ALLOWED;
			}else if(exception instanceof NotAcceptableException){
				statusCode = Status.NOT_ACCEPTABLE;
			}else if(exception instanceof InternalServerErrorException){
				statusCode = Status.INTERNAL_SERVER_ERROR;
			}
		}
		String errMsg = code.toString();
        RestResp<Integer> resp = new RestResp<>(code.getErrorCode(),errMsg);
		log.error(errMsg, exception);  
		return Response.ok(resp).status(statusCode).build();  
	}
	
}
