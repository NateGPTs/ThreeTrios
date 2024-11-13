package model.Strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.ReadOnlyThreeThriosModel;
import model.card.Card;
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
  public List<Map<String, Integer>> chooseMove(ReadOnlyThreeThriosModel model, Player player);

  public List<Map<String, Integer>> moveLog();


}
