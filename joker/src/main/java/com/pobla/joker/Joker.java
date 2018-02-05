package com.pobla.joker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Joker {

  private final static List<String> jokeLists;

  static {
    jokeLists = new ArrayList<>();
    jokeLists.add("What do you call a fish with no eyes?\nA fsh!");
    jokeLists.add("What did Mario say when he broke up with Princess Peach?\nIt's not you, it's a-me, Mario!");
    jokeLists.add("Why don't blind people go skydiving?\nBecause it scares their dogs too much.");
    jokeLists.add("What do you call an alligator that wears a vest?\nAn investigator.");
    jokeLists.add("How do you feel when there is no coffee?\nDepresso.");
  }

  private final Random random = new Random(System.currentTimeMillis());

  public String getJoke() {
    int jokeNumber = Math.abs(random.nextInt() % jokeLists.size());
    return jokeLists.get(jokeNumber);
  }
}
