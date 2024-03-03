package com.cyberspeed.scratchgame.service.engine.impl;

import com.cyberspeed.scratchgame.model.GameConfig;
import com.cyberspeed.scratchgame.model.WinCombination;
import com.cyberspeed.scratchgame.service.ProbabilityService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AdvancedGameEngine extends SimpleGameEngine {
  
  private static final String SAME_SYMBOL_HORIZONTALLY = "same_symbols_horizontally";
  
  public AdvancedGameEngine(GameConfig gameConfig, ProbabilityService<String> probabilityService) {
    super(gameConfig, probabilityService);
  }

  @Override
  public List<List<String>> getMatrix() {
    final var columnSize = super.getGameConfig().getColumns();
    final var rowSize = super.getGameConfig().getRows();
    return getMatrix(columnSize, rowSize);
  }

  @Override
  public Map<String, List<WinCombination>> getWinCombinations(List<List<String>> matrix) {
    var symbolWinCombinations = new HashMap<String, List<WinCombination>>();
    super.addSameSymbolWinCombinations(matrix, symbolWinCombinations);
    addSameSymbolsHorizontallyWinCombinations(matrix, symbolWinCombinations);
    return symbolWinCombinations;
  }

  public void addSameSymbolsHorizontallyWinCombinations(
      List<List<String>> matrix, HashMap<String, List<WinCombination>> symbolWinCombinations
  ) {
    var winCombination = super.getGameConfig().getWinCombinations().get(SAME_SYMBOL_HORIZONTALLY);
    var rowsSize = super.getGameConfig().getRows();

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

  private void addWinCombination(
      String symbol, int count, int rows, WinCombination winCombination,
      HashMap<String, List<WinCombination>> symbolWinCombinations) {
    if (count >= rows) {
      symbolWinCombinations.computeIfAbsent(symbol, k -> new ArrayList<>()).add(winCombination);
    }
  }
}
