package com.neuedu.oa.web.controller.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neuedu.oa.dto.JSONResponse;
import com.neuedu.oa.exception.OAException;

@ControllerAdvice
public class HandleGlobleAjaxExceptionController {

	@ExceptionHandler
	@ResponseBody
	public JSONResponse handle(Exception e) {
		if (e instanceof OAException) {
			return JSONResponse.fail((OAException) e);
		}
		return JSONResponse.fail(e);
	}
}
