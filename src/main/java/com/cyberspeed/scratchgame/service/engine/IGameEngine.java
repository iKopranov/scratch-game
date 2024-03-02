package com.cyberspeed.scratchgame.service.engine;

public interface IGameEngine<T, K, M, B> {
  
  void initGame(T config);

  M getMatrix();

  K getWinCombinations(M matrix);
  
  Long getReward(Long betAmount, K winCombinations, B bonus);


  B getBonus(M matrix);
}
