package org.framework.integration.example.common.constant;

/**
 * Created  on 2022/9/21 12:12:49
 *
 * @author wmz
 */
public interface ExchangeDeclare {

    String WORK_EXCHANGE = "fi:example:work-queue-exchange";

    String SIMPLE_EXCHANGE = "fi:example:simple-queue-exchange";

    String FANOUT_EXCHANGE = "fi:example:fanout-exchange";

    String TOPIC_EXCHANGE = "fi:example:topic-exchange";

    String DIRECT_EXCHANGE = "fi:example:direct-exchange";
}
