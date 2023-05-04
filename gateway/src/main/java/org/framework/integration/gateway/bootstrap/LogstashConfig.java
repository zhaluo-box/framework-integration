package org.framework.integration.gateway.bootstrap;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.appender.LogstashTcpSocketAppender;
import net.logstash.logback.encoder.LogstashEncoder;
import org.slf4j.impl.StaticLoggerBinder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

/**
 * Created  on 2023/4/28 09:9:21
 *
 * @author zl
 */
@Configuration
@Slf4j
public class LogstashConfig implements ApplicationRunner {

    @Value("${logstash.enable:false}")
    private Boolean enable;

    @Value("${logstash.host:null}")
    private String host;

    @Value("${logstash.index:''}")
    private String index;

    @Value("${spring.application.name:''}")
    private String applicationName;

    @Value("${spring.application.profiles.active:'default'}")
    private String activePro;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // 判断是否开启lostash
        if (!enable) {
            log.info("Logstash configuration is not turned on.");
            return;
        }

        // 判断是否设置host,未设置host则退出配置
        if (null == host) {
            log.warn("Failed to configure a logstash: 'host' attribute is not specified and no embedded appender could be configured.");
            return;
        }

        // 判断是否设置appName,如果没有设置appname则使用applicaionanme-active替代
        if (null != index || "".equals(index)) {
            index = applicationName + "-" + activePro;
        }

        LoggerContext loggerContext = (LoggerContext) StaticLoggerBinder.getSingleton().getLoggerFactory();
        LogstashEncoder encoder = new LogstashEncoder();
        encoder.setContext(loggerContext);
        // 配置appname为es中的index
        encoder.setCustomFields("{\"appname\":\"" + index + "\"}");
        encoder.start();

        LogstashTcpSocketAppender appender = new LogstashTcpSocketAppender();
        appender.addDestination(host);
        appender.setContext(loggerContext);

        appender.setEncoder(encoder);
        appender.start();

        Logger rootLogger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.addAppender(appender);
    }
}

