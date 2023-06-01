package org.framework.integration.fi.mg.db.view.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created  on 2023/5/30 11:11:52
 *
 * @author zl
 */
@ComponentScan(basePackages = "org.framework.integration.fi.mg.db.view")
@MapperScan(basePackages = "org.framework.integration.fi.mg.db.view.mapper")
public class MapperScannerConfig {
}
