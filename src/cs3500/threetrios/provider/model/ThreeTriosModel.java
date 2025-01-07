package cs3500.threetrios.provider.model;

import java.util.List;

/**
 * Interface representing the model for the Three Trios game.
 * The game of Three Trios is a two-player card game played on a customizable grid,
 * in which players place cards in turns, attempting to capture more cards than their opponent.
 * Each card has four attack values, representing the card's strength in each direction.
 * Players aim to control the most cards by the end of the game to win.
 */
public interface ThreeTriosModel extends ReadOnlyTriosModel {

  /**
   * Initializes and starts the Three Trios game.
   * The game configuration is loaded from specified files for the grid and cards.
   *
   * @param shuffle if true, cards are shuffled
   * @param cardConfigPath the file path to the card configuration
   * @param gridConfigPath the file path to the grid configuration
   * @throws IllegalStateException if the game has already started or is already over
   * @throws IllegalArgumentException if file path is invalid or  content is improperly formatted
   */
  public void startGame(Boolean shuffle, String cardConfigPath, String gridConfigPath);

  /**
   * Starts the game from a predetermined state using a provided board and card lists.
   *
   *
   * @param redList A list of cards belonging to red player.
   * @param blueList A list of cards belonging to blue player.
   * @param board The board to play on.
   * @throws IllegalArgumentException if board is null
   * @throws IllegalArgumentException if board does not have an odd number of open cells
   * @throws IllegalArgumentException if either list is null
   * @throws IllegalArgumentException if total number of cards in game is not sufficient
   * @throws IllegalStateException if the game is already started
   */
  public void startGameInProgress(List<Card> redList, List<Card> blueList,
                                    ThreeTriosModel board);

  /**
   * Places a card in the specified cell (i, j) on the grid using a 0-based index system.
   *
   * @param rowIndex the row index of the cell
   * @param colIndex the column index of the cell
   * @param indexInHand the index of card in hand to place onto grid
   * @throws IllegalArgumentException if row or column index is invalid
   * @throws IllegalStateException if the game has not started yet or is already over
   * @throws IllegalArgumentException if hand index is invalid
   */
  public void playToCell(int rowIndex, int colIndex, int indexInHand);



}
