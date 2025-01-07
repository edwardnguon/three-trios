package cs3500.threetrios.strategy;

import java.io.File;
import java.io.IOException;

import cs3500.threetrios.model.ThreeTriosGameModel;

/**
 * Class for debugging strategy classes.
 */
public class StrategyDebug {
  /**
   * Main method to debug strategy classes.
   * @param args args
   * @throws IOException if files can't be read
   */
  public static void main(String[] args) throws IOException {
    ThreeTriosGameModel model = new ThreeTriosGameModel();
    model.startGame(new File("Configs/NoHolesGrid:9C0H"),
            new File("Configs/CardConfig:10:V2"), false);

    Strategy strategy = new CornerStrategy(model);
    Move foundMove = strategy.findMove();
  }
}
