package com.cyberspeed.scratchgame.service.engine.impl;

import com.cyberspeed.scratchgame.service.engine.IGameEngine;
import java.util.List;
import java.util.Map;

public class DummyGameEngine implements IGameEngine<String, Map<String, List<String>>, List<List<String>>, String> {

  @Override
  public void initGame(String config) {
    
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
  public Map<String, List<String>> getWinCombinations(List<List<String>> matrix) {
    return Map.of("B", List.of("same_symbol_5_times"));
  }

  @Override
  public Double getReward(Double betAmount, Map<String, List<String>> winCombinations, String bonus) {
    return 50000d;
  }

  @Override
  public String getBonus(List<List<String>> matrix) {
    return "10x";
  }
}
