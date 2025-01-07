package cs3500.threetrios.model;

import java.util.List;

/**
 * To represent a Player of the ThreeTriosGame.
 */
public interface Player {
  /**
   * Gets the player's color.
   * @return the player's color
   */
  Color getColor();

  /**
   * Sets the player's hand with a list of cards.
   * @param hand desired hand
   */
  void setHand(List<Card> hand);

  /**
   * Removes a card from player's hand after placing it on grid.
   * @param card card that needs to be removed
   */
  void removeCardFromHand(Card card);

  /**
   * Gets a copy of the player's hand. Changing it will have no effect
   * on the original hand.
   * @return the player's hand copy
   */
  List<Card> getHand();

  /**
   * Adds a given card to the player's hand.
   * @param card card
   */
  void addHand(Card card);

  /**
   * Makes a copy of the player object.
   * @return a copy of this player
   */
  Player copy();
}
