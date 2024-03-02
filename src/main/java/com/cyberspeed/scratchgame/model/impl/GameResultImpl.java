package com.cyberspeed.scratchgame.model.impl;

import com.cyberspeed.scratchgame.model.GameResult;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameResultImpl implements GameResult {

  private List<List<String>> matrix;
  private Double reward;
  private Map<String, List<String>> appliedWinningCombinations;
  private String appliedBonusSymbol;
}
