package com.cyberspeed.scratchgame.service.engine.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cyberspeed.scratchgame.model.GameConfig;
import com.cyberspeed.scratchgame.model.WinCombination;
import com.cyberspeed.scratchgame.service.impl.ProbabilityServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class AdvancedGameEngineTest {

  private static final String SOURCE_FILE_PATH =
      System.getProperty("user.dir") + "/src/test/resources/advanced-config.json";
  private static final ObjectMapper objectMapper = new ObjectMapper();
  private static GameConfig gameConfig;

  @BeforeAll
  static void init() throws IOException {
    objectMapper.setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy());
    var file = new File(SOURCE_FILE_PATH);
    gameConfig = objectMapper.readValue(file, GameConfig.class);
  }

  @Test
  void shouldReturnMatrix() {
    // given
    final var gameEngine =
        new AdvancedGameEngine(gameConfig,
            new ProbabilityServiceImpl(gameConfig.getProbabilities()));

    // when
    final var matrix = gameEngine.getMatrix();

    // then
    assertEquals(4, matrix.size());
    for (List<String> row : matrix) {
      assertEquals(4, row.size());
    }
  }

  @Test
  void shouldReturnSameSymbolWinCombinations() {
    // given
    final var gameEngine =
        new AdvancedGameEngine(gameConfig,
            new ProbabilityServiceImpl(gameConfig.getProbabilities()));
    var matrix = List.of(
        List.of("A", "A", "B", "F"),
        List.of("A", "+1000", "B", "C"),
        List.of("A", "A", "B", "D")
    );

    // when
    final var winCombinations = gameEngine.getWinCombinations(matrix);

    // then
    var expectedWinCombinations = Map.of("A",
        List.of(new WinCombination("same_symbol_5_times", 2d, "same_symbols", 5L, "same_symbols",
            null)),
        "B",
        List.of(
            new WinCombination("same_symbol_3_times", 1d, "same_symbols", 3L, "same_symbols", null))
    );
    assertEquals(expectedWinCombinations, winCombinations);
  }

  @Test
  void shouldReturnHorizontallyLinearWinCombinations() {
    // given
    final var gameEngine =
        new AdvancedGameEngine(gameConfig,
            new ProbabilityServiceImpl(gameConfig.getProbabilities()));
    var matrix = List.of(
        List.of("F", "A", "B", "F"),
        List.of("A", "+1000", "B", "C"),
        List.of("D", "D", "D", "D")
    );

    // when
    final var winCombinations = gameEngine.getWinCombinations(matrix);

    // then
    var expectedWinCombinations = Map.of("D",
        List.of(new WinCombination("same_symbol_4_times", 1.5, "same_symbols", 4L, "same_symbols",
                null),
            new WinCombination("same_symbols_horizontally", 2d, "linear_symbols", null,
                "horizontally_linear_symbols", List.of(
                List.of("0:0", "0:1", "0:2"),
                List.of("1:0", "1:1", "1:1"),
                List.of("2:0", "2:1", "2:2")
            ))
        ));
    
    assertEquals(expectedWinCombinations, winCombinations);
  }

}