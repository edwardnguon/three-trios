package cs3500.threetrios.strategy;

/**
 * Interface representing a strategy for determining the best move in the Three Trios game.
 * Implementations of this interface will define how to find the optimal
 * move based on the current game state.
 * This interface is useful for implementing different strategies, such as AI-based decision-making.
 */
public interface Strategy {

  /**
   * Method for finding the best move for a strategy.
   * @return a Move object with the information for the best move
   */
  Move findMove();
}
