package model.Strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.ReadOnlyThreeThriosModel;
import model.card.Card;
import model.cell.Cell;
import model.player.Player;

/**
 * Represents a strategy for the CPU, where moves that flip the most cards are "best".
 */
public class MostFlips implements ThreeTriosStrategy {



  public List<Map<String, Integer>> chooseMove(ReadOnlyThreeThriosModel model,
      Player player) {
    Cell[][] grid = model.getGrid();
    List<Map<String, Integer>> moves = new ArrayList<Map<String, Integer>>();
    ArrayList<Card> playerHand = player.getHand();
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
              highestCount = count;
            } else if (highestCount != 0 && (count == highestCount)) {
              moves.add(createMoveInfo(handIndx, row, col));
            }
          }
        }
      }
    }

    return moves;
  }

    private Map<String, Integer> createMoveInfo (int index, int row, int col) {

      return new StrategyUtils().createMoveInfo(index, row, col);
    }



}
