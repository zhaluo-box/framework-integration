//package org.framework.integration.web.starter.handler;
//
//import org.framework.integration.common.core.http.response.ResponseEntity;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.HttpRequestMethodNotSupportedException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * 全局异常处理器
// *
// * @author ruoyi
// */
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
//
//
//
//
//
//    /**
//     * 请求方式不支持
//     */
//    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
//    //    @ResponseStatus(org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED)
//    public ResponseEntity<String> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
//        String requestURI = request.getRequestURI();
//        log.error("请求地址'{}',不支持'{}'请求", requestURI, e.getMethod());
//        return AjaxResult.error(e.getMessage());
//    }
//
//    /**
//     * 业务异常
//     */
//    @ExceptionHandler(ServiceException.class)
//    public AjaxResult handleServiceException(ServiceException e, HttpServletRequest request) {
//        String requestURI = request.getRequestURI();
//        log.error("请求地址'{}'", requestURI);
//        log.error(e.getMessage(), e);
//        Integer code = e.getCode();
//        return StringUtils.isNotNull(code) ? AjaxResult.error(code, e.getMessage()) : AjaxResult.error(e.getMessage());
//    }
//
//    /**
//     * 拦截未知的运行时异常
//     */
//    @ExceptionHandler(RuntimeException.class)
//    //    @ResponseStatus(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR)
//    public AjaxResult handleRuntimeException(RuntimeException e, HttpServletRequest request) {
//        String requestURI = request.getRequestURI();
//        log.error("请求地址'{}',发生未知异常.", requestURI, e);
//        return AjaxResult.error(e.getMessage());
//    }
//
//    /**
//     * 自定义验证异常
//     */
//    @ExceptionHandler(BindException.class)
//    //    @ResponseStatus(org.springframework.http.HttpStatus.BAD_REQUEST)
//    public AjaxResult handleBindException(BindException e) {
//        log.error(e.getMessage(), e);
//        String message = e.getAllErrors().get(0).getDefaultMessage();
//        return AjaxResult.error(message);
//    }
//
//    /**
//     * 自定义验证异常
//     */
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    //    @ResponseStatus(org.springframework.http.HttpStatus.BAD_REQUEST)
//    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
//        log.error(e.getMessage(), e);
//        String message = e.getBindingResult().getFieldError().getDefaultMessage();
//        return AjaxResult.error(message);
//    }
//
//    /**
//     * 内部认证异常
//     */
//    @ExceptionHandler(InnerAuthException.class)
//    //    @ResponseStatus(org.springframework.http.HttpStatus.FORBIDDEN)
//    public AjaxResult handleInnerAuthException(InnerAuthException e) {
//        return AjaxResult.error(e.getMessage());
//    }
//
//    /**
//     * 演示模式异常
//     */
//    @ExceptionHandler(DemoModeException.class)
//    //    @ResponseStatus(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR)
//    @SuppressWarnings("unused")
//    public AjaxResult handleDemoModeException(DemoModeException e) {
//        return AjaxResult.error("演示模式，不允许操作");
//    }
//
//    //    ============================MES 系统自定义异常 ==============================
//    @ExceptionHandler(ResourceConflictException.class)
//    //    @ResponseStatus(org.springframework.http.HttpStatus.CONFLICT)
//    public AjaxResult handle(ResourceConflictException exception, HttpServletRequest request) {
//        String requestURI = request.getRequestURI();
//        log.error("请求地址'{}',发生未知异常.", requestURI, exception);
//        log.warn(exception.getMessage(), exception);
//        return buildResult(org.springframework.http.HttpStatus.CONFLICT, exception.getMessage());
//    }
//
//    @ExceptionHandler(ResourceNotFoundException.class)
//    //    @ResponseStatus(org.springframework.http.HttpStatus.NOT_FOUND)
//    public AjaxResult handle(ResourceNotFoundException exception, HttpServletRequest request) {
//        String requestURI = request.getRequestURI();
//        log.error("请求地址'{}',发生未知异常.", requestURI, exception);
//        log.warn(exception.getMessage(), exception);
//        return buildResult(org.springframework.http.HttpStatus.NOT_FOUND, exception.getMessage());
//    }
//
//    @ExceptionHandler(MesException.class)
//    //    @ResponseStatus(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR)
//    public AjaxResult handle(MesException exception, HttpServletRequest request) {
//        String requestURI = request.getRequestURI();
//        log.error("请求地址'{}',发生未知异常.", requestURI, exception);
//        log.warn(exception.getMessage(), exception);
//        return buildResult(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
//    }
//
//    @ExceptionHandler(BadRequestException.class)
//    //    @ResponseStatus(org.springframework.http.HttpStatus.BAD_REQUEST)
//    public AjaxResult handle(BadRequestException exception, HttpServletRequest request) {
//        String requestURI = request.getRequestURI();
//        log.error("请求地址'{}',发生未知异常.", requestURI, exception);
//        log.warn(exception.getMessage(), exception);
//        return buildResult(org.springframework.http.HttpStatus.BAD_REQUEST, exception.getMessage());
//    }
//
//    @ExceptionHandler(ResourceGoneException.class)
//    //    @ResponseStatus(org.springframework.http.HttpStatus.GONE)
//    public AjaxResult handle(ResourceGoneException exception, HttpServletRequest request) {
//        String requestURI = request.getRequestURI();
//        log.error("请求地址'{}',发生未知异常.", requestURI, exception);
//        log.warn(exception.getMessage(), exception);
//        return buildResult(org.springframework.http.HttpStatus.GONE, exception.getMessage());
//    }
//
//    @ExceptionHandler(ForbiddenException.class)
//    //    @ResponseStatus(org.springframework.http.HttpStatus.FORBIDDEN)
//    public AjaxResult handle(ForbiddenException exception, HttpServletRequest request) {
//        String requestURI = request.getRequestURI();
//        log.error("请求地址'{}',发生未知异常.", requestURI, exception);
//        log.warn(exception.getMessage(), exception);
//        return buildResult(org.springframework.http.HttpStatus.FORBIDDEN, exception.getMessage());
//    }
//
//    @ExceptionHandler(ResourceAccessException.class)
//    @ResponseStatus(org.springframework.http.HttpStatus.UNAUTHORIZED)
//    public AjaxResult handle(ResourceAccessException exception, HttpServletRequest request) {
//        String requestURI = request.getRequestURI();
//        log.error("请求地址'{}',发生未知异常.", requestURI, exception);
//        log.warn(exception.getMessage(), exception);
//        return buildResult(org.springframework.http.HttpStatus.UNAUTHORIZED, exception.getMessage());
//    }
//
//    /**
//     * SQL 异常太长了， 不适合显示给客户看， 需要进行友好处理，目前同意返回服务器内部异常，请联系管理员处理
//     */
//    @ExceptionHandler(SQLException.class)
//    //    @ResponseStatus(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR)
//    public AjaxResult handle(SQLException exception, HttpServletRequest request) {
//        String requestURI = request.getRequestURI();
//        log.error("请求地址'{}',发生未知异常.", requestURI, exception);
//        log.warn(exception.getMessage(), exception);
//        return buildResult(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR, "服务器内部异常，请联系管理员处理！");
//    }
//
//    @ExceptionHandler(Exception.class)
//    //    @ResponseStatus(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR)
//    public AjaxResult handle(Exception exception, HttpServletRequest request) {
//        String requestURI = request.getRequestURI();
//        log.error("请求地址'{}',发生未知异常.", requestURI, exception);
//        log.warn(exception.getMessage(), exception);
//        return buildResult(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
//    }
////
//    private AjaxResult buildResult(org.springframework.http.HttpStatus status, String errorMessage) {
//        return AjaxResult.error(status.value(), errorMessage);
//    }
//}
