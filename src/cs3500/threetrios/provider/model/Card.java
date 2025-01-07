package cs3500.threetrios.provider.model;

/**
 * Represents a card in the Three Trios game.
 */
public interface Card {

  /**
   * Returns the attack power of the card in a specified direction.
   *
   * @param direction the direction (North, South, East, or West) for which to get the attack power
   * @return the attack power of the card in the specified direction
   */
  public Powers getAttackPower(Direction direction);

  /**
   * Returns the name of the card.
   *
   * @return the name of the card
   */
  public String getName();

  /**
   * Returns the color associated with the hand.
   *
   * @return the color associated with the hand
   */
  public Color getOwner();


  /**
   * Sets a new owner for the card.
   *
   * @param color the color that will own the card
   * @throws IllegalArgumentException if player is null
   */
  public void setOwner(Color color);

  /**
   * Returns a deep copy of the card.
   *
   * @return a deep copy of the card.
   */
  public Card getCopy();

}
