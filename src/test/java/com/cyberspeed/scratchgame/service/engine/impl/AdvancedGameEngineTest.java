package com.cyberspeed.scratchgame.service.engine.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cyberspeed.scratchgame.model.GameConfig;
import com.cyberspeed.scratchgame.service.impl.ProbabilityServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import java.io.File;
import java.io.IOException;
import java.util.List;
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
    final var simpleGameEngine =
        new AdvancedGameEngine(gameConfig, new ProbabilityServiceImpl(gameConfig.getProbabilities()));

    // when
    final var matrix = simpleGameEngine.getMatrix();

    // then
    assertEquals(4, matrix.size());
    for (List<String> row : matrix) {
      assertEquals(4, row.size());
    }
  }

}