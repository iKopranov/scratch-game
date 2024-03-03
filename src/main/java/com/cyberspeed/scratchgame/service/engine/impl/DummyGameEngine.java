package com.cyberspeed.scratchgame.service.engine.impl;

import com.cyberspeed.scratchgame.model.GameConfig;
import com.cyberspeed.scratchgame.model.Symbol;
import com.cyberspeed.scratchgame.model.WinCombination;
import com.cyberspeed.scratchgame.service.engine.IGameEngine;
import java.util.List;
import java.util.Map;

public class DummyGameEngine implements 
    IGameEngine<GameConfig, Map<String, List<WinCombination>>, List<List<String>>, Symbol> {

  @Override
  public void initGame(GameConfig config) {
    
  }

  @Override
  public List<List<String>> getMatrix() {
    return List.of(
        List.of("A", "B", "C"),
        List.of("E", "B", "10x"),
        List.of("F", "D", "B")
    );
  }

  @Override
  public Map<String, List<WinCombination>> getWinCombinations(List<List<String>> matrix) {
    final var winCombination = new WinCombination();
    winCombination.setName("same_symbol_5_times");
    return Map.of("B", List.of(winCombination));
  }

  @Override
  public Double getReward(Double betAmount, Map<String, List<WinCombination>> winCombinations, Symbol bonus) {
    return 50000d;
  }

  @Override
  public Symbol getBonus(List<List<String>> matrix) {
    final var symbol = new Symbol();
    symbol.setName("10x");
    return symbol;
  }
}
