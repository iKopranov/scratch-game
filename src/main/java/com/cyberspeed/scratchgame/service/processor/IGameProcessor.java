package com.cyberspeed.scratchgame.service.processor;

import com.cyberspeed.scratchgame.service.engine.IGameEngine;

public interface IGameProcessor<T, K, M, R> {
  IGameEngine<T, K, M> getGameEngine();
  default R bet(Long betAmount) {
    final var gameEngine = getGameEngine();
    final M matrix = gameEngine.getMatrix();
    final K winCombinations = gameEngine.getWinCombinations(matrix);
    final Long reward = gameEngine.getReward(betAmount, winCombinations);
    return getGameResult(matrix, reward, winCombinations);
  }

  R getGameResult(M matrix, Long reward, K winCombinations);

}
