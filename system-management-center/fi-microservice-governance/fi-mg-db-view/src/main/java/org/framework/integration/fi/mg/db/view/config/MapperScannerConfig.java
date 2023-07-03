package org.framework.integration.fi.mg.db.view.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created  on 2023/5/30 11:11:52
 *
 * @author zl
 */
@Configuration
@ComponentScan(basePackages = "org.framework.integration.fi.mg.db.view")
public class MapperScannerConfig {

    static {
        System.out.println("----ubut--");
    }
}
