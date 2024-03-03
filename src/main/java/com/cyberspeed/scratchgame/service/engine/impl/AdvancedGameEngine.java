package com.cyberspeed.scratchgame.service.engine.impl;

import com.cyberspeed.scratchgame.model.GameConfig;
import com.cyberspeed.scratchgame.model.WinCombination;
import com.cyberspeed.scratchgame.service.ProbabilityService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AdvancedGameEngine extends SimpleGameEngine {
  
  
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
    //todo: add 
    return symbolWinCombinations;
  }
}
