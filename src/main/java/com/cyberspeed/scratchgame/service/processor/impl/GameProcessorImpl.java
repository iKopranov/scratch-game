package com.cyberspeed.scratchgame.service.processor.impl;

import com.cyberspeed.scratchgame.model.GameResult;
import com.cyberspeed.scratchgame.model.impl.GameResultImpl;
import com.cyberspeed.scratchgame.service.engine.IGameEngine;
import com.cyberspeed.scratchgame.service.engine.impl.DummyGameEngine;
import com.cyberspeed.scratchgame.service.processor.IGameProcessor;
import java.util.List;
import java.util.Map;

public class GameProcessorImpl implements 
    IGameProcessor<String, Map<String, List<String>>, List<List<String>>, String, GameResult> {

  @Override
  public IGameEngine<String, Map<String, List<String>>, List<List<String>>, String> getGameEngine() {
    return new DummyGameEngine();
  }

  @Override
  public GameResult getGameResult(List<List<String>> matrix, Long reward, Map<String, List<String>> winCombinations, String bonus) {
    return new GameResultImpl(matrix, reward, winCombinations, bonus);
  }
}
