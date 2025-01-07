package cs3500.threetrios.model;

/**
 * Enum class that consists of the cardinal directions North, East, South, and West.
 * Used to get specific values for cards, important for the battle phases of the game.
 */
public enum CardinalDirection {
  NORTH,
  SOUTH,
  EAST,
  WEST;

  /**
   * Finds the opposite direction ex. calling this on NORTH
   * returns SOUTH, EAST returns WEST, etc.
   * @return the opposite direction
   */
  public CardinalDirection opposite() {
    switch (this) {
      case NORTH:
        return SOUTH;
      case SOUTH:
        return NORTH;
      case EAST:
        return WEST;
      case WEST:
        return EAST;
      default:
        throw new IllegalStateException("Invalid cardinal");
    }
  }
}
