package com.cyberspeed.scratchgame.service.processor.impl;

import com.cyberspeed.scratchgame.model.GameResult;
import com.cyberspeed.scratchgame.model.impl.GameResultImpl;
import com.cyberspeed.scratchgame.service.engine.IGameEngine;
import com.cyberspeed.scratchgame.service.processor.IGameProcessor;
import java.util.List;
import java.util.Map;

public class GameProcessorImpl implements 
    IGameProcessor<String, Map<String, List<String>>, List<List<String>>, String, GameResult> {

  private final IGameEngine<String, Map<String, List<String>>, List<List<String>>, String> gameEngine;
  
  public GameProcessorImpl(IGameEngine<String, Map<String, List<String>>, List<List<String>>, String> gameEngine) {
    this.gameEngine = gameEngine;
  }
  @Override
  public IGameEngine<String, Map<String, List<String>>, List<List<String>>, String> getGameEngine() {
    return gameEngine;
  }

  @Override
  public GameResult getGameResult(List<List<String>> matrix, Long reward, Map<String, List<String>> winCombinations, String bonus) {
    return new GameResultImpl(matrix, reward, winCombinations, bonus);
  }
}
