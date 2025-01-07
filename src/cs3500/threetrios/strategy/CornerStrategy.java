package cs3500.threetrios.strategy;

import java.util.LinkedHashMap;
import java.util.Map;

import cs3500.threetrios.model.CardCell;
import cs3500.threetrios.model.CardinalDirection;
import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.ThreeTriosGameModel;
import cs3500.threetrios.model.ThreeTriosModel;

/**
 * Strategy class to determine which corner to play a card in to expose
 * the least attack values and minimize risk.
 */
public class CornerStrategy extends AbstractStrategy implements Strategy {
  private final ThreeTriosModel model;

  public CornerStrategy(ThreeTriosModel model) {
    this.model = model;
  }

  @Override
  public Move findMove() {
    Map<Move, Integer> map = new LinkedHashMap<>();
    Player player = model.currentPlayer();
    ThreeTriosGameModel mockModel;
    int score;
    for (int cardIdx = 0; cardIdx < player.getHand().size(); cardIdx++) {
      for (int row = 0; row < model.getGridRows(); row++) {
        for (int col = 0; col < model.getGridCols(); col++) {
          mockModel = model.copy();
          score = 0;
          if (row == 0) {
            score += 11;
          } else if (mockModel.getGrid().isHole(row - 1, col )) {
            score += 11;
          } else {
            score += player.getHand().get(cardIdx).getValue(CardinalDirection.SOUTH);
          }
          if (row == mockModel.getGridRows() - 1) {
            score += 11;
          } else if (mockModel.getGrid().isHole(row + 1, col)) {
            score += 11;
          } else {
            score += player.getHand().get(cardIdx).getValue(CardinalDirection.NORTH);
          }
          if (col == 0) {
            score += 11;
          } else if (mockModel.getGrid().isHole(row, col - 1)) {
            score += 11;
          } else {
            score += player.getHand().get(cardIdx).getValue(CardinalDirection.WEST);
          }
          if (col == mockModel.getGridCols() - 1) {
            score += 11;
          } else if (mockModel.getGrid().isHole(row, col + 1)) {
            score += 11;
          } else {
            score += player.getHand().get(cardIdx).getValue(CardinalDirection.EAST);
          }
          int distance = row + col;
          score -= distance;
          if (!mockModel.getGrid().isHole(row, col) && mockModel.isEmpty(row, col)) {
            map.put(new Move(cardIdx, row, col), score);
          }
          if (mockModel.getGrid().getCell(row, col) instanceof CardCell) {
            if (mockModel.getGrid().getCell(row, col).isEmpty()) {
              mockModel.playToGrid(cardIdx, row, col);
            }
          }
        }
      }
    }
    return findBestMove(map);
  }
}