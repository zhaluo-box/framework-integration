package org.framework.integratio.sys.test.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Created  on 2023/4/27 11:11:53
 *
 * @author zl
 */
@Slf4j
@Component
public class ScheduledTestService {

    @Scheduled(fixedRate = 5000)
    public void scheduledPrintTest() {
        var random = new Random();
        var i = random.nextInt(100);
        log.info("scheduled print test  index : {}", i);
    }
}
