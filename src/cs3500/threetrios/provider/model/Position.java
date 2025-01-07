package cs3500.threetrios.provider.model;

import java.util.Objects;

/**
 * Represents a specific position on a TriosBoard.
 */
public class Position {

  public final int x;
  public final int y;

  /**
   * Creates a new position with specified coordinate.
   *
   * @param x the x coordinate
   * @param y the y coordinate
   * @throws IllegalArgumentException if x or y is negative
   */
  public Position(int x, int y) {
    if (x < 0 || y < 0) {
      throw new IllegalArgumentException("Position arguments cannot be negative.");
    }
    this.x = x;
    this.y = y;
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof Position) {
      Position otherPos = (Position)other;
      return otherPos.x == x && otherPos.y == y;
    }
    return false;
  }


  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }


}
