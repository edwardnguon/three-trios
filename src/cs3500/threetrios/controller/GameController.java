package cs3500.threetrios.controller;

import javax.swing.SwingUtilities;

import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.ThreeTriosGameModel;
import cs3500.threetrios.view.Features;
import cs3500.threetrios.view.ThreeTriosGameView;

/**
 * Controller for handling user interactions in the Three Trios game.
 * Implements {@link Features} to respond to player actions such as selecting cards
 * and playing them on the grid.
 */
public class GameController implements Features {

  private final ThreeTriosGameModel model;
  private final ThreeTriosGameView view;
  private final Player player;
  private int selectedCardIndex = -1; // Track selected card index

  /**
   * Constructor for initializing the game's controller with a model and view.
   *
   * @param model The game's model.
   * @param view  The game's view.
   */
  public GameController(ThreeTriosGameModel model, ThreeTriosGameView view, Player player) {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Arguments cannot be null");
    }
    this.model = model;
    this.view = view;
    this.player = player;
    model.addListener(this);
    view.setTitle("Player " + player.getColor());
  }

  /**
   * Does the appropriate things needed when a card is clicked such as printing
   * the right message to the console and highlighting the card or showing
   * the user an error message.
   * @param cardIndex index of the clicked card in the hand
   * @param player player who clicked the card
   */
  public void onCardClick(int cardIndex, String player) {
    // Check if it's the player's turn
    if (model.currentPlayer() != this.player) {
      view.showErrorMessage("It's not your turn!");
      return;
    }

    // Validate the card index
    if (cardIndex < 0 || cardIndex >= model.getHand(this.player.getColor()).size()) {
      view.showErrorMessage("Invalid card selected!");
      return;
    }

    // Store selected card index and update the view
    this.selectedCardIndex = cardIndex;
    System.out.println(player + " selected card at index " + cardIndex);
  }


  @Override
  public void playCardToGrid(int cardIndex, int row, int col) {
    try {

      // Play selected card to grid
      model.playToGrid(cardIndex, row, col);

      // Update both players' hands after playing a card
      view.updateHandPanels(model.getHand(cs3500.threetrios.model.Color.RED),
              model.getHand(cs3500.threetrios.model.Color.BLUE));

      view.render();  // Repaint after playing a card

      // Reset selected card after playing
    } catch (IllegalArgumentException | IllegalStateException e) {
      System.out.println(e.getMessage());  // Handle any errors during play
    }
  }


  @Override
  public void turnSwitched() {
    view.render();
    if (model.currentPlayer() == this.player && !model.checkGameOver()) {
      view.showMessage("It's your turn!");
    }
  }

  @Override
  public void gameOver() {
    view.showMessage(model.announceWinner());
  }

  /**
   * To start the game by rendering the view with listening capabilities.
   */
  public void startGame() {
    view.addClickListener(this);

    SwingUtilities.invokeLater(() -> view.setVisible(true));
  }

  /**
   * To return the Player.
   */
  public Player getPlayer() {
    return this.player;
  }

  /**
   * To return the selectedCardIndex.
   */
  public int getSelectedCardIndex() {
    return this.selectedCardIndex;
  }
}