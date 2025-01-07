package cs3500.threetrios.provider.model;

import java.util.List;

/**
 * Interface representing the model for the Three Trios game.
 * The game of Three Trios is a two-player card game played on a customizable grid,
 * in which players place cards in turns, attempting to capture more cards than their opponent.
 * Each card has four attack values, representing the card's strength in each direction.
 * Players aim to control the most cards by the end of the game to win.
 */
public interface ReadOnlyTriosModel {


  /**
   * Returns a deep copy of the player whose turn it is to play.
   *
   * @return the player whose turn it currently is
   * @throws IllegalStateException if the game has not started yet or is already over
   */
  public Color getCurrentPlayer();

  /**
   * Checks if the game is over.
   *
   * @return true if the game is over, false otherwise
   * @throws IllegalStateException if game has not started yet
   */
  public Boolean isGameOver();

  /**
   * Returns a copy of the current game board.
   *
   * @return the current game board
   * @throws IllegalStateException if game has not been started
   */
  public ThreeTriosModel getBoard();

  /**
   * Returns the color of the player who won the game.
   * Returns null if the game was tied.
   *
   * @return the color of the player who is currently winning
   * @throws IllegalStateException if the game is in progress or hasn't started
   */
  public Color getWinner();

  /**
   * Returns a list of cards in the hand of the specified player.
   *
   * @param color the color representing the player (RED or BLUE)
   * @return a list of cards belonging to the specified player
   * @throws IllegalArgumentException if the color is invalid
   */
  public List<Card> getPlayerHand(Color color);

  /**
   * Returns the width of the game board.
   *
   * @return the number of columns on the game board
   */
  public int getBoardWidth();

  /**
   * Returns the height of the game board.
   *
   * @return the number of rows on the game board
   */
  public int getBoardHeight();

  /**
   * Returns the cell located at the specified row and column on the board.
   *
   * @param row the row index of the cell
   * @param col the column index of the cell
   * @return the cell at the specified position on the board
   * @throws IllegalArgumentException if the specified row or column is out of bounds
   */
  public Cell getCellAt(int row, int col);

  /**
   * Calculates the score for a given player based on the current board state.
   *
   * @param playerColor the color of the player (RED or BLUE) whose score is to be calculated
   * @return the number of cells currently controlled by the specified player
   * @throws IllegalArgumentException if the provided player color is invalid
   */
  public int getScore(Color playerColor);

  public void registerListener(ModelListener listener);


}
