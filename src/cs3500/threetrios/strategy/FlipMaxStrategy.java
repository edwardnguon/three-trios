package cs3500.threetrios.strategy;

import java.util.LinkedHashMap;
import java.util.Map;

import cs3500.threetrios.model.CardCell;
import cs3500.threetrios.model.Color;
import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.ThreeTriosGameModel;
import cs3500.threetrios.model.ThreeTriosModel;

/**
 * Strategy class to determine which Move is will flip over
 * the most cards.
 */
public class FlipMaxStrategy extends AbstractStrategy implements Strategy {
  private final ThreeTriosModel model;

  /**
   * Public constructor for this strategy to observe a given model.
   * @param model given game model
   */
  public FlipMaxStrategy(ThreeTriosModel model) {
    this.model = model;
  }

  @Override
  public Move findMove() {
    // Mapping moves to their "score"
    Map<Move, Integer> map = new LinkedHashMap<>();

    Player player = model.currentPlayer();
    Color color = player.getColor();

    ThreeTriosGameModel mockModel;

    // Iterates through every possible Move a player can make with a given hand and grid
    for (int cardIdxInHand = 0; cardIdxInHand < player.getHand().size(); cardIdxInHand++) {
      for (int row = 0; row < model.getGridRows(); row++) {
        for (int col = 0; col < model.getGridCols(); col++) {
          mockModel = model.copy();
          player = mockModel.currentPlayer();
          if (mockModel.getGrid().getCell(row, col) instanceof CardCell) {
            if (mockModel.getGrid().getCell(row, col).isEmpty()) {
              mockModel.playToGrid(cardIdxInHand, row, col);
              int score = mockModel.playerScore(color);
              Move move = new Move(cardIdxInHand, row, col);
              map.put(move, score);
            }
          }
        }
      }
    }

    return findBestMove(map);
  }
}
