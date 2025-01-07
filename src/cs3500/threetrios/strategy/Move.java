package cs3500.threetrios.strategy;

import java.util.Objects;

/**
 * Move class that contains the information needed to make move in
 * the three trios game, one int to represent the card in hand,
 * and two ints to represent the coordinates on the grid.
 */
public class Move {
  private final int cardIdxInHand;
  private final int row;
  private final int col;

  /**
   * Constructor for a Move object with the needed given parameters.
   *
   * @param cardIdxInHand index of the card in the player's hand
   * @param row           index of row in the grid
   * @param col           index of column in the grid
   */
  public Move(int cardIdxInHand, int row, int col) {
    this.cardIdxInHand = cardIdxInHand;
    this.row = row;
    this.col = col;
  }

  /**
   * Getter method for the card index in hand.
   *
   * @return the card index in hand of this move
   */
  public int getCardIdxInHand() {
    return cardIdxInHand;
  }

  /**
   * Getter method for the row index.
   *
   * @return the row index of this move
   */
  public int getRow() {
    return row;
  }

  /**
   * Getter method for the column index.
   *
   * @return the column index of this move
   */
  public int getCol() {
    return col;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Move move = (Move) o;
    return cardIdxInHand == move.cardIdxInHand && getRow() == move.row && getCol() == move.col;
  }

  @Override
  public int hashCode() {
    return Objects.hash(cardIdxInHand, row, col);
  }

  @Override
  public String toString() {
    return "Move{" +
            "cardIndex=" + cardIdxInHand +
            ", rowIndex=" + row +
            ", colIndex=" + col +
            '}';
  }
}
