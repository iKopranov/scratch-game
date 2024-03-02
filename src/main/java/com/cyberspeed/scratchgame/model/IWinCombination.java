package com.cyberspeed.scratchgame.model;

public interface IWinCombination<T, K> {
  
  T getWinType();
  Long getRewardMultiplier();
  K getWhen();
  Long getCount();

}
