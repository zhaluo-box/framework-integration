package org.framework.integration.fi.mg.client.bootstrap;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;

/**
 * Created  on 2023/5/23 15:15:30
 *
 * @author zl
 */
public interface BootstrapService extends ApplicationListener<ApplicationReadyEvent>, Ordered {

}
