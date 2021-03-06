package com.zw.admin.framework.common.exception;

import com.google.common.collect.ImmutableMap;
import com.zw.admin.framework.common.response.CommonCode;
import com.zw.admin.framework.common.response.ResponseMain;
import com.zw.admin.framework.common.response.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常捕获类
 * @author: ZhouWei
 * @create: 2021-01
 **/
@ControllerAdvice
public class ExceptionCatch {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionCatch.class);
    /**定义map的builder对象，去构建ImmutableMap*/
    protected static ImmutableMap.Builder<Class<? extends Throwable>, ResponseMain> builder = ImmutableMap.builder();
    /**定义map，配置异常类型所对应的错误代码*/
    private static ImmutableMap<Class<? extends Throwable>, ResponseMain> EXCEPTIONS;

    static {
        //定义异常类型所对应的错误代码
        builder.put(HttpMessageNotReadableException.class, CommonCode.PARAM_INVALID);
    }

    /**
     * 捕获CustomException此类异常
     * @param customException 自定义异常类型
     * @return com.zw.admin.framework.common.response.ResponseResult
     **/
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseResult customException(CustomException customException) {
        //记录日志
        LOGGER.error("catch exception:{}", customException.getMessage());
        ResponseMain responseMain = customException.getResultCode();
        return new ResponseResult(responseMain);
    }

    /**
     * 捕获preAuthorizeException此类异常
     * @param preAuthorizeException 权限异常
     * @return com.zw.admin.framework.common.response.ResponseResult
     **/
    @ExceptionHandler(PreAuthorizeException.class)
    @ResponseBody
    public ResponseResult preAuthorizeException(PreAuthorizeException preAuthorizeException) {
        System.out.println("没有权限，请联系管理员授权");
        //记录日志
        LOGGER.error("catch exception:{}", preAuthorizeException.getMessage());
        return new ResponseResult(CommonCode.UNAUTHORISE);
    }

    /**
     * 捕获Exception此类异常
     * @param exception
     * @return com.zw.admin.framework.common.response.ResponseResult
     **/
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exception(Exception exception) {
        //记录日志
        LOGGER.error("catch exception:{}", exception.getMessage());
        if (EXCEPTIONS == null) {
            //EXCEPTIONS构建成功
            EXCEPTIONS = builder.build();
        }
        //从EXCEPTIONS中找异常类型所对应的错误代码，如果找到了将错误代码响应给用户，如果找不到给用户响应9999异常
        ResponseMain responseMain = EXCEPTIONS.get(exception.getClass());
        if (responseMain != null) {
            return new ResponseResult(responseMain);
        } else {
            //返回99999异常
            return new ResponseResult(CommonCode.SERVER_ERROR);
        }
    }
}
