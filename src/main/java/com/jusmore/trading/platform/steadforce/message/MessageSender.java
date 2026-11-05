package com.jusmore.trading.platform.steadforce.message;

import tools.jackson.databind.ObjectMapper;

import java.util.Objects;

public class MessageSender implements Sender {
  TopicListener textTopic = new TextTopic();
  BinaryTopic binaryTopic = new BinaryTopic();

  @Override
  public boolean send(Message message) {
    if (Objects.nonNull(message)) {
      if (message.messageType() == MessageType.TEXT) {
        textTopic.publish(message);
      } else if (message.messageType() == MessageType.BINARY) {
        binaryTopic.publish(message);
      }
      return true;
    }

    return false;
  }
}
