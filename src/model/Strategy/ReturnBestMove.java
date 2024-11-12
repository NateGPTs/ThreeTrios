package model.Strategy;

import java.util.HashMap;
import java.util.Map;
import model.ReadOnlyThreeThriosModel;
import model.player.Player;

public interface ReturnBestMove {

  Map<String, Integer> getBestMove(ReadOnlyThreeThriosModel model, Player player);

}
