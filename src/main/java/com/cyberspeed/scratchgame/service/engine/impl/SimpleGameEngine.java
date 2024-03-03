package com.cyberspeed.scratchgame.service.engine.impl;

import com.cyberspeed.scratchgame.model.GameConfig;
import com.cyberspeed.scratchgame.model.Symbol;
import com.cyberspeed.scratchgame.model.SymbolType;
import com.cyberspeed.scratchgame.model.WinCombination;
import com.cyberspeed.scratchgame.service.engine.IGameEngine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;


public class SimpleGameEngine 
    implements IGameEngine<GameConfig, Map<String, List<WinCombination>>, List<List<String>>, Symbol> {
  
  private GameConfig gameConfig;
  
  @Override
  public void initGame(GameConfig config) {
    config.getSymbols().forEach((key, value) -> value.setName(key));
    config.getWinCombinations().forEach((key, value) -> value.setName(key));
    this.gameConfig = config;
  }

  @Override
  public List<List<String>> getMatrix() {
    return null;
  }

  @Override
  public Map<String, List<WinCombination>> getWinCombinations(List<List<String>> matrix) {
    var symbolWinCombinations = new HashMap<String, List<WinCombination>>();
    
    final var symbolCounts = matrix.stream()
        .flatMap(List::stream)
        .collect(Collectors.groupingBy(s -> s, Collectors.counting()));

    final var winCombinations = gameConfig.getWinCombinations()
        .values()
        .stream()
        .collect(Collectors.groupingBy(WinCombination::getCount));
    
    for (var symbol : symbolCounts.keySet()) {
      var symbolCount = symbolCounts.get(symbol);
      if (winCombinations.containsKey(symbolCount)) {
        symbolWinCombinations.computeIfAbsent(symbol, k -> new ArrayList<>())
            .addAll(winCombinations.get(symbolCount));
      }
    }
    
    return symbolWinCombinations;
  }

  private static Double getCombinationsMultiplier(List<WinCombination> combinations) {
    return combinations.stream()
        .map(WinCombination::getRewardMultiplier)
        .reduce((aRewMultiplier, bRewMultiplier) -> aRewMultiplier * bRewMultiplier)
        .orElse(1d);
  }

  private static Double addBonus(Symbol bonus, Double reward) {
    if (bonus == null) {
      return reward;
    }
    if (bonus.getRewardMultiplier() != null ) {
      return reward * bonus.getRewardMultiplier();
    } else {
      return reward + bonus.getExtra();
    }
  }

  @Override
  public Double getReward(Double betAmount, Map<String, List<WinCombination>> winCombinations, Symbol bonus) {
    final var reward = getWinCombinationsReward(betAmount, winCombinations);
    return addBonus(bonus, reward);
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
}