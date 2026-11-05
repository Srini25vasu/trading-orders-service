package com.jusmore.trading.platform.steadforce.message;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class TextTopic implements TopicListener{

  //private ConcurrentHashMap<String, Message> concurrentHashMap = new ConcurrentHashMap<>();

  private static List<Message> list = Collections.synchronizedList(new LinkedList<>());

  @Override
  public boolean publish(Message message) {
    if(Objects.nonNull(message)) {
      if (message.messageType() == MessageType.TEXT) {
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
