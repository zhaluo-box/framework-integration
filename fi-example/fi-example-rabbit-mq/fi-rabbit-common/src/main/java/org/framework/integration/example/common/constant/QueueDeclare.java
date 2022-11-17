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

    String TOPIC_QUEUE = "fi:example:topic-queue";

    String RPC_QUEUE = "fi:example:rpc-queue";

    String PUBLISH_SUBSCRIBE_QUEUE = "fi:example:publish-subscribe-queue";

    String PUBLISH_CONFIRM_QUEUE = "fi:example:publish-confirm-queue";
}
