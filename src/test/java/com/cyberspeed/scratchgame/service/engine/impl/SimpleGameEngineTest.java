package com.cyberspeed.scratchgame.service.engine.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.cyberspeed.scratchgame.model.GameConfig;
import com.cyberspeed.scratchgame.model.Symbol;
import com.cyberspeed.scratchgame.model.SymbolType;
import com.cyberspeed.scratchgame.model.WinCombination;
import com.cyberspeed.scratchgame.service.impl.ProbabilityServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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
    objectMapper.setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy());
    var file = new File(SOURCE_FILE_PATH);
    gameConfig = objectMapper.readValue(file, GameConfig.class);
  }

  @Test
  void shouldReturnWinCombinations() {
    // given
    final var simpleGameEngine =
        new SimpleGameEngine(gameConfig, new ProbabilityServiceImpl(gameConfig.getProbabilities()));
    var matrix = List.of(
        List.of("A", "A", "B"),
        List.of("A", "+1000", "B"),
        List.of("A", "A", "B")
    );

    // when
    final var winCombinations = simpleGameEngine.getWinCombinations(matrix);

    // then
    var expectedWinCombinations = Map.of("A",
        List.of(new WinCombination("same_symbol_5_times", 2d, "same_symbols", 5L, "same_symbols")),
        "B",
        List.of(new WinCombination("same_symbol_3_times", 1d, "same_symbols", 3L, "same_symbols"))
    );
    assertEquals(expectedWinCombinations, winCombinations);
  }

  @Test
  void shouldReturnEmptyWinCombinations() {
    // given
    final var simpleGameEngine =
        new SimpleGameEngine(gameConfig, new ProbabilityServiceImpl(gameConfig.getProbabilities()));
    var matrix = List.of(
        List.of("D", "A", "B"),
        List.of("C", "+1000", "E"),
        List.of("F", "F", "B")
    );

    // when
    final var winCombinations = simpleGameEngine.getWinCombinations(matrix);

    // then
    assertTrue(winCombinations.isEmpty());
  }

  @Test
  void shouldReturnBonusSymbol() {
    // given
    final var simpleGameEngine =
        new SimpleGameEngine(gameConfig, new ProbabilityServiceImpl(gameConfig.getProbabilities()));
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
  void shouldReturnRewardWhenWinCombinationsExist() {
    // given
    final var simpleGameEngine =
        new SimpleGameEngine(gameConfig, new ProbabilityServiceImpl(gameConfig.getProbabilities()));

    var winCombinations = Map.of(
        "A", List.of(
            new WinCombination(
                "same_symbol_5_times", 2d, "same_symbols", 5L, "same_symbols")
        )
    );
    var bonus = new Symbol("+500", null, SymbolType.BONUS, 500d, "multiply_reward");

    // when
    final var reward =
        simpleGameEngine.getReward(1000d, winCombinations, bonus);

    // then
    assertEquals(100500, reward);
  }

  @Test
  void shouldReturnRewardZeroWhenWinCombinationsAreEmptyAndBonusExtra() {
    // given
    final var simpleGameEngine =
        new SimpleGameEngine(gameConfig, new ProbabilityServiceImpl(gameConfig.getProbabilities()));

    var winCombinations = new HashMap<String, List<WinCombination>>();
    var bonus = new Symbol("+500", null, SymbolType.BONUS, 500d, "multiply_reward");

    // when
    final var reward =
        simpleGameEngine.getReward(1000d, winCombinations, bonus);

    // then
    assertEquals(0d, reward);
  }

  @Test
  void shouldReturnRewardZeroWhenWinCombinationsAreEmpty() {
    // given
    final var simpleGameEngine =
        new SimpleGameEngine(gameConfig, new ProbabilityServiceImpl(gameConfig.getProbabilities()));

    var winCombinations = new HashMap<String, List<WinCombination>>();
    var bonus = new Symbol("10x", 10d, SymbolType.BONUS, null, "multiply_reward");

    // when
    final var reward =
        simpleGameEngine.getReward(100d, winCombinations, bonus);

    // then
    assertEquals(0d, reward);
  }

  @Test
  void shouldReturnMatrix() {
    // given
    final var simpleGameEngine =
        new SimpleGameEngine(gameConfig, new ProbabilityServiceImpl(gameConfig.getProbabilities()));

    // when
    final var matrix = simpleGameEngine.getMatrix();

    // then
    assertEquals(3, matrix.size());
    for (List<String> row : matrix) {
      assertEquals(3, row.size());
    }
  }
}