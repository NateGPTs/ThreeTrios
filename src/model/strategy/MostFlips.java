package model.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.ReadOnlyThreeTriosModel;
import model.card.Card;
import model.cell.Cell;
import model.player.Player;

/**
 * Represents a strategy for the CPU that prioritizes moves which flip the most opponent cards. This
 * strategy evaluates each possible move based on how many opponent cards it can capture and selects
 * the moves that result in the maximum number of flips.
 */
public class MostFlips implements ThreeTriosStrategy {

  /**
   * Maintains a record of moves and their evaluations performed by this strategy. Each entry in the
   * list is a map containing the move details (card index and position).
   */
  private final List<Map<String, Integer>> log;

  /**
   * Constructs a new MostFlips strategy with an empty move log.
   */
  public MostFlips() {
    this.log = new ArrayList<Map<String, Integer>>();
  }

  /**
   * Constructs a new MostFlips strategy with a pre-existing move log.
   *
   * @param log the list of previous move records to initialize with
   */
  public MostFlips(List<Map<String, Integer>> log) {
    this.log = log;
  }

  /**
   * Determines the optimal moves by evaluating every possible card placement and selecting those
   * that result in the most opponent card flips. For each card in the player's hand, this method: -
   * Checks every valid position on the grid - Calculates how many opponent cards would be flipped -
   * Keeps track of moves that result in the maximum number of flips
   *
   * @param model  the current state of the game
   * @param player the player for whom moves should be calculated
   * @return a list of moves where each move is represented as a map w/ card index and position.
   */
  public List<Map<String, Integer>> chooseMove(ReadOnlyThreeTriosModel model,
      Player player) {
    Cell[][] grid = model.getGrid();
    List<Map<String, Integer>> moves = new ArrayList<Map<String, Integer>>();
    List<Card> playerHand = player.getHand();
    int highestCount = 0;

    for (int handIndx = 0; handIndx < playerHand.size(); handIndx++) {
      for (int row = 0; row < grid.length; row++) {
        for (int col = 0; col < grid[row].length; col++) {
          if (!grid[row][col].isHole() && grid[row][col].isEmpty()) {
            int count = model.flipCount(playerHand.get(handIndx), row, col, player);
            if (count > highestCount) {
              moves.clear();
              Map<String, Integer> highestMove = createMoveInfo(handIndx, row, col);
              moves.add(highestMove);
              this.log.add(highestMove);
              highestCount = count;
            } else if (highestCount != 0 && (count == highestCount)) {
              moves.add(createMoveInfo(handIndx, row, col));
              this.log.add(createMoveInfo(handIndx, row, col));
            }
          }
        }
      }
    }

    return moves;
  }

  @Override
  public List<Map<String, Integer>> moveLog() {
    return this.log;
  }

  private Map<String, Integer> createMoveInfo(int index, int row, int col) {

    return new StrategyUtils().createMoveInfo(index, row, col);
  }


}
