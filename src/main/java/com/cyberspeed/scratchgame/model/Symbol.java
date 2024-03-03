package com.cyberspeed.scratchgame.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Symbol {

  private String name;
  @JsonProperty("reward_multiplier")
  private Double rewardMultiplier;
  private SymbolType type;
  private Double extra;
  private String impact;
  
  public Double addBonus(Double reward) {
    if (this.rewardMultiplier != null ) {
      return reward * this.rewardMultiplier;
    } else if (this.extra != null) {
      return reward + this.extra;
    } else {
      return reward;
    }
  }
}
