package com.cyberspeed.scratchgame.service.engine;

/**
 * This interface defines the contract for a generic game engine.
 *
 * @param <T> The type of configuration used to initialize the game.
 * @param <K> The type representing winning combinations.
 * @param <M> The type representing the game matrix.
 * @param <B> The type representing bonuses in the game.
 */
public interface IGameEngine<T, K, M, B> {

  /**
   * Initializes the game engine with the provided configuration.
   *
   * @param config The configuration object used to initialize the game.
   */
  void initGame(T config);

  /**
   * Retrieves the game matrix.
   *
   * @return The game matrix.
   */
  M getMatrix();

  /**
   * Determines the winning combinations based on the provided game matrix.
   *
   * @param matrix The game matrix.
   * @return The winning combinations.
   */
  K getWinCombinations(M matrix);

  /**
   * Calculates the reward amount based on the bet amount, winning combinations,
   * and bonus in the game.
   *
   * @param betAmount      The amount bet by the player.
   * @param winCombinations The winning combinations in the game.
   * @param bonus          The bonus applied in the game.
   * @return The reward amount.
   */
  Double getReward(Double betAmount, K winCombinations, B bonus);

  /**
   * Retrieves the bonus based on the provided game matrix.
   *
   * @param matrix The game matrix.
   * @return The bonus applied in the game.
   */
  B getBonus(M matrix);
}
