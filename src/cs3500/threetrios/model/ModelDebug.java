package cs3500.threetrios.model;

import java.io.File;
import java.io.IOException;

/**
 * Public class for observing and debugging the model.
 */
public class ModelDebug {
  /**
   * Main method to debug model operations.
   * @param args args
   * @throws IOException if file can't be read properly
   */
  public static void main(String[] args) throws IOException {
    ThreeTriosGameModel model = new ThreeTriosGameModel();
    model.startGame(new File("Configs/NoHolesGrid:9C0H"),
            new File("Configs/CardConfig:10"), false);

    model.playToGrid(0, 0, 0);
    System.out.println(model.getGrid());
    model.playToGrid(0, 0, 1);
    System.out.println(model.getGrid());
    model.playToGrid(0, 0, 2);
    System.out.println(model.getGrid());
    model.playToGrid(0, 1, 2);
    System.out.println(model.getGrid());
    model.playToGrid(0, 1, 1);
    System.out.println(model.getGrid());
    model.playToGrid(0, 1, 0);
    System.out.println(model.getGrid());
  }
}
