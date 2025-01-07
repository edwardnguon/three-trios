package cs3500.threetrios.model;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.strategy.Strategy;

/**
 * Represents a machine (AI) player in the Three Trios game.
 * A machine player automatically determines its moves using a specified {@link Strategy}.
 * This class implements the {@link Player} interface, allowing it to interact with the game model
 * in the same way as a human player.
 * Responsibilities of this class include:
 * - Managing the player's hand of cards.
 * - Using a strategy to determine moves during gameplay.
 * - Providing a copy of itself for game state replication.
 */
public class MachinePlayer implements Player {
  private final Color color;
  private List<Card> hand;
  private final Strategy strategy;

  /**
   * Constructs a machine player with a specified color and strategy.
   *
   * @param color The color representing this player (e.g., RED or BLUE).
   * @param strategy The strategy used by this machine player to determine its moves.
   * @throws IllegalArgumentException if either {@code color} or {@code strategy} is null.
   */
  public MachinePlayer(Color color, Strategy strategy) {
    if (color == null || strategy == null) {
      throw new IllegalArgumentException("Arguments can't be null");
    }
    this.color = color;
    this.strategy = strategy;
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
    Player player = new MachinePlayer(color, strategy);
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

  /**
   * Gets the machine player's set strategy.
   * @return the strategy
   */
  public Strategy getStrategy() {
    return strategy;
  }
}
