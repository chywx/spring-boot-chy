//package cn.chendahai.chy.mq.test;
//
//import org.apache.rocketmq.common.message.MessageId;
//
//import java.time.Duration;
//import java.util.Collections;
//
//public class SimpleConsumerTest {
//
//
//    public static void main(String[] args) {
//        SimpleConsumer consumer = provider.newSimpleConsumerBuilder()
//                .setClientConfiguration(clientConfiguration)
//                // Set the consumer group name.
//                .setConsumerGroup(consumerGroup)
//                // set await duration for long-polling.
//                .setAwaitDuration(awaitDuration)
//                // Set the subscription for the consumer.
//                .setSubscriptionExpressions(Collections.singletonMap(topic, filterExpression))
//                .build();
//// Max message num for each long polling.
//        int maxMessageNum = 16;
//// Set message invisible duration after it is received.
//        Duration invisibleDuration = Duration.ofSeconds(15);
//        final List<MessageView> messages = consumer.receive(maxMessageNum, invisibleDuration);
//        LOGGER.info("Received {} message(s)", messages.size());
//        for (MessageView message : messages) {
//            final MessageId messageId = message.getMessageId();
//            try {
//                consumer.ack(message);
//                LOGGER.info("Message is acknowledged successfully, messageId={}", messageId);
//            } catch (Throwable t) {
//                LOGGER.error("Message is failed to be acknowledged, messageId={}", messageId, t);
//            }
//        }
//// Close the simple consumer when you don't need it anymore.
//        consumer.close();
//    }
//
//}
