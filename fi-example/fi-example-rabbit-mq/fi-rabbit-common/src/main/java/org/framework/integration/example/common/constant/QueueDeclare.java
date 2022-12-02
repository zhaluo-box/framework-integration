package org.framework.integration.example.common.constant;

/**
 * Created  on 2022/9/21 12:12:49
 *
 * @author wmz
 */
public interface QueueDeclare {

    String SIMPLE_QUEUE = "fi:example:simple-queue";
    String WORK_QUEUE = "fi:example:work-queue";

    String DIRECT_QUEUE = "fi:example:direct-queue";
    String DIRECT_QUEUE2 = "fi:example:direct-queue2";

    String FANOUT_QUEUE1 = "fi:example:fanout-queue1";
    String FANOUT_QUEUE2 = "fi:example:fanout-queue2";

    String TOPIC_QUEUE1 = "fi:example:topic-queue1";
    String TOPIC_QUEUE2 = "fi:example:topic-queue2";
    String TOPIC_QUEUE3 = "fi:example:topic-queue3";
    String TOPIC_QUEUE4 = "fi:example:topic-queue4";

    String SIMPLE_TOPIC = "fi:example:simple-topic";

    String ABSTRACT_MESSAGE_QUEUE = "fi:example:abstract-message-queue";
    String ABSTRACT_MESSAGE_QUEUE_2 = "fi:example:abstract-message-queue-2";

    String DIRECT_QUEUE_D = "fi:example:direct-queue-d";

    String DIRECT_QUEUE_DLX = "fi:example:direct-queue-dlx";

    String DIRECT_BATCH_QUEUE = "fi:example:direct-batch-queue";

    String CONVERT_FAIL_QUEUE = "convert-fail";
    String CONVERT_FAIL_QUEUE_DLX = "convert-fail-dlx";

}
