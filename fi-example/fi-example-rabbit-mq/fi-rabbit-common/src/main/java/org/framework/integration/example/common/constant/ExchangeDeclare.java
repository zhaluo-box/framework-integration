package org.framework.integration.example.common.constant;

/**
 * Created  on 2022/9/21 12:12:49
 *
 * @author wmz
 */
public interface ExchangeDeclare {

    String FANOUT_EXCHANGE = "fi:example:fanout-exchange";

    String TOPIC_EXCHANGE = "fi:example:topic-exchange";

    String SIMPLE_TOPIC_EXCHANGE = "fi:example:simple-topic-exchange";

    String DIRECT_EXCHANGE = "fi:example:direct-exchange";
    String DIRECT_EXCHANGE_D = "fi:example:direct-exchange-d";

    String TOPIC_EXCHANGE_DLX = "fi:example:topic-exchange-dlx";

    String DIRECT_BATCH_EXCHANGE = "fi:example:direct-batch-exchange";
}
