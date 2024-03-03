package com.cyberspeed.scratchgame.service.impl;

import com.cyberspeed.scratchgame.model.GameConfig;
import com.cyberspeed.scratchgame.model.WinCombination;
import com.cyberspeed.scratchgame.service.WinCombinationsAdder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SameSymbolsHorizontallyWinCombinationsAdder implements WinCombinationsAdder {

  public static final String SAME_SYMBOL_HORIZONTALLY_NAME = "same_symbols_horizontally";

  private final GameConfig gameConfig;
  
  public SameSymbolsHorizontallyWinCombinationsAdder(GameConfig gameConfig) {
    this.gameConfig = gameConfig;
  }

  @Override
  public void addWinCombinations(List<List<String>> matrix,
      Map<String, List<WinCombination>> symbolWinCombinations
  ) {
    var winCombination = gameConfig.getWinCombinations().get(SAME_SYMBOL_HORIZONTALLY_NAME);
    var rowsSize = gameConfig.getRows();

    for (var row : matrix) {
      String symbol = null;
      int count = 0;
      for (var cell : row) {
        if (symbol == null || cell.equals(symbol)) {
          count++;
        } else {
          addWinCombination(symbol, count, rowsSize, winCombination, symbolWinCombinations);
          count = 1;
        }
        symbol = cell;
      }
      addWinCombination(symbol, count, rowsSize, winCombination, symbolWinCombinations);
    }
  }

  @Override
  public String getName() {
    return SAME_SYMBOL_HORIZONTALLY_NAME;
  }

  private void addWinCombination(
      String symbol, int count, int rows, WinCombination winCombination,
      Map<String, List<WinCombination>> symbolWinCombinations) {
    if (count >= rows) {
      symbolWinCombinations.computeIfAbsent(symbol, k -> new ArrayList<>()).add(winCombination);
    }
  }
}
