package com.cyberspeed.scratchgame.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class Probabilities {
  @JsonProperty("standard_symbols")
  private List<StandardSymbolProbability> standardSymbols;
  @JsonProperty("bonus_symbols")
  private BonusSymbolProbability bonusSymbols;

  @Data
  public static class StandardSymbolProbability {
    private int column;
    private int row;
    private Map<String, Integer> symbols;
  }

  @Data
  public static class BonusSymbolProbability {
    private Map<String, Integer> symbols;
  }
}
