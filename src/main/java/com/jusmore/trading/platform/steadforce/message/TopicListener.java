package com.jusmore.trading.platform.steadforce.message;

public interface TopicListener {
  boolean publish(Message message);
  boolean send(Message message);
}
