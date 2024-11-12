package model.Strategy;

import java.util.HashMap;
import java.util.Map;

public class StrategyUtils {

  public Map<String, Integer> createMoveInfo (int index, int row, int col) {

    HashMap<String, Integer> cardInfo = new HashMap<String, Integer>();
    cardInfo.put("index", index);
    cardInfo.put("row", row);
    cardInfo.put("col", col);

    return cardInfo;
  }

}
