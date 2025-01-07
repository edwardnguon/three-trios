package cs3500.threetrios.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import cs3500.threetrios.strategy.Move;
import cs3500.threetrios.strategy.Strategy;
import cs3500.threetrios.view.Features;

//Class Invariant: # of Cards on board must be odd (in order to decide a current winner)

/**
 * Public class that holds the implementation of the three trios game model
 * and implements the ThreeTriosModel interface.
 */
public class ThreeTriosGameModel implements ThreeTriosModel {
  private Grid grid;
  private List<Card> cards;
  private Player currentPlayer;
  private Player playerRed;
  private Player playerBlue;
  private GameState gameState;
  private Random shuffler;
  private final List<Features> listeners = new ArrayList<>();

  /**
   * Public constructor for the games model based off of a given grid,
   * initializes starting values for the rest of the model's data.
   */
  public ThreeTriosGameModel() {
    this.cards = new ArrayList<>();
    this.playerRed = new HumanPlayer(Color.RED);
    this.playerBlue = new HumanPlayer(Color.BLUE);
    this.currentPlayer = playerRed;
    this.gameState = GameState.GAME_NOT_STARTED;
  }

  /**
   * Public constructor for the game's model based on a given grid and random shuffler.
   * Initializes starting values for the rest of the model's data, such as players and game state.
   *
   * @param random A Random object used for shuffling cards.
   */
  public ThreeTriosGameModel(Random random) {
    this.cards = new ArrayList<>();
    this.playerRed = new HumanPlayer(Color.RED);
    this.playerBlue = new HumanPlayer(Color.BLUE);
    this.currentPlayer = playerRed;
    this.gameState = GameState.GAME_NOT_STARTED;
    this.shuffler = random;
  }

  /**
   * Private constructor for the game model used to make "mock" copies of the model.
   */
  private ThreeTriosGameModel(Grid grid, Player playerRed, Player playerBlue,
                              Player currentPlayer, GameState gameState) {
    this.grid = grid;
    this.playerRed = playerRed;
    this.playerBlue = playerBlue;
    this.currentPlayer = currentPlayer;
    this.gameState = gameState;
  }

  @Override
  public void startGame(File gridConfig, File cardsConfig, boolean shuffle) throws IOException {
    if (cards == null) {
      throw new IllegalArgumentException("Cards can not be null");
    }
    for (Card card : cards) {
      if (card == null) {
        throw new IllegalArgumentException("Cards contain null card(s)");
      }
    }
    if (gameState != GameState.GAME_NOT_STARTED) {
      throw new IllegalStateException("Game has already been started");
    }

    try {
      // read grid from configfile
      this.grid = ConfigReader.readGrid(gridConfig.getPath());
      this.cards = ConfigReader.readCards(cardsConfig.getPath());

      if (shuffle) {
        shuffle();
      }

      distributeCards();
      this.gameState = GameState.PLACING_PHASE;
      currentPlayer = playerRed;
    } catch (IOException e) {
      throw new IOException("Error reading from configuration file: " + e.getMessage());
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid configuration in file: " + e.getMessage());
    }
  }


  @Override
  public void playToGrid(int cardIdxInHand, int row, int col) {
    if (cardIdxInHand >= currentPlayer.getHand().size()) {
      throw new IllegalArgumentException("Card idx in hand is out of bounds for index:"
              + cardIdxInHand + " and for size: " + currentPlayer.getHand().size());
    }
    if (row >= grid.getRows() || col >= grid.getCols()) {
      throw new IllegalArgumentException("Row or Col is out of bounds for the grid");
    }
    if (this.gameState == GameState.GAME_NOT_STARTED) {
      throw new IllegalStateException("Game has not started.");
    }
    if (this.gameState != GameState.PLACING_PHASE) {
      throw new IllegalStateException("It is not the placing phase");
    }

    Card card = currentPlayer.getHand().get(cardIdxInHand);
    Cell cell = this.grid.getCell(row, col);
    cell.placeCard(card);
    currentPlayer.removeCardFromHand(card);
    gameState = GameState.BATTLE_PHASE;
    battlePhase(card);
    switchCurrentPlayer();
    gameState = GameState.PLACING_PHASE;
  }

