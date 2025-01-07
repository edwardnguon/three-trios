package cs3500.threetrios.view;

import java.awt.Color;

/**
 * Utility class for converting colors from the Three Trios game model to Java AWT colors.
 * This class provides a method to convert custom model colors (e.g., RED, BLUE)
 * to standard {@link java.awt.Color} values used for rendering in the game's view.
 */
public class ColorConverter {

  /**
   * Converts cs3500.threetrios.model.Color to java.awt.Color.
   *
   * @param modelColor The color from the model (RED or BLUE).
   * @return The corresponding java.awt.Color.
   */
  public static Color convertToAwtColor(cs3500.threetrios.model.Color modelColor) {
    if (modelColor == cs3500.threetrios.model.Color.RED) {
      return Color.RED;
    } else if (modelColor == cs3500.threetrios.model.Color.BLUE) {
      return Color.BLUE;
    }
    throw new IllegalArgumentException("Unsupported color: " + modelColor);
  }
}