package com.jusmore.trading.platform.steadforce.message;

import java.util.List;
import java.util.Objects;

public class MessageReciever implements Runnable {
  TopicListener textTopic = new TextTopic();
  BinaryTopic binaryTopic = new BinaryTopic();
  public

  /*@Override
  public boolean recieve(Message message) {
    if (Objects.nonNull(message)) {
      if (message.messageType() == MessageType.TEXT) {
        textTopic.re(message);
      } else if (message.messageType() == MessageType.BINARY) {
        binaryTopic.publish(message);
      }
      return true;
    }
  }*/
  @Override
  void run() {
    boolean messageInteruppted = false;
    while(!messageInteruppted) {
      try {
        List<Message> list = TextTopic.getMessageList();
        Message message = list.getLast();
        System.out.println("Message recieved: " + message);
      } catch (Exception e) {
        messageInteruppted = true;
      }
    }
  }
}
