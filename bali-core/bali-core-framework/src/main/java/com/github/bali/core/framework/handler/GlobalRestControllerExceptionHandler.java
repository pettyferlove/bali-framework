package com.github.bali.core.framework.handler;

import com.github.bali.core.framework.domain.vo.ExceptionResponse;
import com.github.bali.core.framework.domain.vo.R;
import com.github.bali.core.framework.exception.BaseException;
import com.github.bali.core.framework.exception.BaseRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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

    protected MessageSourceAccessor messages;

    @PostConstruct
    public void initProvider() {
        ReloadableResourceBundleMessageSource localMessageSource = new ReloadableResourceBundleMessageSource();
        localMessageSource.setBasenames("messages_zh_CN");
        messages = new MessageSourceAccessor(localMessageSource);
    }

    /**
     * 处理BaseRuntimeException异常
     *
     * @param response 响应对象
     * @param ex       异常
     * @return data
     */
    @ExceptionHandler(BaseRuntimeException.class)
    public ExceptionResponse baseExceptionHandler(HttpServletResponse response, BaseRuntimeException ex) {
        log.error(ex.getMessage(), ex);
        response.setStatus(ex.getStatus().value());
        return new ExceptionResponse(ex.getMessage());
    }

    /**
     * 处理RuntimeException异常
     *
     * @param response 响应对象
     * @param ex       异常
     * @return data
     */
    @ExceptionHandler(RuntimeException.class)
    public ExceptionResponse runtimeExceptionHandler(HttpServletResponse response, RuntimeException ex) {
        String message = ex.getMessage();
        log.error(message, ex);
        if(message != null && message.contains("TenantHandlerException")){
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ExceptionResponse(this.messages
                    .getMessage("GlobalControllerExceptionHandler.tenantHandlerExceptionHandler", "This user cannot query tenant data"));
        } else {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ExceptionResponse(this.messages
                    .getMessage("GlobalControllerExceptionHandler.runtimeExceptionHandler", "Runtime exception,please try again later"));
        }
    }

    /**
     * 处理BaseException异常
     *
     * @param response 响应对象
     * @param ex       异常
     * @return data
     */
    @ExceptionHandler(BaseException.class)
    public ExceptionResponse otherExceptionHandler(HttpServletResponse response, BaseException ex) {
        log.error(ex.getMessage(), ex);
        response.setStatus(ex.getStatus().value());
        return new ExceptionResponse(ex.getMessage());
    }


    /**
     * 处理Exception异常
     *
     * @param response 响应对象
     * @param ex       异常
     * @return data
     */
    @ExceptionHandler(Exception.class)
    public ExceptionResponse exceptionHandler(HttpServletResponse response, Exception ex) {
        log.error(ex.getMessage(), ex);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ExceptionResponse(this.messages
                .getMessage("GlobalControllerExceptionHandler.exceptionHandler", "Service exception,please try again later"));
    }


    /**
     * 处理数据验证异常
     *
     * @param response 响应对象
     * @param ex       异常
     * @return data
     */
    @ExceptionHandler(BindException.class)
    public ExceptionResponse methodArgumentNotValidExceptionHandler(HttpServletResponse response, BindException ex) {
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
        return new ExceptionResponse(errorMessage.toString());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ExceptionResponse accessDeniedExceptionHandler(HttpServletResponse response, RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        return new ExceptionResponse(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionResponse handleMethodArgumentNotValidException(HttpServletResponse response, MethodArgumentNotValidException ex) {
        log.error(ex.getMessage(), ex);
        StringBuilder errorMessage = new StringBuilder();
        List<ObjectError> objectErrors = ex.getBindingResult().getAllErrors();
        if (!objectErrors.isEmpty()) {
            for (int i = 0; i < objectErrors.size(); i++) {
                if (i != 0) {
                    errorMessage.append(",");
                }
                errorMessage.append(objectErrors.get(i).getDefaultMessage());
            }
        } else {
            errorMessage.append("MethodArgumentNotValidException occured.");
        }
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ExceptionResponse(errorMessage.toString());
    }

}
