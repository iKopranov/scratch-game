package com.cyberspeed.scratchgame.service;

public interface ProbabilityService<T> {
  
  T getRandomSymbol(Integer column, Integer row);
}
