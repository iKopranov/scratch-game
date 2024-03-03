package com.cyberspeed.scratchgame.service.engine.impl;

import static com.cyberspeed.scratchgame.service.impl.SameSymbolWinCombinationsAdder.SAME_SYMBOLS_NAME;
import static com.cyberspeed.scratchgame.service.impl.SameSymbolsHorizontallyWinCombinationsAdder.SAME_SYMBOL_HORIZONTALLY_NAME;

import com.cyberspeed.scratchgame.model.GameConfig;
import com.cyberspeed.scratchgame.model.WinCombination;
import com.cyberspeed.scratchgame.service.ProbabilityService;
import com.cyberspeed.scratchgame.service.WinCombinationsAdder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AdvancedGameEngine extends SimpleGameEngine {
  
  public AdvancedGameEngine(
      GameConfig gameConfig, ProbabilityService<String> probabilityService,
      Map<String,WinCombinationsAdder> winCombinations
      ) {
    super(gameConfig, probabilityService, winCombinations);
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
    super.getWinCombinations().get(SAME_SYMBOLS_NAME).addWinCombinations(matrix, symbolWinCombinations);
    super.getWinCombinations().get(SAME_SYMBOL_HORIZONTALLY_NAME).addWinCombinations(matrix, symbolWinCombinations);
    //todo: implement interface WinCombinationsAdder for each type of winCombination: vertically, diagonally
    return symbolWinCombinations;
  }
}
