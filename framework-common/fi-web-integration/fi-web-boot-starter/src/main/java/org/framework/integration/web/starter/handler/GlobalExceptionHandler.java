package org.framework.integration.web.starter.handler;

import org.framework.integration.common.core.exceptions.*;
import org.framework.integration.common.core.http.response.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<String> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',不支持'{}'请求", requestURI, e.getMethod());
        return ResponseEntity.fail(HttpStatus.METHOD_NOT_ALLOWED.value(), String.format("请求地址'%s',不支持'%s'请求", requestURI, e.getMethod()));
    }

    /**
     * 运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleServiceException(RuntimeException e, HttpServletRequest request) {
        log.error("请求地址'{}'", request.getRequestURI());
        return buildResult(HttpStatus.INTERNAL_SERVER_ERROR, "服务器内部异常 %s", e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handle(Exception exception, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", requestURI, exception);
        log.warn(exception.getMessage(), exception);
        return buildResult(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @ExceptionHandler(ResourceConflictException.class)
    @ResponseStatus(org.springframework.http.HttpStatus.CONFLICT)
    public ResponseEntity<String> handle(ResourceConflictException exception, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", requestURI, exception);
        log.warn(exception.getMessage(), exception);
        return buildResult(HttpStatus.CONFLICT, exception.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(org.springframework.http.HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handle(ResourceNotFoundException exception, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", requestURI, exception);
        log.warn(exception.getMessage(), exception);
        return buildResult(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(org.springframework.http.HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handle(BadRequestException exception, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", requestURI, exception);
        log.warn(exception.getMessage(), exception);
        return buildResult(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(ResourceGoneException.class)
    @ResponseStatus(org.springframework.http.HttpStatus.GONE)
    public ResponseEntity<String> handle(ResourceGoneException exception, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", requestURI, exception);
        log.warn(exception.getMessage(), exception);
        return buildResult(org.springframework.http.HttpStatus.GONE, exception.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(org.springframework.http.HttpStatus.FORBIDDEN)
    public ResponseEntity<String> handle(ForbiddenException exception, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", requestURI, exception);
        log.warn(exception.getMessage(), exception);
        return buildResult(org.springframework.http.HttpStatus.FORBIDDEN, exception.getMessage());
    }

    @ExceptionHandler(ResourceAccessException.class)
    @ResponseStatus(org.springframework.http.HttpStatus.UNAUTHORIZED)
    public ResponseEntity<String> handle(ResourceAccessException exception, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", requestURI, exception);
        log.warn(exception.getMessage(), exception);
        return buildResult(org.springframework.http.HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

    private ResponseEntity<String> buildResult(HttpStatus status, String format, Object... args) {
        return ResponseEntity.fail(status.value(), String.format(format, args));
    }
}
