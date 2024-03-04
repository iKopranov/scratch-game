package com.cyberspeed.scratchgame.service.facotry.impl;

import com.cyberspeed.scratchgame.model.GameConfig;
import com.cyberspeed.scratchgame.model.GameResult;
import com.cyberspeed.scratchgame.model.Symbol;
import com.cyberspeed.scratchgame.model.WinCombination;
import com.cyberspeed.scratchgame.service.engine.IGameEngine;
import com.cyberspeed.scratchgame.service.engine.impl.AdvancedGameEngine;
import com.cyberspeed.scratchgame.service.engine.impl.DummyGameEngine;
import com.cyberspeed.scratchgame.service.engine.impl.SimpleGameEngine;
import com.cyberspeed.scratchgame.service.facotry.IGameProcessorFactory;
import com.cyberspeed.scratchgame.service.impl.ProbabilityServiceImpl;
import com.cyberspeed.scratchgame.service.impl.SameSymbolWinCombinationsAdder;
import com.cyberspeed.scratchgame.service.impl.SameSymbolsHorizontallyWinCombinationsAdder;
import com.cyberspeed.scratchgame.service.processor.IGameProcessor;
import com.cyberspeed.scratchgame.service.processor.impl.GameProcessorImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GameProcessorFactoryImpl implements IGameProcessorFactory<GameConfig, Map<String, List<WinCombination>>, List<List<String>>, Symbol, GameResult> {

  private static final String SOURCE_FILE_PATH = System.getProperty("user.dir");
  private final GameConfig gameConfig;
  
  public GameProcessorFactoryImpl(String configFile) {
    var objectMapper = new ObjectMapper();
    objectMapper.setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy());
    var file = new File(SOURCE_FILE_PATH + configFile);
    try {
      this.gameConfig = objectMapper.readValue(file, GameConfig.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  @Override
  public IGameProcessor<GameConfig, Map<String, List<WinCombination>>, List<List<String>>, Symbol, GameResult> create(
      String engineName
  ) {
    final var sameSymbolWinCombinationsAdder = new SameSymbolWinCombinationsAdder(gameConfig);
    final var horizontallyWinCombinationsAdder = new SameSymbolsHorizontallyWinCombinationsAdder(gameConfig);
    return new GameProcessorImpl(
        getGameEngine(sameSymbolWinCombinationsAdder, horizontallyWinCombinationsAdder, engineName)
    );
  }
  
  private IGameEngine<GameConfig, Map<String, List<WinCombination>>, List<List<String>>, Symbol> getGameEngine(
      SameSymbolWinCombinationsAdder sameSymbolWinCombinationsAdder,
      SameSymbolsHorizontallyWinCombinationsAdder horizontallyWinCombinationsAdder,
      String engineName
  ) {
    
    if (engineName.equals("simple")) {
      log.info("Simple game engine was chosen");
      return new SimpleGameEngine(
          gameConfig, new ProbabilityServiceImpl(gameConfig.getProbabilities()),
          Map.of(
              sameSymbolWinCombinationsAdder.getName(), sameSymbolWinCombinationsAdder,
              horizontallyWinCombinationsAdder.getName(), horizontallyWinCombinationsAdder
          )
      );
    } else if (engineName.equals("advanced")) {
      log.info("Advanced game engine was chosen");
      
      return new AdvancedGameEngine(
          gameConfig, new ProbabilityServiceImpl(gameConfig.getProbabilities()),
          Map.of(
              sameSymbolWinCombinationsAdder.getName(), sameSymbolWinCombinationsAdder,
              horizontallyWinCombinationsAdder.getName(), horizontallyWinCombinationsAdder
          )
      );
    } else {
      log.info("No one game engine was chosen return dummy engine");
      return new DummyGameEngine();
    }
  }
}