package org.framework.integration.example.web.mvc.utils;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * 所有方法都可能抛出{@link  org.springframework.context.NoSuchMessageException }
 * Created  on 2023/5/16 15:15:48
 *
 * @author zl
 */
@Slf4j
@NoArgsConstructor
public final class MessageUtil {

    private static MessageSource messageSource;

    public static void init(MessageSource ms) {
        messageSource = ms;
    }

    public static String getMessage(String code, Object[] args, String defaultMessage) {
        return messageSource.getMessage(code, args, defaultMessage, LocaleContextHolder.getLocale());
    }

    public static String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    public static String getMessage(MessageSourceResolvable resolvable, Locale locale) {
        return messageSource.getMessage(resolvable, locale);
    }
}
