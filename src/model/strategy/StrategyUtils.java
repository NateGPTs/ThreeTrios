package model.strategy;

import java.util.HashMap;
import java.util.Map;

/**
 * A util class to abstract methods that are detrimental to strategies functioning.
 */
public class StrategyUtils {

  /**
   * Creates the format of the move.
   *
   * @param index represents the players card hand index.
   * @param row   represents the row index of the card placement on the board.
   * @param col   represents the column index of the card placement on the board.
   * @return a Map with an "index", "row", "col" key representing the hand, row, col values.
   */
  public Map<String, Integer> createMoveInfo(int index, int row, int col) {

    Map<String, Integer> cardInfo = new HashMap<String, Integer>();
    cardInfo.put("index", index);
    cardInfo.put("row", row);
    cardInfo.put("col", col);

    return cardInfo;
  }

}
