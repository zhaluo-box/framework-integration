package org.framework.integratio.sys.test.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created  on 2023/4/20 15:15:08
 *
 * @author zl
 */
@Slf4j
@Component
public class FeignLogTraceInterceptor implements RequestInterceptor, Ordered {

    @Override
    public void apply(RequestTemplate template) {
        try {
            var requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            template.header(LogConstant.LOG_TRACE_ID, getLogTraceId(requestAttributes.getRequest()));
        } catch (Exception ex) {
            log.warn(NestedExceptionUtils.buildMessage("Feign get log_trace_id failed!", ex));
        }
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }

    private String getLogTraceId(HttpServletRequest request) {
        var header = ServletUtil.getHeader(request, LogConstant.LOG_TRACE_ID);
        if (StringUtils.hasText(header)) {
            return header;
        }
        return String.valueOf(request.getAttribute(LogConstant.LOG_TRACE_ID));
    }
}
