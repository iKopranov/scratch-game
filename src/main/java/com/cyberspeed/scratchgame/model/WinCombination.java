package com.cyberspeed.scratchgame.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WinCombination {
  private String name;
  private Double rewardMultiplier;
  private String when;
  private Integer count;
  private String group;
}
