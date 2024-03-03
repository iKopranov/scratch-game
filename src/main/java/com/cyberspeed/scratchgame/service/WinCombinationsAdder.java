package com.cyberspeed.scratchgame.service;

import com.cyberspeed.scratchgame.model.WinCombination;
import java.util.List;
import java.util.Map;

public interface WinCombinationsAdder {
  
  void addWinCombinations(
      List<List<String>> matrix, Map<String, List<WinCombination>> symbolWinCombinations
  );
  
  String getName();

}
