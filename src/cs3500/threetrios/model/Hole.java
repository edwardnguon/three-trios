package cs3500.threetrios.model;

/**
 * Represents a hole where no card can be placed.
 */
public class Hole implements Cell {

  /**
   * Default constructor for the Hole class.
   * Initializes an empty hole where no card can be placed.
   */
  public Hole() {
    // No specific initialization required for a hole
  }

  @Override
  public void placeCard(Card card) {
    throw new IllegalStateException("Can't place a card onto a hole.");
  }

  @Override
  public String toString() {
    return "X";
  }

  @Override
  public Card getCard() {
    throw new IllegalStateException("A hole can not have a card");
  }

  @Override
  public boolean isEmpty() {
    return true;
  }

  @Override
  public Cell copy() {
    return new Hole();
  }
}
