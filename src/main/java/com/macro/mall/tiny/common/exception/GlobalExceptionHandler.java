package com.macro.mall.tiny.common.exception;

import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.common.api.IErrorCode;
import com.macro.mall.tiny.common.api.ResultCode;
import com.macro.mall.tiny.modules.dlm.exception.MyEumException;
import com.macro.mall.tiny.modules.dlm.exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * 全局异常处理
 * Created by macro on 2020/2/27.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = ApiException.class)
    public CommonResult handle(ApiException e) {
        if (e.getErrorCode() != null) {
            return CommonResult.failed(e.getErrorCode());
        }
        return CommonResult.failed(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public CommonResult handleValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField()+fieldError.getDefaultMessage();
            }
        }
        return CommonResult.validateFailed(message);
    }

    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    public CommonResult handleValidException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField()+fieldError.getDefaultMessage();
            }
        }
        return CommonResult.validateFailed(message);
    }

    @ResponseBody
    @ExceptionHandler(value = MyException.class)
    public CommonResult defaultMyExceptionHandler(MyException e) {
        return CommonResult.failed(e.getCode(),e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler()
    public CommonResult defaultExceptionHandler(HttpServletRequest request,Exception e) {
        StackTraceElement stackTraceElement = e.getStackTrace()[0];
        e.printStackTrace();
        //异常类全路径
        log.error("path: " + stackTraceElement.getClassName());
        //异常行数
        log.error("line: " + stackTraceElement.getLineNumber());
        //异常信息
        log.error("error: " + e.toString());
        //异常请求地址
        log.error("url: " + request.getRequestURI());
        //异常请求参数
        StringBuffer sb = new StringBuffer();
        if(request.getParameterMap() != null && request.getParameterMap().size() > 0){
            for(String key : request.getParameterMap().keySet()){
                sb.append(",");
                sb.append(key);
                sb.append("|");
                sb.append(request.getParameter(key));
            }
        }
        if(sb.length() > 0){
            sb.deleteCharAt(0);
        }
        log.error("param: " + sb.toString());

        //未知错误
        return CommonResult.failed(-1,"系统异常,请稍后再试");
    }
}
