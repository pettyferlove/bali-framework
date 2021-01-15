package com.github.bali.core.framework.handler;

import com.github.bali.core.framework.domain.vo.R;
import com.github.bali.core.framework.exception.BaseException;
import com.github.bali.core.framework.exception.BaseRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

/**
 * RestfulApi异常拦截
 *
 * @author Petty
 */
@Slf4j
@Configuration
@ConditionalOnWebApplication
@RestControllerAdvice(annotations = {RestController.class})
public class GlobalRestControllerExceptionHandler {
    /**
     * 处理BaseRuntimeException异常
     *
     * @param response 响应对象
     * @param ex       异常
     * @return data
     */
    @ExceptionHandler(BaseRuntimeException.class)
    public R baseExceptionHandler(HttpServletResponse response, BaseRuntimeException ex) {
        log.error(ex.getMessage(), ex);
        response.setStatus(ex.getStatus().value());
        return new R(ex, ex.getStatus());
    }

    /**
     * 处理RuntimeException异常
     *
     * @param response 响应对象
     * @param ex       异常
     * @return data
     */
    @ExceptionHandler(RuntimeException.class)
    public R runtimeExceptionHandler(HttpServletResponse response, RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new R(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 处理BaseException异常
     *
     * @param response 响应对象
     * @param ex       异常
     * @return data
     */
    @ExceptionHandler(BaseException.class)
    public R otherExceptionHandler(HttpServletResponse response, BaseException ex) {
        log.error(ex.getMessage(), ex);
        response.setStatus(ex.getStatus().value());
        return new R(ex, ex.getStatus());
    }


    /**
     * 处理Exception异常
     *
     * @param response 响应对象
     * @param ex       异常
     * @return data
     */
    @ExceptionHandler(Exception.class)
    public R exceptionHandler(HttpServletResponse response, Exception ex) {
        log.error(ex.getMessage(), ex);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new R(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /**
     * 处理数据验证异常
     *
     * @param response 响应对象
     * @param ex       异常
     * @return data
     */
    @ExceptionHandler(BindException.class)
    public R<String> methodArgumentNotValidExceptionHandler(HttpServletResponse response, BindException ex) {
        log.error(ex.getMessage(), ex);
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder errorMessage = new StringBuilder(bindingResult.getFieldErrors().size() << 4);
        errorMessage.append("invalid request:");
        for (int i = 0; i < bindingResult.getFieldErrors().size(); i++) {
            if (i > 0) {
                errorMessage.append(",");
            }
            FieldError error = bindingResult.getFieldErrors().get(i);
            errorMessage.append(error.getField());
            errorMessage.append(":");
            errorMessage.append(error.getDefaultMessage());
        }
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new R<>(ex, errorMessage.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public R accessDeniedExceptionHandler(HttpServletResponse response, RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        return new R(ex, HttpStatus.FORBIDDEN);
    }

}
