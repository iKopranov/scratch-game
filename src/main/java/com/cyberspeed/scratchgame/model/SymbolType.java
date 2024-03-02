package com.cyberspeed.scratchgame.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SymbolType {
  STANDARD("standard"),
  BONUS("bonus");
  
  private final String title;
  SymbolType(String title) {
    this.title = title;
  }
  
  @JsonValue
  public String getTitle() {
    return this.title;
  }

}
