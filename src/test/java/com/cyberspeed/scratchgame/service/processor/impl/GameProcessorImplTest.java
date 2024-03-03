package com.cyberspeed.scratchgame.service.processor.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.cyberspeed.scratchgame.model.Symbol;
import com.cyberspeed.scratchgame.model.WinCombination;
import com.cyberspeed.scratchgame.service.engine.impl.DummyGameEngine;
import com.cyberspeed.scratchgame.service.engine.impl.SimpleGameEngine;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
    assertEquals(50000d, result.getReward());
    assertEquals(expectedWinCombinations, result.getAppliedWinningCombinations());
    assertEquals("10x", result.getAppliedBonusSymbol());
  }

  @Test
  void shouldReturnGameResultWhenSimpleEngine() {
    // given
    final var simpleGameEngine = Mockito.mock(SimpleGameEngine.class);
    final var gameProcessor = new GameProcessorImpl(simpleGameEngine);

    final var bonusName = "10x";
    var matrix = List.of(
        List.of("A", "B", "C"),
        List.of("E", "B", bonusName),
        List.of("F", "D", "B")
    );
    final var winCombination = new WinCombination();
    winCombination.setName("same_symbol_5_times");
    var winCombinations = Map.of("B", List.of(winCombination));

    final var symbol = new Symbol();
    symbol.setName(bonusName);
    final double betAmount = 1000d;
    final double reward = 50000d;

    // when
    when(simpleGameEngine.getMatrix()).thenReturn(matrix);
    when(simpleGameEngine.getWinCombinations(matrix)).thenReturn(winCombinations);
    when(simpleGameEngine.getBonus(matrix)).thenReturn(symbol);
    when(simpleGameEngine.getReward(any(), any(), any())).thenReturn(reward);
    
    final var result = gameProcessor.bet(betAmount);
    
    // then
    var expectedWinCombinations = Map.of("B", List.of("same_symbol_5_times"));

    assertEquals(matrix, result.getMatrix());
    assertEquals(reward, result.getReward());
    assertEquals(expectedWinCombinations, result.getAppliedWinningCombinations());
    assertEquals(bonusName, result.getAppliedBonusSymbol());
  }

}