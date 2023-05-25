package org.framework.integration.example.web.mvc.controller;

import org.framework.integration.example.web.mvc.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created  on 2023/5/16 14:14:40
 *
 * @author zl
 */
@RestController
@RequestMapping("/i18n")
public class I18NTestController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private AsyncService asyncService;

    @GetMapping("actions/base-api-test")
    public String baseApiTest(@RequestParam String code) {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }

    @GetMapping("actions/async-test")
    public void asyncTest(@RequestParam String code) {
        asyncService.printName(code);
    }
    
}
