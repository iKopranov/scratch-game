package com.cyberspeed.scratchgame;

import com.cyberspeed.scratchgame.service.engine.facotry.impl.GameProcessorFactoryImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

  public static void main(String[] args) {
    if (args.length != 2 || !args[0].startsWith("--config") || !args[1].startsWith("--betting-amount")) {
      log.info("Invalid arguments. Usage: java -jar <your-jar-file> --config <your-config-file> --betting-amount <amount number>");
      return;
    }
    var configFile = args[0].split("=")[1];
    var bettingAmount = args[1].split("=")[1]; 
    log.info("Start game with configFile {} and bettingAmount {}", configFile, bettingAmount);

    final var gameProcessor = new GameProcessorFactoryImpl(configFile).create();
    final var result = gameProcessor.bet(Double.valueOf(bettingAmount));
    log.info("End game with result: {}", result);
  }
}