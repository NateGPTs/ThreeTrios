package model.strategy;

import java.util.Map;
import model.ReadOnlyThreeTriosModel;
import model.player.Player;

/**
 * An interface that returns the best move for the ThreeTriosGame.
 */
public interface ReturnBestMove {

  /**
   * Return the best move based on the corresponding strategy.
   *
   * @param model  the model given.
   * @param player the player we are applying this move for.
   * @return a Map with a string "index", "row", "col" key representing handIndex, row, column.
   */
  Map<String, Integer> getBestMove(ReadOnlyThreeTriosModel model, Player player);

}
