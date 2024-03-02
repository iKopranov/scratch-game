package com.cyberspeed.scratchgame.service.processor.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cyberspeed.scratchgame.service.engine.impl.DummyGameEngine;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class GameProcessorImplTest {
  
  @Test
  void shouldReturnGameResultWhenDummyEngine() {
    // given
    final var gameProcessor = new GameProcessorImpl(new DummyGameEngine());
    // when
    final var result = gameProcessor.bet(1000d);
    // then
    var expectedMatrix = List.of(
        List.of("A", "B", "C"),
        List.of("E", "B", "10x"),
        List.of("F", "D", "B")
    );
    
    var expectedWinCombinations = Map.of("B", List.of("same_symbol_5_times"));
    
    assertEquals(expectedMatrix, result.getMatrix());
    assertEquals(50000L, result.getReward());
    assertEquals(expectedWinCombinations, result.getAppliedWinningCombinations());
    assertEquals("10x", result.getAppliedBonusSymbol());
  }

}