  @Override
  public void switchCurrentPlayer() {
    if (currentPlayer.getColor() == Color.RED) {
      currentPlayer = playerBlue;
    } else {
      currentPlayer = playerRed;
    }

    notifyTurnSwitched();

    if (currentPlayer instanceof MachinePlayer) {
      Strategy strategy = ((MachinePlayer) currentPlayer).getStrategy();
      Move bestMove = strategy.findMove();
      int cardIdxInHand = bestMove.getCardIdxInHand();
      int row = bestMove.getRow();
      int col = bestMove.getCol();
      playToGrid(cardIdxInHand, row, col);
    }
  }

  @Override
  public void setPlayer(Color color, Player player) {
    if (color == Color.RED) {
      playerRed = player;
    } else {
      playerBlue = player;
    }
  }

  /**
   * Initiates the battle phase after a card has been placed to the grid.
   *
   * @param card last card to be placed to the grid
   * @throws IllegalStateException    if the game has not begun or is over
   * @throws IllegalArgumentException if the card is null
   */
  private void battlePhase(Card card) {
    if (gameState == GameState.GAME_NOT_STARTED || gameState == GameState.GAME_OVER) {
      throw new IllegalStateException("Game is not in progress");
    }
    if (card == null) {
      throw new IllegalArgumentException("Card can not be null");
    }
    Cell placedCell = grid.searchCell(card);
    int rowIdx = -1;
    int colIdx = -1;
    for (int row = 0; row < grid.getRows(); row++) {
      for (int col = 0; col < grid.getCols(); col++) {
        if (placedCell.equals(grid.getCell(row, col))) {
          rowIdx = row;
          colIdx = col;
          break;
        }
      }
    }

    // if the newly placed card is not on a top row.
    if (rowIdx != 0) {
      // if the card above the new card is a cell with a card on it.
      if (grid.getCell(rowIdx - 1, colIdx) instanceof CardCell
              && !(grid.getCell(rowIdx - 1, colIdx).isEmpty())
              && getOwner(rowIdx - 1, colIdx) != card.getOwner()) {
        // call the battle step method on the new card and existing card
        Card existingCard = grid.getCell(rowIdx - 1, colIdx).getCard();
        battleStep(card, existingCard, CardinalDirection.NORTH);
      }
    } // repeat for all possible directions
    if (rowIdx != grid.getRows() - 1) {
      if (grid.getCell(rowIdx + 1, colIdx) instanceof CardCell
              && !(grid.getCell(rowIdx + 1, colIdx).isEmpty())
              && getOwner(rowIdx + 1, colIdx) != card.getOwner()) {
        battleStep(card, grid.getCell(rowIdx + 1, colIdx).getCard(), CardinalDirection.SOUTH);
      }
    }
    if (colIdx != 0) {
      if (grid.getCell(rowIdx, colIdx - 1) instanceof CardCell
              && !(grid.getCell(rowIdx, colIdx - 1).isEmpty())
              && getOwner(rowIdx, colIdx - 1) != card.getOwner()) {
        battleStep(card, grid.getCell(rowIdx, colIdx - 1).getCard(), CardinalDirection.WEST);
      }
    }
    if (colIdx != grid.getCols() - 1) {
      if (grid.getCell(rowIdx, colIdx + 1) instanceof CardCell
              && !(grid.getCell(rowIdx, colIdx + 1).isEmpty())
              && getOwner(rowIdx, colIdx + 1) != card.getOwner()) {
        battleStep(card, grid.getCell(rowIdx, colIdx + 1).getCard(), CardinalDirection.EAST);
      }
    }
    gameState = GameState.PLACING_PHASE;
  }

