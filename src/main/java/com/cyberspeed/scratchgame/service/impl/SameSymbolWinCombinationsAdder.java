package com.cyberspeed.scratchgame.service.impl;

import com.cyberspeed.scratchgame.model.GameConfig;
import com.cyberspeed.scratchgame.model.WinCombination;
import com.cyberspeed.scratchgame.service.WinCombinationsAdder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SameSymbolWinCombinationsAdder implements WinCombinationsAdder {

  public static final String SAME_SYMBOLS_NAME = "same_symbols";
  private final GameConfig gameConfig;
  
  public SameSymbolWinCombinationsAdder(GameConfig gameConfig) {
    this.gameConfig = gameConfig;
  }

  @Override
  public void addWinCombinations(List<List<String>> matrix,
      Map<String, List<WinCombination>> symbolWinCombinations
  ) {
    final var symbolCounts = matrix.stream()
        .flatMap(List::stream)
        .collect(Collectors.groupingBy(s -> s, Collectors.counting()));

    final var winCombinations = gameConfig.getWinCombinations()
        .values()
        .stream()
        .filter(winCombination -> SAME_SYMBOLS_NAME.equals(winCombination.getGroup()))
        .collect(Collectors.groupingBy(WinCombination::getCount));

    for (var symbol : symbolCounts.keySet()) {
      var symbolCount = symbolCounts.get(symbol);
      if (winCombinations.containsKey(symbolCount)) {
        symbolWinCombinations.computeIfAbsent(symbol, k -> new ArrayList<>())
            .addAll(winCombinations.get(symbolCount));
      }
    }
  }

  @Override
  public String getName() {
    return SAME_SYMBOLS_NAME;
  }
}
