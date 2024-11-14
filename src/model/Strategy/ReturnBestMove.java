package model.Strategy;

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
   * @param model the model given.
   * @param player the player we are applying this move for.
   * @return a Map<String, Integer> with an "index" key representing a players card hand index,
   * "row" key row index of the board, "col" key representing column index of board card placement.
   */
  Map<String, Integer> getBestMove(ReadOnlyThreeTriosModel model, Player player);

}
