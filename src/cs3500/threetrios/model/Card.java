package cs3500.threetrios.model;

import java.util.Objects;

/**
 * Represents a card in the game of Three Trios.
 * Each card has a unique name and four attack values corresponding to the four cardinal directions:
 * North, South, East, and West. The attack values range from 1 to 10 (with 'A' representing 10).
 * Cards are owned by players, and ownership can change during gameplay.
 */
public class Card {
  private final String name;
  private final int north;
  private final int south;
  private final int east;
  private final int west;
  private Color faceUpSide;

  /**
   * Constructor for the card class.
   *
   * @param name given card name (must not be null)
   * @param north north attack value (must be between 1 and 10)
   * @param south south attack value (must be between 1 and 10)
   * @param east east attack value (must be between 1 and 10)
   * @param west west attack value (must be between 1 and 10)
   * @throws IllegalArgumentException if any attack value is out of range or if the name is null
   */
  public Card(String name, int north, int south, int east, int west) {
    if (name == null) {
      throw new IllegalArgumentException("Name can not be null");
    }
    if (north < 1 || north > 10 || south < 1 || south > 10 ||
            east < 1 || east > 10 || west < 1 || west > 10) {
      throw new IllegalArgumentException("Attack values must be between 1 and 10.");
    }
    this.name = name;
    this.north = north;
    this.south = south;
    this.east = east;
    this.west = west;
  }

  /**
   * Gets the name of the card.
   *
   * @return the name of the card
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the owner of the card.
   *
   * @return the owner of the card
   * @throws IllegalStateException if the card does not currently have an owner
   */
  public Color getOwner() {
    if (faceUpSide == null) {
      throw new IllegalStateException("Card does not currently have an owner");
    }
    return faceUpSide;
  }

  /**
   * Sets the owner of the card.
   *
   * @param owner the new owner of the card (must not be null)
   * @throws IllegalArgumentException if the owner is null
   */
  public void setOwner(Color owner) {
    if (owner == null) {
      throw new IllegalArgumentException("Owner color can not be null");
    }
    this.faceUpSide = owner;
  }

  /**
   * Gets the attack value for a specific cardinal direction.
   *
   * @param cardinal the cardinal direction (NORTH, SOUTH, EAST, or WEST)
   * @return the attack value for that direction
   * @throws IllegalArgumentException if cardinal is null or invalid
   */
  public int getValue(CardinalDirection cardinal) {
    if (cardinal == null) {
      throw new IllegalArgumentException("Cardinal can not be null");
    }
    switch (cardinal) {
      case NORTH:
        return north;
      case SOUTH:
        return south;
      case EAST:
        return east;
      case WEST:
        return west;
      default:
        throw new IllegalArgumentException("Cardinal not valid");
    }
  }

  /**
   * Provides a textual representation of a card for display purposes.
   *
   * @return a string representation of this card in "name north south east west" format
   */
  @Override
  public String toString() {
    return name + " " + north + " " + south + " " + east + " " + west;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Card other = (Card) obj;
    return Objects.equals(name, other.name) && north == other.north && south == other.south &&
            east == other.east && west == other.west && faceUpSide == other.faceUpSide;
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, north, south, east, west, faceUpSide);
  }
}