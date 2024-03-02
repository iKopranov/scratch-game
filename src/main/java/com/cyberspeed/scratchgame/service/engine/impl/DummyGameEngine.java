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
    return List.of();
  }

  @Override
  public Map<String, List<String>> getWinCombinations(List<List<String>> matrix) {
    return Map.of();
  }

  @Override
  public Long getReward(Long betAmount, Map<String, List<String>> winCombinations, String bonus) {
    return 1000L;
  }

  @Override
  public String getBonus(List<List<String>> matrix) {
    return "";
  }
}
