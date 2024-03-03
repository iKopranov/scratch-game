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

  @Override
  public String toString() {
    var stringBuilder = new StringBuilder();
    stringBuilder.append("Matrix:\n");
    for (List<String> row : matrix) {
      stringBuilder.append(row).append("\n");
    }
    stringBuilder.append("Reward: ").append(reward).append("\n");
    stringBuilder.append("Applied Winning Combinations:\n");
    
    for (Map.Entry<String, List<String>> entry : appliedWinningCombinations.entrySet()) {
      stringBuilder.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
    }
    stringBuilder.append("Applied Bonus Symbol: ").append(appliedBonusSymbol).append("\n");
    return stringBuilder.toString();
  }
}
