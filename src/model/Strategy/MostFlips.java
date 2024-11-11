package model.Strategy;

import java.util.ArrayList;
import java.util.HashMap;
import model.ReadOnlyThreeThriosModel;
import model.card.Card;
import model.cell.Cell;
import model.player.Player;

/**
 * Represents a strategy for the CPU, where moves that flip the most cards are "best".
 */
public class MostFlips implements ThreeTriosStrategy {

  public ArrayList<HashMap<String, Integer>> chooseMove(ReadOnlyThreeThriosModel model,
      Player player) {
    Cell[][] grid = model.getGrid();
    ArrayList<HashMap<String, Integer>> moves = new ArrayList<>();
    ArrayList<Card> playerHand = player.getHand();
    int highestCount = 0;

    for (int handIndx = 0; handIndx < playerHand.size(); handIndx++) {
      for (int row = 0; row < grid.length; row++) {
        for (int col = 0; col < grid[row].length; col++) {
          if (!grid[row][col].isHole() && grid[row][col].isEmpty()) {
            int count = model.flipCount(playerHand.get(handIndx), new Coord(row, col), player);
            if (count > highestCount) {
              moves.clear();
              HashMap<String, Integer> highestMove = createMoveInfo(handIndx, row, col);
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

    private HashMap<String, Integer> createMoveInfo (int index, int row, int col) {

      HashMap<String, Integer> cardInfo = new HashMap<String, Integer>();
      cardInfo.put("index", index);
      cardInfo.put("row", row);
      cardInfo.put("col", col);

      return cardInfo;
    }



}
