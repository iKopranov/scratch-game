package com.cyberspeed.scratchgame.service.processor;

import com.cyberspeed.scratchgame.service.engine.IGameEngine;

public interface IGameProcessor<T, K, M, B, R> {
  IGameEngine<T, K, M, B> getGameEngine();
  default R bet(Double betAmount) {
    final var gameEngine = getGameEngine();
    final M matrix = gameEngine.getMatrix();
    final K winCombinations = gameEngine.getWinCombinations(matrix);
    final B bonus = gameEngine.getBonus(matrix);
    final var reward = gameEngine.getReward(betAmount, winCombinations, bonus);
    return getGameResult(matrix, reward, winCombinations, bonus);
  }

  R getGameResult(M matrix, Double reward, K winCombinations, B bonus);

}
