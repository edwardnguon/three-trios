package cs3500.threetrios.adapter;

import cs3500.threetrios.provider.model.Color;

/**
 * Class to adapt our color enum to the client's.
 */
public class ColorAdapter {
  /**
   * Takes in our color enum value and converts it to the corresponding
   * client's color type.
   *
   * @param color our color enum type
   * @return the client's color enum type
   */
  public static Color toClientColor(cs3500.threetrios.model.Color color) {
    switch (color) {
      case RED:
        return Color.RED;
      case BLUE:
        return Color.BLUE;
      default:
        throw new IllegalArgumentException("Invalid color");
    }
  }

  /**
   * Takes in the client's color enum value and converts it to the corresponding
   * color enum type for us.
   *
   * @param color client's color type
   * @return our color type
   */
  public static cs3500.threetrios.model.Color toOurColor(Color color) {
    switch (color) {
      case RED:
        return cs3500.threetrios.model.Color.RED;
      case BLUE:
        return cs3500.threetrios.model.Color.BLUE;
      default:
        throw new IllegalArgumentException("Invalid color");
    }
  }
}
