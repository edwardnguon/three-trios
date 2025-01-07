package cs3500.threetrios.adapter;

import cs3500.threetrios.model.CardinalDirection;
import cs3500.threetrios.provider.model.Direction;

/**
 * Class to adapt our direction enum to the client's.
 */
public class DirectionAdapter {
  /**
   * Converts our cardinal direction enum to our client's direction enum.
   *
   * @param direction our direction type
   * @return the client's direction type
   */
  public static Direction toClientDirection(CardinalDirection direction) {
    switch (direction) {
      case NORTH:
        return Direction.NORTH;
      case SOUTH:
        return Direction.SOUTH;
      case EAST:
        return Direction.EAST;
      case WEST:
        return Direction.WEST;
      default:
        throw new IllegalStateException("Unexpected value: " + direction);
    }
  }

  /**
   * Converts our client's direction enum to our cardinal direction enum.
   *
   * @param direction the client's direction type
   * @return our cardinal direction type
   */
  public static CardinalDirection toOurDirection(Direction direction) {
    switch (direction) {
      case NORTH:
        return CardinalDirection.NORTH;
      case SOUTH:
        return CardinalDirection.SOUTH;
      case EAST:
        return CardinalDirection.EAST;
      case WEST:
        return CardinalDirection.WEST;
      default:
        throw new IllegalStateException("Unexpected value: " + direction);
    }
  }
}
