package cs3500.threetrios.controller;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.model.MockModel;
import cs3500.threetrios.model.Player;
import cs3500.threetrios.view.Features;
import cs3500.threetrios.view.MockView;

/**
 * A mock implementation of the GameController for testing purposes.
 * Simulates basic controller behavior and tracks interactions with its methods.
 */
public class MockController implements Features {

  private final MockModel model; // Mock model
  private final MockView view; // Mock view
  private final Player player; // The player this controller manages
  private final List<String> actions; // Tracks actions performed by the controller
  private int selectedCardIndex = -1; // Tracks the selected card index

  /**
   * Constructs a mock controller for testing purposes.
   *
   * @param model  The mock model to simulate game state.
   * @param view   The mock view to simulate user interactions.
   * @param player The player this controller manages.
   */
  public MockController(MockModel model, MockView view, Player player) {
    if (model == null || view == null || player == null) {
      throw new IllegalArgumentException("Arguments cannot be null");
    }
    this.model = model;
    this.view = view;
    this.player = player;
    this.actions = new ArrayList<>();
  }

  @Override
  public void onCardClick(int cardIndex, String playerColor) {
    if (!player.getColor().toString().equalsIgnoreCase(playerColor)) {
      actions.add("Attempted to select card " + cardIndex + " from opponent's hand.");
      view.showErrorMessage("You cannot select cards from your opponent's hand!");
      return;
    }
    if (model.currentPlayer() != this.player) {
      actions.add("Attempted to select card " + cardIndex + " out of turn.");
      view.showErrorMessage("It's not your turn!");
      return;
    }
    selectedCardIndex = cardIndex;
    actions.add("Selected card " + cardIndex);
    System.out.println(playerColor + " selected card at index " + cardIndex);
  }

  @Override
  public void playCardToGrid(int cardIndex, int row, int col) {
    if (selectedCardIndex == -1) {
      actions.add("Attempted to play a card without selecting one.");
      view.showErrorMessage("You must select a card before playing!");
      return;
    }
    try {
      model.playToGrid(cardIndex, row, col);
      actions.add("Played card " + cardIndex + " to (" + row + ", " + col + ")");
      selectedCardIndex = -1; // Reset after playing
      view.render(); // Update the view
      if (model.checkGameOver()) {
        view.showMessage(model.announceWinner());
        actions.add("Game over: " + model.announceWinner());
      }
    } catch (IllegalArgumentException | IllegalStateException e) {
      actions.add("Error: " + e.getMessage());
      view.showErrorMessage(e.getMessage());
    }
  }

  @Override
  public void turnSwitched() {
    actions.add("Turn switched to " + model.currentPlayer().getColor());
    view.render();
    if (model.currentPlayer() == this.player && !model.checkGameOver()) {
      view.showMessage("It's your turn!");
    }
  }

  @Override
  public void gameOver() {
    String winnerMessage = model.announceWinner();
    actions.add("Game over: " + winnerMessage);
    view.showMessage(winnerMessage);
  }

  /**
   * Gets a list of all actions performed by this mock controller.
   *
   * @return A list of actions as strings.
   */
  public List<String> getActions() {
    return new ArrayList<>(actions);
  }

  /**
   * Resets the controller's state for reuse in tests.
   */
  public void reset() {
    selectedCardIndex = -1;
    actions.clear();
  }
}