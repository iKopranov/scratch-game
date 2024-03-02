package com.cyberspeed.scratchgame.service.impl;

import com.cyberspeed.scratchgame.service.ProbabilityService;

public class DummyProbabilityService implements ProbabilityService<String> {

  @Override
  public String getRandomSymbol(Integer column, Integer row) {
    return "5x";
  }
}
