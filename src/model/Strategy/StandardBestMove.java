package model.Strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.ReadOnlyThreeTriosModel;
import model.cell.Cell;
import model.player.Player;

/**
 * Represents the best move position returned by a strategy for the StandardThreeTrios class.
 */
public class StandardBestMove implements ReturnBestMove {

  private final List<ThreeTriosStrategy> strategies;

  /**
   * Constructs the class with a List of strategies to evaluate.
   *
   * @param strategies the list of strategies the class will evaluate.
   */
  public StandardBestMove(List<ThreeTriosStrategy> strategies) {

    this.strategies = strategies;

  }

  @Override
  public Map<String, Integer> getBestMove(ReadOnlyThreeTriosModel model, Player player) {

    for(ThreeTriosStrategy strategy : strategies) {
      Map<String, Integer> move = evaluateStrategy(model, player, strategy);
      if(move != null) {
        return move;
      }
    }

    return NoValidMoves(model.getGrid(), player);
  }



  private Map<String, Integer> evaluateStrategy(ReadOnlyThreeTriosModel model, Player player, ThreeTriosStrategy strategy) {

    List<Map<String, Integer>> listOfBestMoves = strategy.chooseMove(model, player);

    if(listOfBestMoves.isEmpty()) {
      return null;

    } else if (listOfBestMoves.size() == 1) {

      return listOfBestMoves.get(0);

    } else {

      return breakTie(listOfBestMoves);
    }
  }


  private Map<String, Integer> NoValidMoves (Cell[][] grid, Player player) {

    Map<String, Integer> cardInfo = new HashMap<String, Integer>();

    for(int row = 0; row < grid.length; row++) {
      for(int col = 0; col < grid[row].length; col++) {
        if(!grid[row][col].isHole() && grid[row][col].isEmpty()) {

          cardInfo.put("index", 0);
          cardInfo.put("row", row);
          cardInfo.put("col", col);

        }
      }
    }

    return cardInfo;
  }

  private Map<String, Integer> breakTie (List<Map<String, Integer>> moves) {

    if (moves.isEmpty()) {
      return null;
    }

    int minRow = Integer.MAX_VALUE;
    int minCol = Integer.MAX_VALUE;

    for (Map<String, Integer> move : moves) {
      int currentRow = move.get("row");
      int currentCol = move.get("col");

      if (currentRow < minRow) {
        minRow = currentRow;
        minCol = currentCol;
      }

      else if (currentRow == minRow && currentCol < minCol) {
        minCol = currentCol;
      }
    }

    List<Map<String, Integer>> candidateMoves = new ArrayList<>();
    for (Map<String, Integer> move : moves) {
      if (move.get("row") == minRow && move.get("col") == minCol) {
        candidateMoves.add(move);
      }
    }

    Map<String, Integer> bestMove = candidateMoves.get(0);
    int minIndex = Math.abs(candidateMoves.get(0).get("index"));

    for (Map<String, Integer> move : candidateMoves) {
      int currentIndex = Math.abs(move.get("index"));
      if (currentIndex < minIndex) {
        minIndex = currentIndex;
        bestMove = move;
      }
    }

    return bestMove;
  }


}
