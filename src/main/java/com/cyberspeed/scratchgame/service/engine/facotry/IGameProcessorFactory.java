package com.cyberspeed.scratchgame.service.engine.facotry;

import com.cyberspeed.scratchgame.service.processor.IGameProcessor;

public interface IGameProcessorFactory<T, K, M, B, R> {

  IGameProcessor<T, K, M, B, R> create();
}
