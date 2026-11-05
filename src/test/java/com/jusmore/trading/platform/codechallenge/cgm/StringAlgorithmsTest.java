package com.jusmore.trading.platform.codechallenge.cgm;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringAlgorithmsTest {

  @Test
  void shouldReturnFirstNonRepeatedCharacter() {
    assertEquals(Optional.of('w'), StringAlgorithms.firstNonRepeatedCharacter("swiss"));
    assertEquals(Optional.of('h'), StringAlgorithms.firstNonRepeatedCharacter("hello"));
  }

  @Test
  void shouldReturnEmptyForRepeatedNullOrEmptyInput() {
    assertEquals(Optional.empty(), StringAlgorithms.firstNonRepeatedCharacter("aabb"));
    assertEquals(Optional.empty(), StringAlgorithms.firstNonRepeatedCharacter(null));
    assertEquals(Optional.empty(), StringAlgorithms.firstNonRepeatedCharacter(""));
  }

  @Test
  void shouldBeCaseSensitive() {
    assertEquals(Optional.of('A'), StringAlgorithms.firstNonRepeatedCharacter("aAa"));
  }

  @Test
  void shouldReturnFirstRepeatedCharacter() {
    assertEquals(Optional.of('s'), StringAlgorithms.firstRepeatedCharacter("ahss"));
  }
}
