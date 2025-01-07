import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import cs3500.threetrios.controller.MockController;
import cs3500.threetrios.model.Color;
import cs3500.threetrios.model.MockModel;
import cs3500.threetrios.model.MockPlayer;
import cs3500.threetrios.view.MockView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the GameController class using mock classes.
 */
public class GameControllerTest {

  private MockModel model;
  private MockView view;
  private MockPlayer redPlayer;
  private MockPlayer bluePlayer;
  private MockController redController;
  private MockController blueController;

  /**
   * To create new, unaltered mock versions as necessary to make the conditional setup for
   * the testing condition is clean.
   */
  @BeforeEach
  public void setup() {
    // Initialize mocks
    model = new MockModel();
    view = new MockView();
    view.reset();

    // Create players
    redPlayer = new MockPlayer(Color.RED);
    bluePlayer = new MockPlayer(Color.BLUE);

    // Create controllers for each player
    redController = new MockController(model, view, redPlayer);
    blueController = new MockController(model, view, bluePlayer);
  }

  //Test that a player can only take action when it is their turn.
  @Test
  public void testPlayerCanOnlyActOnTheirTurn() {
    // Simulate Red's turn
    model.setCurrentPlayer(redPlayer);

    // Red selects a card and plays it
    redController.onCardClick(0, "Red");
    assertEquals(List.of("Selected card 0"), redPlayer.getActions());

    redController.playCardToGrid(0, 1, 1);
    assertEquals(List.of("Selected card 0", "Played card 0 to (1,1)"), redPlayer.getActions());

    // Simulate Blue trying to act on Red's turn (should not work)
    model.setCurrentPlayer(redPlayer); // Still Red's turn
    blueController.onCardClick(1, "Blue");
    assertTrue(bluePlayer.getActions().isEmpty()); // Blue should not take any action

    blueController.playCardToGrid(1, 2, 2);
    assertTrue(bluePlayer.getActions().isEmpty()); // Blue should not take any action
  }

  //Test that a player cannot select cards from their opponent's hand.
  @Test
  public void testCannotSelectOpponentCards() {
    // Simulate Red's turn
    model.setCurrentPlayer(redPlayer);

    // Try selecting a card from Blue's hand
    redController.onCardClick(0, "Blue");
    assertTrue(redPlayer.getActions().isEmpty()); // Red should not be able to select Blue's cards

    // Try selecting a card from Red's hand (valid)
    redController.onCardClick(0, "Red");
    assertEquals(List.of("Selected card 0"), redPlayer.getActions());
  }

  //Test that a player must select a card before choosing a cell to play to.
  @Test
  public void testMustSelectCardBeforePlaying() {
    // Simulate Red's turn
    model.setCurrentPlayer(redPlayer);

    // Try playing without selecting a card
    redController.playCardToGrid(-1, 1, 1);
    assertTrue(redPlayer.getActions().isEmpty()); // No move should be made

    // Select a card and then play it
    redController.onCardClick(0, "Red");
    assertEquals(List.of("Selected card 0"), redPlayer.getActions());

    redController.playCardToGrid(0, 1, 1);
    assertEquals(List.of("Selected card 0", "Played card 0 to (1,1)"), redPlayer.getActions());
  }

  //Test that the controller updates its view even when the player is a machine.
  @Test
  public void testControllerUpdatesViewForMachinePlayers() {
    // Simulate Red's turn with machine player making moves
    model.setCurrentPlayer(redPlayer);

    // Simulate playing a card on the grid
    redController.playCardToGrid(0, 2, 2);

    // Ensure the view is updated after the move
    assertTrue(view.wasRendered());
  }

  //Test that invalid moves are handled gracefully and display error messages.
  @Test
  public void testHandlesInvalidMoves() {
    // Simulate an invalid move throwing an exception in the model
    model.setThrowExceptionOnPlay(true);

    redController.playCardToGrid(3, 3, 3);

    // Ensure an error message is displayed in the view
    assertEquals(List.of("Error: Invalid move"), view.getMessages());
  }

  //Test that the game ends with a winner and displays the winning score.

  @Test
  public void testGameEndsWithWinner() {
    // Simulate game over state in model
    model.setGameOver(true);

    redController.playCardToGrid(4, 4, 4);

    // Ensure winner message is displayed in the view
    assertEquals(List.of("Message: Red wins with score: X"), view.getMessages());
  }

  //Test that switching turns updates the current player and renders the view.
  @Test
  public void testTurnSwitchUpdatesView() {
    // Set current player to Red and simulate a turn switch to Blue
    model.setCurrentPlayer(redPlayer);

    model.setCurrentPlayer(bluePlayer); // Switch turn to Blue

    blueController.turnSwitched();

    // Ensure actions reflect the turn switch and view was updated
    assertTrue(view.wasRendered());
  }
}