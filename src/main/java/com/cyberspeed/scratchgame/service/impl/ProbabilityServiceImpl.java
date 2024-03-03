package com.cyberspeed.scratchgame.service.impl;

import com.cyberspeed.scratchgame.model.Probabilities;
import com.cyberspeed.scratchgame.model.Probabilities.StandardSymbolProbability;
import com.cyberspeed.scratchgame.service.ProbabilityService;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ProbabilityServiceImpl implements ProbabilityService<String> {

  private final Probabilities probabilities;

  public ProbabilityServiceImpl(Probabilities probabilities) {
    this.probabilities = probabilities;
  }

  @Override
  public String getRandomSymbol(Integer column, Integer row) {
    final var standardSymbolProbability = getStandardSymbolProbability(column, row);
    final var totalProbability = getTotalProbability(standardSymbolProbability);

    var probabilityPercentage = new HashMap<String, Double>();
    for (Map.Entry<String, Integer> entry : standardSymbolProbability.getSymbols().entrySet()) {
      double probability = Double.valueOf(entry.getValue()) / totalProbability * 100;
      probabilityPercentage.put(entry.getKey(), probability);
    }
    return selectSymbol(probabilityPercentage);
  }

  private Integer getTotalProbability(StandardSymbolProbability standardSymbolProbability) {
    return standardSymbolProbability.getSymbols()
        .values()
        .stream()
        .reduce(Integer::sum)
        .orElse(0);
  }

  private StandardSymbolProbability getStandardSymbolProbability(Integer column, Integer row) {
    return probabilities.getStandardSymbols()
        .stream()
        .filter(probability -> column.compareTo(probability.getColumn()) == 0
            && row.compareTo(probability.getRow()) == 0)
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException(
                "Standard symbol probability doesnt exist for column %d and row %d"
                    .formatted(column, row)
            )
        );
  }

  private String selectSymbol(Map<String, Double> probabilities) {
    var random = new Random().nextDouble() * 100;
    var cumulativeProbability = 0;
    for (Map.Entry<String, Double> entry : probabilities.entrySet()) {
      cumulativeProbability += entry.getValue();
      if (random <= cumulativeProbability) {
        return entry.getKey();
      }
    }
    return null;
  }
}
