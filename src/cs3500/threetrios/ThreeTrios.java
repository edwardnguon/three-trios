package cs3500.threetrios;

import java.io.File;
import java.io.IOException;

import cs3500.threetrios.controller.GameController;
import cs3500.threetrios.model.ThreeTriosGameModel;
import cs3500.threetrios.view.Features;
import cs3500.threetrios.view.ThreeTriosGameView;

/**
 * The main class for running the Three Trios game.
 * This class sets up and starts the game by initializing the model, view, and controller,
 * and handles user interactions such as selecting cards and playing them on the grid.
 */
public final class ThreeTrios {

  /**
   * The main method for starting the Three Trios game.
   * This method reads configuration files for setting up the grid and cards, initializes
   * the game model, and sets up the view. It also adds listeners for user interactions such as
   * clicking on grid cells and playing cards through {@link Features}.
   *
   * @param args Command-line arguments (not used).
   */
  public static void main(String[] args) {
    try {
      File gridConfigFile = new File("Configs/ConnectingGrid:9C16H");
      File cardConfigFile = new File("Configs/CardConfig:10");

      // Initialize game model
      ThreeTriosGameModel model = new ThreeTriosGameModel();
      model.startGame(gridConfigFile, cardConfigFile, false);

      // Initialize game view
      ThreeTriosGameView viewRed = new ThreeTriosGameView(model);
      ThreeTriosGameView viewBlue = new ThreeTriosGameView(model);

      // Create GameController which implements ClickListener
      GameController controllerRed = new GameController(model, viewRed, model.currentPlayer());
      GameController controllerBlue = new GameController(model, viewBlue, model.otherPlayer());

      controllerRed.startGame();
      controllerBlue.startGame();

    } catch (IOException e) {
      System.out.println("Error loading configuration files: " + e.getMessage());
    }
  }
}