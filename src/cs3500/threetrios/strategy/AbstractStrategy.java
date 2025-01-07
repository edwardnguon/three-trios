package cs3500.threetrios.strategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Abstract strategy class to deduplicate code.
 */
public class AbstractStrategy {
  protected Move findBestMove(Map<Move, Integer> map) {
    List<Map.Entry<Move, Integer>> entryList = new ArrayList<>(map.entrySet());

    entryList.sort(Comparator
            .comparingInt((Map.Entry<Move, Integer> entry) -> entry.getKey().getRow())
            .thenComparingInt(entry -> entry.getKey().getCol())
            .thenComparingInt(entry -> entry.getKey().getCardIdxInHand()));

    int max = 0;
    Move bestMove = null;
    for (Map.Entry<Move, Integer> entry : map.entrySet()) {
      if (entry.getValue() > max) {
        max = entry.getValue();
        bestMove = entry.getKey();
      }
    }

    return bestMove;
  }
}
