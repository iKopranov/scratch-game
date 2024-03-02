package com.cyberspeed.scratchgame.model;

import java.util.List;
import java.util.Map;

public interface GameResult {

  List<List<String>> getMatrix();
  Double getReward();
  Map<String, List<String>> getAppliedWinningCombinations();
  String getAppliedBonusSymbol();

}
