package cs3500.threetrios.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A mock implementation of the ThreeTriosGameModel for testing purposes.
 */
public class MockModel implements ThreeTriosModel {

  private final List<String> actions; // Tracks interactions with methods
  private boolean gameOver; // Tracks whether the game is over
  private Player currentPlayer; // Tracks the current player
  private Player otherPlayer; // Tracks the other player
  private boolean throwExceptionOnPlay; // Simulates exceptions for invalid moves

  /**
   * Constructor for the mock model, with actions list to represent actions taken.
   */
  public MockModel() {
    this.actions = new ArrayList<>();
    this.gameOver = false;
    this.throwExceptionOnPlay = false;

    // Initialize mock players
    this.currentPlayer = new MockPlayer(Color.RED);
    this.otherPlayer = new MockPlayer(Color.BLUE);
  }

  @Override
  public void startGame(File gridConfig, File cardsConfig, boolean shuffle) throws IOException {
    //don't need to actually "run the model"
  }

  @Override
  public void playToGrid(int cardIdxInHand, int row, int col) {
    if (gameOver) {
      throw new IllegalStateException("Game is already over.");
    }
    if (throwExceptionOnPlay) {
      throw new IllegalArgumentException("Invalid move");
    }
    actions.add("Played card " + cardIdxInHand + " to (" + row + ", " + col + ")");
  }

  @Override
  public void switchCurrentPlayer() {
    //testing conditions for player switching are either done manually or unnecessary
  }

  @Override
  public void setPlayer(Color color, Player player) {
    //not needed for controller testing
  }

  @Override
  public boolean checkGameOver() {
    return gameOver;
  }

  @Override
  public String announceWinner() {
    return "Red wins with score: X";
  }

  @Override
  public Player currentPlayer() {
    return currentPlayer;
  }

  @Override
  public Player otherPlayer() {
    return otherPlayer;
  }

  @Override
  public Grid getGrid() {
    return null;
  }

  @Override
  public int getGridRows() {
    return 0;
  }

  @Override
  public int getGridCols() {
    return 0;
  }

  @Override
  public Card cellContents(int row, int col) {
    return null;
  }

  @Override
  public Color cellOwner(int row, int col) {
    return null;
  }

  @Override
  public int playerScore(Color color) {
    return 0;
  }

  @Override
  public boolean isHole(int row, int col) {
    return false;
  }

  @Override
  public boolean isEmpty(int row, int col) {
    return false;
  }

  @Override
  public Color getOwner(int row, int col) {
    return null;
  }

  @Override
  public ThreeTriosGameModel copy() {
    return null;
  }

  @Override
  public GameState getGameState() {
    return null;
  }

  @Override
  public List<Card> getHand(Color color) {
    List<Card> hand = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      hand.add(new Card(color.toString(), i, i + 1, i + 2, i + 3));
    }
    return hand;
  }

  public void setCurrentPlayer(Player player) {
    this.currentPlayer = player;
  }

  public void setOtherPlayer(Player player) {
    this.otherPlayer = player;
  }

  public void setThrowExceptionOnPlay(boolean throwException) {
    this.throwExceptionOnPlay = throwException;
  }

  public void setGameOver(boolean gameOver) {
    this.gameOver = gameOver;
  }

  public List<String> getActions() {
    return actions;
  }
}