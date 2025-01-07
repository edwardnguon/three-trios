package cs3500.threetrios.model;

import java.io.File;
import java.io.IOException;

/**
 * Public interface to represent the three trios game model.
 */
public interface ThreeTriosModel extends ReadonlyThreeTriosModel {
  /**
   * Starts the game with the given options.
   * @param gridConfig configuration file with the grid to play the game on
   * @param cardsConfig configuration file with the cards to play the game
   * @param shuffle true if you want to shuffle, false otherwise
   * @throws IllegalStateException if the game has already started
   * @throws IllegalArgumentException if cards include null values or is null
   * @throws IllegalArgumentException if there aren't enough cards for the grid
   */
  void startGame(File gridConfig, File cardsConfig, boolean shuffle) throws IOException;

  /**
   * Takes a card form the current player's hand and plays it to a cell
   * in the grid based off a given row/col coordinate.
   * @param cardIdxInHand index of desired card in hand
   * @param row row value of cell in grid
   * @param col column value of cell in grid
   * @throws IllegalArgumentException if any of the indices are invalid
   * @throws IllegalStateException if it is not the placing phase
   */
  void playToGrid(int cardIdxInHand, int row, int col);

  /**
   * Switches the current player's turn.
   */
  void switchCurrentPlayer();

  /**
   * Changes one of the model's players accordingly to a given color and player.
   * @param color desired color of player to change
   * @param player new player
   */
  void setPlayer(Color color, Player player);
}
