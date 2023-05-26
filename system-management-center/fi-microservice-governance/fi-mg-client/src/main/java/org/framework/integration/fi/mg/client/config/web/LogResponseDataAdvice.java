package org.framework.integration.fi.mg.client.config.web;

import lombok.extern.slf4j.Slf4j;
import org.framework.integration.fi.mg.client.config.support.LogContextHolder;
import org.framework.integration.fi.mg.common.dto.SysOperationLogOriginalDTO;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Created  on 2023/5/23 16:16:07
 *
 * @author zl
 */
@Slf4j
@ControllerAdvice
public class LogResponseDataAdvice<T> implements ResponseBodyAdvice<T> {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        //若返回false则下面的配置不生效
        return true;
    }

    @Override
    public T beforeBodyWrite(T body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
                             ServerHttpRequest request, ServerHttpResponse response) {
        if (log.isTraceEnabled()) {
            log.trace("操作日志埋点，响应数据检测！ body type : {}, body : {}", returnType, body);
            log.trace("other param : selectedContentType : {},selectedConverterType : {}", selectedContentType, selectedConverterType);
        }

        SysOperationLogOriginalDTO logContext = LogContextHolder.getLogContext();
        if (!logContext.isIgnored()) {
            logContext.setOriginalResponseData(body);
        }
        return body;
    }

}
