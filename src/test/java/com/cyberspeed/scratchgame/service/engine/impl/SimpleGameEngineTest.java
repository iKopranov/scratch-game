package com.cyberspeed.scratchgame.service.engine.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cyberspeed.scratchgame.model.GameConfig;
import com.cyberspeed.scratchgame.model.Symbol;
import com.cyberspeed.scratchgame.model.SymbolType;
import com.cyberspeed.scratchgame.model.WinCombination;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SimpleGameEngineTest {

  private static final String SOURCE_FILE_PATH =
      System.getProperty("user.dir") + "/src/test/resources/config.json";
  private static final ObjectMapper objectMapper = new ObjectMapper();
  private static GameConfig gameConfig;
  
  @BeforeAll
  static void init() throws IOException {
    objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    var file = new File(SOURCE_FILE_PATH);
    gameConfig = objectMapper.readValue(file, GameConfig.class);
  }
  
  @Test
  void shouldReturnWinCombinations() {
    // given
    final var simpleGameEngine = new SimpleGameEngine();
    simpleGameEngine.initGame(gameConfig);

    var matrix = List.of(
        List.of("A", "A", "B"),
        List.of("A", "+1000", "B"),
        List.of("A", "A", "B")
    );
    
    // when
    final var winCombinations = simpleGameEngine.getWinCombinations(matrix);
    
    // then
    var expectedWinCombinations = Map.of("A", List.of(new WinCombination("same_symbol_5_times", 2D, "same_symbols", 5, "same_symbols")),
        "B", List.of(new WinCombination("same_symbol_3_times", 1d, "same_symbols", 3, "same_symbols"))
        );
    
    assertEquals(expectedWinCombinations, winCombinations);
  }

  @Test
  void shouldReturnBonusSymbol() {
    // given
    final var simpleGameEngine = new SimpleGameEngine();
    simpleGameEngine.initGame(gameConfig);
    
    var matrix = List.of(
        List.of("A", "A", "B"),
        List.of("A", "+1000", "B"),
        List.of("A", "A", "B")
    );

    // when
    final var bonus = simpleGameEngine.getBonus(matrix);

    // then
    var expected = new Symbol("+1000", null, SymbolType.BONUS, 1000d, "extra_bonus");

    assertEquals(expected, bonus);
  }

  @Test
  void shouldReturnReward() {
    // given
    final var simpleGameEngine = new SimpleGameEngine();
    simpleGameEngine.initGame(gameConfig);
    
    var winCombinations = Map.of(
        "B", List.of(new WinCombination("same_symbol_3_times", 1d, "same_symbols", 3, "same_symbols"))
    );
    
    var bonus = new Symbol("10x", 10d, SymbolType.BONUS, null, "multiply_reward");

    // when
    final var reward = 
        simpleGameEngine.getReward(100d, winCombinations, bonus);

    // then

    assertEquals(25000d, reward);
  }

}