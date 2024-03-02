package com.cyberspeed.scratchgame.service.engine.impl;

import com.cyberspeed.scratchgame.model.GameConfig;
import com.cyberspeed.scratchgame.model.Symbol;
import com.cyberspeed.scratchgame.model.WinCombination;
import com.cyberspeed.scratchgame.service.engine.IGameEngine;
import java.util.List;
import java.util.Map;


public class SimpleGameEngine 
    implements IGameEngine<GameConfig, Map<String, List<WinCombination>>, List<List<String>>, Symbol> {
  
  private GameConfig gameConfig;

  @Override
  public void initGame(GameConfig config) {
    this.gameConfig = config;
  }

  @Override
  public List<List<String>> getMatrix() {
    return null;
  }

  @Override
  public Map<String, List<WinCombination>> getWinCombinations(List<List<String>> matrix) {
    
    return null;
  }

  @Override
  public Long getReward(Long betAmount, Map<String, List<WinCombination>> winCombinations, Symbol bonus) {
    return null;
  }

  @Override
  public Symbol getBonus(List<List<String>> matrix) {
    return null;
  }
}
