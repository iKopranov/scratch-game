package com.cyberspeed.scratchgame.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
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
  private Long count;
  private String group;
  @JsonProperty("covered_areas")
  private List<List<String>> coveredAreas;
}
