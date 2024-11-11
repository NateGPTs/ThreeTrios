package model.Strategy;

import java.util.HashMap;
import model.ReadOnlyThreeThriosModel;
import model.player.Player;

public interface ReturnBestMove {

  HashMap<String, Integer> getBestMove(ReadOnlyThreeThriosModel model, Player player);

}
