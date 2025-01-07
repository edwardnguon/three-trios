package cs3500.threetrios.model;

/**
 * Cell interface that enables grids to contain
 * multiple types of cells.
 */
public interface Cell {

  /**
   * Places a given card in the cell.
   * Ensures that a card can only be placed in an empty cell. If the cell already contains a card,
   * an exception is thrown.
   * @param card input card
   * @throws IllegalArgumentException if card is null
   * @throws IllegalStateException if the cell is a hole
   * @throws IllegalStateException if there is already a card in the cell
   */
  void placeCard(Card card);

  /**
   * String representation of a cell. Empty card cells are "_", if they have a card in them, it
   * will be "R" or "B" depending on the card's owner. Holes will always be represented by " ".
   * @return string version of the cell
   */
  String toString();

  /**
   * Checks if the cell has a card or not.
   *
   * @return true if the cell has no card, false otherwise
   * @throws IllegalStateException if the cell is a hole
   */
  boolean isEmpty();

  /**
   * Gets the card in the cell.
   * @return the card in the card cell
   * @throws IllegalStateException if the cell is a hole
   */
  Card getCard();

  /**
   * Makes a copy of the cell.
   * @return a cell copy
   */
  Cell copy();
}
