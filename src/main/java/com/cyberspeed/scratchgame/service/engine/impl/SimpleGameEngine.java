package com.cyberspeed.scratchgame.service.engine.impl;

import static com.cyberspeed.scratchgame.service.impl.SameSymbolWinCombinationsAdder.SAME_SYMBOLS_NAME;

import com.cyberspeed.scratchgame.model.GameConfig;
import com.cyberspeed.scratchgame.model.Symbol;
import com.cyberspeed.scratchgame.model.SymbolType;
import com.cyberspeed.scratchgame.model.WinCombination;
import com.cyberspeed.scratchgame.service.ProbabilityService;
import com.cyberspeed.scratchgame.service.WinCombinationsAdder;
import com.cyberspeed.scratchgame.service.engine.IGameEngine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import lombok.Getter;

@Getter
public class SimpleGameEngine 
    implements IGameEngine<GameConfig, Map<String, List<WinCombination>>, List<List<String>>, Symbol> {
  
  private GameConfig gameConfig;
  private final ProbabilityService<String> probabilityService;
  private final Map<String,WinCombinationsAdder> winCombinations;
  
  public SimpleGameEngine(
      GameConfig gameConfig, ProbabilityService<String> probabilityService,
      Map<String,WinCombinationsAdder> winCombinations
  ) {
    initGame(gameConfig);
    this.probabilityService = probabilityService;
    this.winCombinations = winCombinations;
  }
  
  @Override
  public void initGame(GameConfig config) {
    config.getSymbols().forEach((key, value) -> value.setName(key));
    config.getWinCombinations().forEach((key, value) -> value.setName(key));
    this.gameConfig = config;
  }

  @Override
  public List<List<String>> getMatrix() {
    return getMatrix(3, 3);
  }

  @Override
  public Map<String, List<WinCombination>> getWinCombinations(List<List<String>> matrix) {
    var symbolWinCombinations = new HashMap<String, List<WinCombination>>();

    winCombinations.get(SAME_SYMBOLS_NAME).addWinCombinations(matrix, symbolWinCombinations);
    return symbolWinCombinations;
  }

  private static Double getCombinationsMultiplier(List<WinCombination> combinations) {
    return combinations.stream()
        .map(WinCombination::getRewardMultiplier)
        .reduce((aRewMultiplier, bRewMultiplier) -> aRewMultiplier * bRewMultiplier)
        .orElse(1d);
  }

  @Override
  public Symbol getBonus(List<List<String>> matrix) {
    final var flatMatrix = matrix.stream()
        .flatMap(List::stream)
        .toList();
    
    for(var cell : flatMatrix) {
      final var symbol = gameConfig.getSymbols().get(cell);
      if (symbol.getType() == SymbolType.BONUS) {
        return symbol;
      }
    }
    return null;
  }

  private void addBonusSymbol(ArrayList<List<String>> matrix) {
    final var random = new Random();
    var randomRow = random.nextInt(3);
    var randomColumn = random.nextInt(3);
    matrix.get(randomRow)
        .set(randomColumn, probabilityService.getRandomSymbol(randomRow, randomColumn, true));
  }

  @Override
  public Double getReward(Double betAmount, Map<String, List<WinCombination>> winCombinations, Symbol bonus) {
    final var reward = getWinCombinationsReward(betAmount, winCombinations);
    if (bonus == null) {
      return reward;
    }
    return winCombinations.isEmpty() ? reward : bonus.addBonus(reward);
  }
  
  private Double getWinCombinationsReward(Double betAmount, Map<String, List<WinCombination>> winCombinations) {
    return winCombinations.entrySet()
        .stream()
        .map(winCombination -> {
          final var combinations = winCombination.getValue();
          var combinationsMultiplier = getCombinationsMultiplier(combinations);
          final var rewardMultiplier = getSymbol(winCombination).getRewardMultiplier();
          return betAmount * rewardMultiplier * combinationsMultiplier;
        })
        .reduce(Double::sum)
        .orElse(0d);
  }

  private Symbol getSymbol(Entry<String, List<WinCombination>> winCombination) {
    return gameConfig.getSymbols()
        .getOrDefault(winCombination.getKey(), new Symbol(null, 1d, null, null, null));
  }

  protected ArrayList<List<String>> getMatrix(Integer columnSize, Integer rowSize) {
    var matrix = new ArrayList<List<String>>();
    for (int column = 0; column < columnSize; column++) {
      var rowList = new ArrayList<String>();
      for (int row = 0; row < rowSize; row++) {
        rowList.add(probabilityService.getRandomSymbol(column, row, false));
      }
      matrix.add(rowList);
    }
    addBonusSymbol(matrix);
    return matrix;
  }
}
