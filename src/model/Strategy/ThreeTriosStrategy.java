package model.Strategy;

import java.util.List;
import java.util.Map;
import model.ReadOnlyThreeTriosModel;
import model.player.Player;

/**
 * Represents a play style cpus can use for the game.
 */
public interface ThreeTriosStrategy {

  /**
   * Outputs best moves for the CPU, represented by coordinate to card pairings in a hashmap.
   *
   * @param model The model given.
   * @param player the player doing the move.
   * @return coordinate to card pairings represented by a hashmap.
   */
  public List<Map<String, Integer>> chooseMove(ReadOnlyThreeTriosModel model, Player player);

  /**
   * Each strategy has a moveLog to document all the cells and combinations checked.
   *
   * @return a list of all moves(each represented by a Map<String, Integer>)
   * with an "index" key representing a players card hand index, row" key row index of the board, "
   * col" key representing column index of board card placement.
   */
  public List<Map<String, Integer>> moveLog();


}
