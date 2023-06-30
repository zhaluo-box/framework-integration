package org.framework.integration.fi.mg.client.service;

import org.framework.integration.fi.mg.common.service.BusinessIdProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Component;

/**
 * Created  on 2023/6/30 15:15:33
 *
 * @author zl
 */
@Component
public class SleuthBusinessIdProvider implements BusinessIdProvider {

    @Autowired
    Tracer tracer;

    @Override
    public String getBusinessCode() {
        Span span = tracer.currentSpan();
        if (span == null) {
            return "-";
        }
        return span.context().traceId();
    }
}