  /**
   * Method to compare a new placed card to an existing adjacent card on the grid
   * and changes the owner of the existing card if necessary.
   * This method is called from the battlePhase method.
   *
   * @param newCard      newly placed card
   * @param existingCard existing card adjacent to thew new card
   * @param cardinal     direction of the existing card in relation to the new card
   *                     ex. if the new card has an existing card above it, the
   *                     cardinal input would be north
   */
  public void battleStep(Card newCard, Card existingCard, CardinalDirection cardinal) {
    // Gets the attack value of the new card that will be used for battle
    int newCardValue = newCard.getValue(cardinal);
    // Gets the attack value of the existing card that will be used for battle
    int existingCardValue = existingCard.getValue(cardinal.opposite());
    // If the new card's value is greater than the existing card, then the existing card
    // will inherit the owner of the new card
    if (newCardValue > existingCardValue) {
      existingCard.setOwner(newCard.getOwner());
      // Calls the battlePhase method on the existing card to possibly start a combo battle phase
      battlePhase(existingCard);
    }
  }

  @Override
  public boolean checkGameOver() {
    if (gameState == GameState.GAME_NOT_STARTED) {
      throw new IllegalArgumentException("Game has not started (checkGameOver)");
    }

    // iterates through every cell in the grid
    for (int row = 0; row < grid.getRows(); row++) {
      for (int col = 0; col < grid.getCols(); col++) {
        // if the cell is a card cell and does not have a card in it, return false
        if (grid.getCell(row, col) instanceof CardCell
                && ((CardCell) grid.getCell(row, col)).isEmpty()) {
          return false;
        }
      }
    }

    // this statement is reached if the above loop finishes without finding any empty cells
    gameState = GameState.GAME_OVER;
    notifyGameOver();
    return true;
  }

  @Override
  public String announceWinner() {
    if (gameState != GameState.GAME_OVER) {
      throw new IllegalStateException("Game is not over");
    }
    if (gameState == GameState.GAME_NOT_STARTED) {
      throw new IllegalStateException("Game has not started (announceWinner)");
    }
    if (gameState == GameState.BATTLE_PHASE) {
      throw new IllegalStateException("Game is still in the battling phase");
    }

    int redScore = calculateScore(playerRed);
    int blueScore = calculateScore(playerBlue);

    if (redScore > blueScore) {
      return "The Red Player has won!";
    } else if (blueScore > redScore) {
      return "The Blue Player has won!";
    } else {
      return "It's a tie!";
    }
  }

  @Override
  public List<Card> getHand(Color player) {
    if (player == Color.RED) {
      return playerRed.getHand();
    } else {
      return playerBlue.getHand();
    }
  }

  @Override
  public Player currentPlayer() {
    return currentPlayer;
  }

  @Override
  public Player otherPlayer() {
    if (gameState == GameState.GAME_NOT_STARTED) {
      throw new IllegalStateException("Game hasn't started");
    }
    if (currentPlayer.getColor() == Color.RED) {
      return playerBlue;
    } else {
      return playerRed;
    }
  }

  @Override
  public Grid getGrid() {
    return grid.copy();
  }

  @Override
  public int getGridRows() {
    return grid.getRows();
  }

  @Override
  public int getGridCols() {
    return grid.getCols();
  }

  @Override
  public Card cellContents(int row, int col) {
    if (gameState == GameState.GAME_NOT_STARTED) {
      throw new IllegalStateException("Game has not started (cellContents)");
    }
    if (grid.getCell(row, col).isEmpty()) {
      throw new IllegalStateException("Cell is empty");
    }

    return grid.getCell(row, col).getCard();
  }

  @Override
  public Color cellOwner(int row, int col) {
    if (gameState == GameState.GAME_NOT_STARTED) {
      throw new IllegalStateException("Game has not started (cellOwner)");
    }

    return grid.getCell(row, col).getCard().getOwner();
  }

