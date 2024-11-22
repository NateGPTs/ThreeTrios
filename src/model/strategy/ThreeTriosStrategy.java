package model.strategy;

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
   * @param model  The model given.
   * @param player the player doing the move.
   * @return coordinate to card pairings represented by a hashmap.
   */
  public List<Map<String, Integer>> chooseMove(ReadOnlyThreeTriosModel model, Player player);

  /**
   * Each strategy has a moveLog to document all the cells and combinations checked.
   *
   * @return a list of all moves  with an "index", "row", "col" key. For hand index, row and column.
   */
  public List<Map<String, Integer>> moveLog();


}
