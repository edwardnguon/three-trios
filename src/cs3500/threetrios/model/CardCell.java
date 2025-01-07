package cs3500.threetrios.model;

import java.util.Objects;

/**
 * Represents a card cell in the game of Three Trios.
 * A card cell is a type of cell that can hold a card. It starts empty and can later contain a card.
 * This class implements the Cell interface, which allows for placing cards in cells.
 */
public class CardCell implements Cell {
  private Card card;

  /**
   * Default constructor for an empty CardCell.
   * Initializes the cell without a card.
   */
  public CardCell() {
    this.card = null; // The cell starts empty
  }

  /**
   * Constructor for a CardCell that starts with a specific card.
   *
   * @param card the card to place in this cell initially (must not be null)
   * @throws IllegalArgumentException if the provided card is null
   */
  public CardCell(Card card) {
    if (card == null) {
      throw new IllegalArgumentException("Card cannot be null");
    }
    this.card = card;
  }

  public boolean isEmpty() {
    return card == null;
  }

  @Override
  public void placeCard(Card card) {
    if (card == null) {
      throw new IllegalArgumentException("Card cannot be null");
    }
    if (this.card != null) {
      throw new IllegalStateException("There is already a card in this cell");
    }
    this.card = card;
  }

  @Override
  public Card getCard() {
    if (card == null) {
      throw new IllegalStateException("There is no card in this cell");
    }
    return card;
  }

  @Override
  public String toString() {
    if (card == null) {
      return "_";
    } else {
      return card.getOwner().equals(Color.RED) ? "R" : "B";
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    CardCell other = (CardCell) obj;
    return Objects.equals(card, other.card);
  }

  /**
   * Generates a hash code for this CardCell based on its fields.
   *
   * @return a hash code for this CardCell
   */
  @Override
  public int hashCode() {
    return Objects.hash(card);
  }

  @Override
  public Cell copy() {
    if (card == null) {
      return new CardCell();
    }
    return new CardCell(card);
  }
}