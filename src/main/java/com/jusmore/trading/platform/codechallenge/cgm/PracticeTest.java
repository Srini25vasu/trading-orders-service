package com.jusmore.trading.platform.codechallenge.cgm;

public class PracticeTest {
  static void main() {
    testEquals();

  }

  static void testEquals() {
    System.out.println(new String("a") == "a");
    Integer a = 127, b = 127;
    System.out.println(a == b);
    int i = 0;
    System.out.println(i++);
    int j = 0;
    System.out.println(++j);

    String x = new String("hello");
    String y = new String("hello");

    System.out.println(x == y);
    System.out.println(x.equals(y));
  }
}
