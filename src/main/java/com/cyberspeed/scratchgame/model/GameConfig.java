package com.cyberspeed.scratchgame.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameConfig {

  private Map<String, Symbol> symbols;
  private Probabilities probabilities;
  @JsonProperty("win_combinations")
  private Map<String, WinCombination> winCombinations;
}
