package model.Strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.ReadOnlyThreeThriosModel;
import model.cell.Cell;
import model.player.Player;

public class StandardBestMove implements ReturnBestMove {

  private ThreeTriosStrategy strategy;

  @Override
  public Map<String, Integer> getBestMove(ReadOnlyThreeThriosModel model, Player player) {

    List<Map<String, Integer>> listOfBestMoves = strategy.chooseMove(model, player);

    if(listOfBestMoves.isEmpty()) {

      NoValidMoves(model.getGrid(), player);

    } else if(listOfBestMoves.size() == 1) {

      return listOfBestMoves.get(0);

    } else {

    return null;

    }

    return null;
  }


  private HashMap<String, Integer> NoValidMoves (Cell[][] grid, Player player) {

    HashMap<String, Integer> cardInfo = new HashMap<String, Integer>();

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


    for(Map<String, Integer> move : moves) {

      return null;

    }

    return null;
  }




}
