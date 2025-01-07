package cs3500.threetrios.adapter;

import cs3500.threetrios.controller.GameController;
import cs3500.threetrios.model.Player;
import cs3500.threetrios.provider.controller.ControllerListener;
import cs3500.threetrios.provider.controller.TriosController;
import cs3500.threetrios.provider.model.Color;
import cs3500.threetrios.provider.model.ModelListener;
import cs3500.threetrios.provider.model.Position;
import cs3500.threetrios.provider.view.ViewListener;

/**
 * An adapter class to integrate the new TriosController and ControllerListener interfaces
 * with the existing GameController.
 */
public class ControllerAdapter implements TriosController, ControllerListener,
        ModelListener, ViewListener {

  private final GameController existingController;

  /**
   * Constructs a ControllerAdapter with an existing GameController.
   *
   * @param existingController The existing GameController to adapt.
   */
  public ControllerAdapter(GameController existingController) {
    if (existingController == null) {
      throw new IllegalArgumentException("GameController cannot be null");
    }
    this.existingController = existingController;
  }

  /**
   * Constructor for the controller's adapter to take in adapter arguments
   * to construct a controller of the client's type.
   * @param model model adapter of the game
   * @param playerAdapter player adapter of the game
   * @param view view adapter of the game
   */
  public ControllerAdapter(ModelAdapter model, PlayerAdapter playerAdapter, ViewAdapter view) {
    Player player;

    if (playerAdapter.getColor() == Color.RED) {
      player = model.currentPlayer();
    } else {
      player = model.otherPlayer();
    }

    this.existingController = new GameController(model, view, player);
  }

  @Override
  public void onCardSelected(int cardIndex) {
    // Use player's color from the existing controller
    String playerColor = existingController.getPlayer().getColor().toString();
    // Delegate to existing controller's method for card selection
    existingController.onCardClick(cardIndex, playerColor);

  }

  @Override
  public void onCellSelected(Position position) {
    if (position == null) {
      throw new IllegalArgumentException("Position cannot be null");
    }
    // x and y variables are public in Position class
    int col = position.x;
    int row = position.y;

    // Validate that a card has been selected before proceeding
    int selectedCardIndex = existingController.getSelectedCardIndex();
    if (selectedCardIndex == -1) {
      System.out.println("No card selected! Cannot play to grid.");
      return;
    }

    // delegate to existing controller's method for playing a card
    existingController.playCardToGrid(selectedCardIndex, row, col);
  }

  @Override
  public void playerChanged() {
    // Notify the controller that the player has changed
    existingController.turnSwitched();
  }

  @Override
  public void gameOver() {
    existingController.turnSwitched();
  }

  @Override
  public void invalidMove(String errorMsg) {
    // Notify the controller of an invalid move. We don't have a method for this because this
    // behavior is handled internally within controller methods
    System.out.println("Invalid move: " + errorMsg);
  }
}