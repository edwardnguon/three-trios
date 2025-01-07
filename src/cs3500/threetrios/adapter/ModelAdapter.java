package cs3500.threetrios.adapter;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cs3500.threetrios.model.ThreeTriosGameModel;
import cs3500.threetrios.provider.model.Card;
import cs3500.threetrios.provider.model.Cell;
import cs3500.threetrios.provider.model.Color;
import cs3500.threetrios.provider.model.ModelListener;
import cs3500.threetrios.provider.model.ThreeTriosModel;

/**
 * Adapts our model to the client's model type by extending our model
 * and implementing the client's model interface.
 */
public class ModelAdapter extends ThreeTriosGameModel implements ThreeTriosModel {
  /**
   * Constructor for the model adapter using super to call to parent constructor.
   */
  public ModelAdapter() {
    super();
  }

  @Override
  public Color getCurrentPlayer() {
    return ColorAdapter.toClientColor(super.currentPlayer().getColor());
  }

  @Override
  public Boolean isGameOver() {
    return super.checkGameOver();
  }

  @Override
  public ThreeTriosModel getBoard() {
    return this;
  }

  @Override
  public Color getWinner() {
    int redScore = super.playerScore(cs3500.threetrios.model.Color.RED);
    int blueScore = super.playerScore(cs3500.threetrios.model.Color.BLUE);

    if (redScore > blueScore) {
      return Color.RED;
    } else if (blueScore > redScore) {
      return Color.BLUE;
    }
    return null;
  }

  @Override
  public List<Card> getPlayerHand(Color color) {
    if (color == ColorAdapter.toClientColor(currentPlayer().getColor())) {
      return CardAdapter.convertHand(super.currentPlayer().getHand());
    } else {
      return CardAdapter.convertHand(super.otherPlayer().getHand());
    }
  }

  @Override
  public int getBoardWidth() {
    return super.getGridCols();
  }

  @Override
  public int getBoardHeight() {
    return super.getGridRows();
  }

  @Override
  public Cell getCellAt(int row, int col) {
    cs3500.threetrios.model.Cell cell = super.getGrid().getCell(row, col);
    return new CellAdapter(cell);
  }

  @Override
  public int getScore(Color playerColor) {
    return super.playerScore(ColorAdapter.toOurColor(playerColor));
  }

  @Override
  public void registerListener(ModelListener listener) {
    // listener functionality is handled in the implementation our controller already
  }

  @Override
  public void startGame(Boolean shuffle, String cardConfigPath, String gridConfigPath) {
    File cardsFile = new File(cardConfigPath);
    File gridFile = new File(gridConfigPath);
    try {
      super.startGame(gridFile, cardsFile, shuffle);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void startGameInProgress(List<Card> redList, List<Card> blueList, ThreeTriosModel board) {
    // never actually used in implementation
  }

  @Override
  public void playToCell(int rowIndex, int colIndex, int indexInHand) {
    super.playToGrid(indexInHand, rowIndex, colIndex);
  }
}
