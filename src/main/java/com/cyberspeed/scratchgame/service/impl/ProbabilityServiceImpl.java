package com.cyberspeed.scratchgame.service.impl;

import com.cyberspeed.scratchgame.model.Probabilities;
import com.cyberspeed.scratchgame.model.Probabilities.StandardSymbolProbability;
import com.cyberspeed.scratchgame.service.ProbabilityService;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class ProbabilityServiceImpl implements ProbabilityService<String> {

  private final Probabilities probabilities;

  public ProbabilityServiceImpl(Probabilities probabilities) {
    this.probabilities = probabilities;
  }

  @Override
  public String getRandomSymbol(Integer column, Integer row, Boolean isBonus) {
    final var symbolsProbabilities = getSymbolsProbabilities(column, row, isBonus);
      final var totalProbability = getTotalProbability(symbolsProbabilities);

      var probabilityPercentage = new HashMap<String, Double>();
      for (Map.Entry<String, Integer> entry : symbolsProbabilities.entrySet()) {
        double probability = Double.valueOf(entry.getValue()) / totalProbability * 100;
        probabilityPercentage.put(entry.getKey(), probability);
      }
      return selectSymbol(probabilityPercentage);
  }
  
  private Map<String, Integer> getSymbolsProbabilities(Integer column, Integer row, Boolean isBonus) {
    if (isBonus) {
      return probabilities.getBonusSymbols().getSymbols();
    } else {
      return getStandardSymbolProbability(column, row).getSymbols();
    }
  }

  private Integer getTotalProbability(Map<String, Integer> symbols) {
    return symbols
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
                "Symbol probability doesnt exist for column %d and row %d".formatted(column, row)
            )
        );
  }

  private String selectSymbol(Map<String, Double> probabilities) {
    var random = new Random().nextDouble() * 100;
    double cumulativeProbability = 0d;
    for (Map.Entry<String, Double> entry : probabilities.entrySet()) {
      cumulativeProbability += entry.getValue();
      if (random <= cumulativeProbability) {
        return entry.getKey();
      }
    }
    return probabilities.entrySet()
        .stream()
        .sorted((a1, a2) -> a2.getValue().compareTo(a1.getValue()))
        .map(Entry::getKey)
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Last symbol probability doesnt exist"));
  }
}
