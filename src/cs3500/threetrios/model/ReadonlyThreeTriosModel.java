package cs3500.threetrios.model;

import java.util.List;

/**
 * Model interface used by the view so that the view can see the game's
 * state but can not modify it. Extended by the TreeTriosModel interface.
 */
public interface ReadonlyThreeTriosModel {
  /**
   * The game is over when all card cells in the grid are filled.
   *
   * @return true if the game is over, false if it isn't
   * @throws IllegalStateException if game hasn't started
   */
  boolean checkGameOver();

  /**
   * Announces the winner of the game.
   *
   * @return a message stating the game winner/if it's a tie
   * @throws IllegalStateException if the game is not over
   */
  String announceWinner();

  /**
   * Returns a copy of the hand in the game. This means modifying the returned list
   * or the cards in the list has no effect on the game.
   *
   * @param player color of the player whose hand you want to see
   * @return a new list containing the cards in the player's hand in the same order
   *         as in the current state of the game.
   */
  List<Card> getHand(Color player);

  /**
   * Returns the player whose turn it is.
   *
   * @return the current turn player
   * @throws IllegalStateException if the game hasn't started
   */
  Player currentPlayer();

  /**
   * Returns the player whose turn it isn't. Opposite of the above method.
   * @return the non-current turn player
   * @throws IllegalStateException if the game hasn't started
   */
  Player otherPlayer();

  /**
   * Returns a copy of the grid.
   *
   * @return a copy of the grid so that modifying it won't change the actual grid.
   */
  Grid getGrid();

  /**
   * Checks the size of the grid.
   *
   * @return number of card cells in the grid
   */
  int getGridRows();

  /**
   * Checks the size of the grid.
   *
   * @return number of card cells in the grid
   * @throws IllegalStateException if the game is not in progress
   */
  int getGridCols();

  /**
   * Checks the contents of the cell at a given row and column.
   *
   * @param row row of grid
   * @param col column of grid
   * @return the card in the cell at the given coordinates
   * @throws IllegalStateException    if the game is not in progress
   * @throws IllegalStateException    if there is no card in the cell
   * @throws IllegalArgumentException if the given coordinates are invalid
   */
  Card cellContents(int row, int col);

  /**
   * Checks the owner of the card in a cell at a given row and column.
   *
   * @param row row of grid
   * @param col column of grid
   * @return the owner of the card in the cell at the given coordinates
   * @throws IllegalStateException    if the game is not in progress
   * @throws IllegalStateException    if there is no card in the cell
   * @throws IllegalArgumentException if the given coordinates are invalid
   */
  Color cellOwner(int row, int col);

  /**
   * Checks a given player's score.
   *
   * @param color desired color of the player
   * @return score of the corresponding player
   * @throws IllegalStateException if the game has not started
   */
  int playerScore(Color color);

  /**
   * Checks if the cell at the specified row and column is a hole.
   * A hole is a type of cell where no card can be placed.
   *
   * @param row The row index of the cell to check.
   * @param col The column index of the cell to check.
   * @return true if the cell is a hole, false otherwise.
   * @throws IndexOutOfBoundsException if the specified coordinates are out of bounds.
   */
  boolean isHole(int row, int col);

  /**
   * Checks if the model's cell has a card or not.
   *
   * @return true if the cell has no card, false otherwise
   * @throws IllegalStateException if the cell is a hole
   */
  boolean isEmpty(int row, int col);

  /**
   * Gets the owner of a model's card.
   *
   * @return the owner of the card
   * @throws IllegalStateException if the card does not currently have an owner
   */
  Color getOwner(int row, int col);

  /**
   * Makes a copy of the game's model's current state.
   *
   * @return model copy
   */
  ThreeTriosGameModel copy();

  /**
   * Gets the current state of the game.
   * @return the model's gameState
   */
  GameState getGameState();
}