  @Override
  public int playerScore(Color color) {
    if (gameState == GameState.GAME_NOT_STARTED) {
      throw new IllegalStateException("Game has not started (playerScore)");
    }

    Player player;
    if (color == Color.RED) {
      player = playerRed;
    } else {
      player = playerBlue;
    }

    int score = 0;
    // iterates through every cell in the grid
    for (int row = 0; row < grid.getRows(); row++) {
      for (int col = 0; col < grid.getCols(); col++) {
        // if the cell is a card cell, and the card in that cell is of the given player's color,
        // increment the score by 1
        if (grid.getCell(row, col) instanceof CardCell && !grid.getCell(row, col).isEmpty()) {
          if (((CardCell) grid.getCell(row, col)).getCard().getOwner() == color) {
            score++;
          }
        }
      }
    }

    return score + player.getHand().size();
  }

  @Override
  public boolean isHole(int row, int col) {
    return grid.isHole(row, col);
  }

  @Override
  public boolean isEmpty(int row, int col) {
    Cell cell = grid.getCell(row, col);

    return cell.isEmpty();
  }

  @Override
  public Color getOwner(int row, int col) {
    if (!grid.getCell(row, col).isEmpty()) {
      return grid.getCell(row, col).getCard().getOwner();
    }
    throw new IllegalStateException("No card present at this position");
  }

  @Override
  public ThreeTriosGameModel copy() {
    return new ThreeTriosGameModel(grid.copy(), playerRed.copy(), playerBlue.copy(),
            currentPlayer.copy(), gameState);
  }

  @Override
  public GameState getGameState() {
    return gameState;
  }

  /**
   * Calculates the score of a given player, which is the number of cards that they own
   * on the grid and in their hand.
   *
   * @param player given player you want the score of
   * @return the score of the given player
   * @throws IllegalStateException if the game hasn't started
   */
  private int calculateScore(Player player) {
    if (gameState == GameState.GAME_NOT_STARTED) {
      throw new IllegalStateException("Game has not started (calculateScore)");
    }

    Color color = player.getColor();
    int score = 0;
    // iterates through every cell in the grid
    for (int row = 0; row < grid.getRows(); row++) {
      for (int col = 0; col < grid.getCols(); col++) {
        // if the cell is a card cell, and the card in that cell is of the given player's color,
        // increment the score by 1
        if (grid.getCell(row, col) instanceof CardCell
                && ((CardCell) grid.getCell(row, col)).getCard().getOwner() == color) {
          score++;
        }
      }
    }

    score += player.getHand().size();

    return score;
  }

  /**
   * Distributes the to the two players. If the number of cards is odd,
   * 1 extra card will go to the red player.
   */
  private void distributeCards() {
    for (int i = 0; i < cards.size(); i++) {
      if (i % 2 == 0) {
        cards.get(i).setOwner(Color.RED);
        playerRed.addHand(cards.get(i));
      } else {
        cards.get(i).setOwner(Color.BLUE);
        playerBlue.addHand(cards.get(i));
      }
    }
  }

  /**
   * Shuffles the cards.
   *
   * @throws IllegalStateException if the game has already begun
   */
  private void shuffle() {
    if (gameState != GameState.GAME_NOT_STARTED) {
      throw new IllegalStateException("Game has already started");
    }

    for (int idx = 0; idx < cards.size(); idx++) {
      int j = shuffler.nextInt(idx + 1);
      Collections.swap(cards, idx, j);
    }
  }

  public void addListener(Features listener) {
    listeners.add(listener);
  }

  public void removeListener(Features listener) {
    listeners.remove(listener);
  }

  private void notifyTurnSwitched() {
    for (Features listener : listeners) {
      listener.turnSwitched();
    }
  }

  private void notifyGameOver() {
    for (Features listener : listeners) {
      listener.gameOver();
    }
  }
}
