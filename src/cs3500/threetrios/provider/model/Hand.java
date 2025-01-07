package cs3500.threetrios.provider.model;

import java.util.List;

/**
 * Represents a player in the Three Trios game.
 */
public interface Hand {

  /**
   * Returns a copy of the player's hand of cards.
   *
   * @return a list of cards in the player's hand
   */
  public List<Card> getHand();

  /**
   * Returns the player's color.
   *
   * @return the color of the player (RED or BLUE)
   */
  public Color getColor();

  /**
   * Returns a deep copy of the player.
   *
   * @return a deep copy of the player
   */
  public Hand getCopy();

  /**
   * Removes card from player's hand at specified index.
   *
   * @param index the index of the card to be removed from the hand
   * @return the card that was removed from the player's hand
   * @throws IllegalArgumentException if the specified index is out of range
   */
  public Card removeCardFromHand(int index);

  /**
   * Returns card in player's hand at specified index.
   *
   * @param index the index of the card to view in the hand
   * @return the card at the specified index in the player's hand
   * @throws IllegalArgumentException if the specified index is out of range
   */
  public Card seeCardInHand(int index);

}
