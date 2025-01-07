package cs3500.threetrios.provider.model;

/**
 * Represents a cell in the Three Trios game board.
 */
public interface Cell {

  /**
   * Checks whether the current cell is empty.
   *
   * @return true if a cell is empty, false otherwise
   */
  public Boolean isEmpty();

  /**
   * Checks whether the current cell is blocked.
   *
   * @return true if a cell is blocked, false otherwise
   */
  public Boolean isBlocked();

  /**
   * Places a card in the current cell.
   *
   * @param card the card to be placed in the cell
   * @throws IllegalStateException if the cell is blocked or already contains a card
   */
  public void placeCard(Card card);

  /**
   * Retrieves the card from the current cell.
   *
   * @return the card at the current cell
   * @throws IllegalArgumentException if the cell is empty or blocked
   */
  public Card getCard();


  /**
   * Checks if a cell contains a card.
   *
   * @return true if cell contains card, false otherwise
   */
  public Boolean hasCard();

  /**
   * Blocks the current cell.
   */
  public void block();


}
