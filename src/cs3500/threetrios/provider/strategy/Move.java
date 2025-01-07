package cs3500.threetrios.provider.strategy;

import cs3500.threetrios.provider.model.Position;

import java.util.Objects;

/**
 * Represents a move in the game, which includes a card and a position.
 */
public class Move {
  public final Position position;
  public final int cardIndex;

  public Move(Position position, int card) {
    this.position = position;
    this.cardIndex = card;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Move) {
      Move other = (Move)obj;
      return (this.position.equals(other.position))
          && this.cardIndex == other.cardIndex;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(position, cardIndex);
  }

}
