package model.Strategy;

import java.util.List;
import java.util.Map;
import model.ReadOnlyThreeThriosModel;
import model.player.Player;

public class LessLikelyFlipped implements ThreeTriosStrategy {



  @Override
  public List<Map<String, Integer>> chooseMove(ReadOnlyThreeThriosModel model, Player player) {
    return List.of();
  }

}
