package cs3500.threetrios.adapter;

import cs3500.threetrios.provider.model.Powers;

/**
 * Class to convert our card's attack values to the client's Powers enum.
 */
public class PowerAdapter {
  /**
   * Converts int to Powers.
   *
   * @param value our card's int value
   * @return client's Powers enum
   */
  public static Powers intToPowers(int value) {
    switch (value) {
      case 1:
        return Powers.ONE;
      case 2:
        return Powers.TWO;
      case 3:
        return Powers.THREE;
      case 4:
        return Powers.FOUR;
      case 5:
        return Powers.FIVE;
      case 6:
        return Powers.SIX;
      case 7:
        return Powers.SEVEN;
      case 8:
        return Powers.EIGHT;
      case 9:
        return Powers.NINE;
      case 10:
        return Powers.TEN;
      default:
        throw new IllegalArgumentException("Invalid value");
    }
  }

  /**
   * Converts Powers to int.
   *
   * @param power our card's int value
   * @return client's Powers enum
   */
  public static int powersToInt(Powers power) {
    switch (power) {
      case ONE:
        return 1;
      case TWO:
        return 2;
      case THREE:
        return 3;
      case FOUR:
        return 4;
      case FIVE:
        return 5;
      case SIX:
        return 6;
      case SEVEN:
        return 7;
      case EIGHT:
        return 8;
      case NINE:
        return 9;
      case TEN:
        return 10;
      default:
        throw new IllegalArgumentException("Invalid value");
    }
  }
}
