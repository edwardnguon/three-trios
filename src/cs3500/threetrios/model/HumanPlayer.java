package cs3500.threetrios.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the game.
 */
public class HumanPlayer implements Player {
  private final Color color;
  private List<Card> hand;

  /**
   * Constructor for the Player class depending on a given color.
   *
   * @param color of player
   */
  public HumanPlayer(Color color) {
    if (color == null) {
      throw new IllegalArgumentException("Arguments can not be null");
    }
    this.color = color;
    this.hand = new ArrayList<>();
  }

  @Override
  public Color getColor() {
    return color;
  }

  @Override
  public void setHand(List<Card> hand) {
    if (hand == null) {
      throw new IllegalArgumentException("Hand can not be null");
    }
    this.hand = hand;
  }

  @Override
  public void removeCardFromHand(Card card) {
    if (!hand.remove(card)) {
      throw new IllegalStateException("No card in hand");
    }
    if (card == null) {
      throw new IllegalArgumentException("Card can not be null");
    }
    hand.remove(card);
  }

  @Override
  public List<Card> getHand() {
    return new ArrayList<>(hand);
  }

  @Override
  public void addHand(Card card) {
    hand.add(card);
  }

  @Override
  public Player copy() {
    Player player = new HumanPlayer(color);
    for (Card card : hand) {
      Card cardCopy = new Card(card.getName(),
              card.getValue(CardinalDirection.NORTH),
              card.getValue(CardinalDirection.SOUTH),
              card.getValue(CardinalDirection.EAST),
              card.getValue(CardinalDirection.WEST));
      cardCopy.setOwner(color);
      player.addHand(cardCopy);
    }
    return player;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HumanPlayer that = (HumanPlayer) o;
    return color.equals(that.color) && hand.equals(that.hand);
  }

  @Override
  public int hashCode() {
    int result = color.hashCode();
    result = 31 * result + hand.hashCode();
    return result;
  }
}