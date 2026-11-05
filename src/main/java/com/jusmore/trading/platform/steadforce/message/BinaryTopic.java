package com.jusmore.trading.platform.steadforce.message;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class BinaryTopic implements TopicListener {
  private static final LinkedList<Message> list = new LinkedList();

  @Override
  public boolean publish(Message message) {
    if (Objects.nonNull(message)) {
      if (message.messageType() == MessageType.BINARY) {

        list.add(message);
        return true;
      }

    }
    return false;
  }

  @Override
  public boolean send(Message message) {
    return false;
  }

  public static List<Message> getMessageList() {
    return list;
  }
}
