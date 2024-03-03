package com.cyberspeed.scratchgame.service.processor.impl;

import com.cyberspeed.scratchgame.model.GameConfig;
import com.cyberspeed.scratchgame.model.GameResult;
import com.cyberspeed.scratchgame.model.Symbol;
import com.cyberspeed.scratchgame.model.WinCombination;
import com.cyberspeed.scratchgame.model.impl.GameResultImpl;
import com.cyberspeed.scratchgame.service.engine.IGameEngine;
import com.cyberspeed.scratchgame.service.processor.IGameProcessor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameProcessorImpl implements 
    IGameProcessor<GameConfig, Map<String, List<WinCombination>>, List<List<String>>, Symbol, GameResult> {

  private final IGameEngine<GameConfig, Map<String, List<WinCombination>>, List<List<String>>, Symbol> gameEngine;
  
  public GameProcessorImpl(
      IGameEngine<GameConfig, Map<String, List<WinCombination>>,
          List<List<String>>, Symbol> gameEngine
  ) {
    this.gameEngine = gameEngine;
  }
  @Override
  public IGameEngine<GameConfig, Map<String, List<WinCombination>>, List<List<String>>, Symbol> getGameEngine() {
    return gameEngine;
  }

  @Override
  public GameResult getGameResult(
      List<List<String>> matrix, Double reward, Map<String, 
      List<WinCombination>> winCombinations, Symbol bonus
  ) {
    var transformed = new HashMap<String, List<String>>();
    for (Map.Entry<String, List<WinCombination>> entry : winCombinations.entrySet()) {
      var names = new ArrayList<String>();
      for (var nameKeeper : entry.getValue()) {
        names.add(nameKeeper.getName());
      }
      transformed.put(entry.getKey(), names);
    }
    return new GameResultImpl(matrix, reward, transformed, bonus.getName());
  }
}
