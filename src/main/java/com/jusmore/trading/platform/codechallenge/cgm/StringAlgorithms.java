package com.jusmore.trading.platform.codechallenge.cgm;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class StringAlgorithms {

  private StringAlgorithms() {
  }

  public static Optional<Character> firstNonRepeatedCharacter(String input) {
    if (input == null || input.isEmpty()) {
      return Optional.empty();
    }

    Map<Character, Integer> counts = countCharacters(input);

    for (int index = 0; index < input.length(); index++) {
      char current = input.charAt(index);
      if (counts.get(current) == 1) {
        return Optional.of(current);
      }
    }

    return Optional.empty();
  }

  public static Optional<Character> firstRepeatedCharacter(String input) {
    if (input == null || input.isEmpty()) {
      return Optional.empty();
    }

    Map<Character, Integer> counts = countCharacters(input);

    for (int index = 0; index < input.length(); index++) {
      char current = input.charAt(index);
      if (counts.get(current) > 1) {
        return Optional.of(current);
      }
    }

    return Optional.empty();
  }

  private static Map<Character, Integer> countCharacters(String input) {
    Map<Character, Integer> counts = new HashMap<>();
    for (int index = 0; index < input.length(); index++) {
      counts.merge(input.charAt(index), 1, Integer::sum);
    }
    return counts;
  }
}
