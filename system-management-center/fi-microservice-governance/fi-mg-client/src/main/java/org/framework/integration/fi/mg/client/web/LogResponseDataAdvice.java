package org.framework.integration.fi.mg.client.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Created  on 2023/5/23 16:16:07
 *
 * @author zl
 */
@Slf4j
@Component
@ControllerAdvice
public class LogResponseDataAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        //若返回false则下面的配置不生效
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {

        if (log.isTraceEnabled()) {
            log.trace("操作日志埋点，响应数据检测！ body type : {}, body : {}", returnType, body);
            log.trace("other param : selectedContentType : {},selectedConverterType : {}", selectedContentType, selectedConverterType);
        }

        LogContextHolder.getLocalMap().setBody(body);
        return body;
    }
}
