package cs3500.threetrios.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A mock implementation of the Player interface for testing purposes.
 * Simulates basic player behavior (human or machine) and tracks actions.
 */
public class MockPlayer implements Player {

  private final Color color; // The player's color (RED or BLUE)
  private final List<String> actions; // Tracks actions performed by the player
  private List<Card> hand; // The player's hand of cards
  private int selectedCardIndex = -1; // Tracks the index of the selected card

  /**
   * Constructs a mock player with the specified color.
   *
   * @param color The player's color (RED or BLUE).
   */
  public MockPlayer(Color color) {
    if (color == null) {
      throw new IllegalArgumentException("Color cannot be null.");
    }
    this.color = color;
    this.actions = new ArrayList<>();
    this.hand = new ArrayList<>();
  }

  /**
   * To represent which card is selected through messages.
   *
   * @param cardIndex card in hand index
   */
  public void selectCard(int cardIndex) {
    selectedCardIndex = cardIndex;
    actions.add("Selected card " + cardIndex);
  }

  /**
   * To represent playing a card to a location.
   *
   * @param row row of card play
   * @param col col of card play
   */
  public void playCardToGrid(int row, int col) {
    if (selectedCardIndex == -1) {
      throw new IllegalStateException("No card selected.");
    }
    actions.add("Played card " + selectedCardIndex + " to (" + row + ", " + col + ")");
    selectedCardIndex = -1; // Reset after playing
  }

  @Override
  public void setHand(List<Card> hand) {
    if (hand == null) {
      throw new IllegalArgumentException("Hand cannot be null.");
    }
    this.hand = new ArrayList<>(hand);
    actions.add("Set hand with " + hand.size() + " cards");
  }

  @Override
  public void removeCardFromHand(Card card) {
    if (card == null) {
      throw new IllegalArgumentException("Card cannot be null.");
    }
    if (!hand.remove(card)) {
      throw new IllegalStateException("Card not found in hand.");
    }
    actions.add("Removed card " + card.getName() + " from hand");
  }

  @Override
  public List<Card> getHand() {
    actions.add("Retrieved hand with " + hand.size() + " cards");
    return new ArrayList<>(hand);
  }

  @Override
  public void addHand(Card card) {
    if (card == null) {
      throw new IllegalArgumentException("Card cannot be null.");
    }
    hand.add(card);
    actions.add("Added card " + card.getName() + " to hand");
  }

  @Override
  public Player copy() {
    MockPlayer copy = new MockPlayer(this.color);
    for (Card card : this.hand) {
      Card copiedCard = new Card(card.getName(),
              card.getValue(CardinalDirection.NORTH),
              card.getValue(CardinalDirection.SOUTH),
              card.getValue(CardinalDirection.EAST),
              card.getValue(CardinalDirection.WEST));
      copiedCard.setOwner(this.color);
      copy.addHand(copiedCard);
    }
    actions.add("Created a copy of the player");
    return copy;
  }

  /**
   * Gets a list of all actions performed by this mock player.
   *
   * @return A list of actions as strings.
   */
  public List<String> getActions() {
    return new ArrayList<>(actions);
  }

  /**
   * Gets the color of this player.
   *
   * @return The player's color (RED or BLUE).
   */
  @Override
  public Color getColor() {
    return color;
  }

  /**
   * Resets the player's state for reuse in tests.
   */
  public void reset() {
    selectedCardIndex = -1;
    actions.clear();
    hand.clear();
    actions.add("Reset player state");
  }
}