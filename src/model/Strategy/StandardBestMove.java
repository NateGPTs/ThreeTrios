package model.Strategy;

import java.util.ArrayList;
import java.util.HashMap;
import model.ReadOnlyThreeThriosModel;
import model.cell.Cell;
import model.player.Player;

public class StandardBestMove implements ReturnBestMove {

  private ThreeTriosStrategy strategy;

  @Override
  public HashMap<String, Integer> getBestMove(ReadOnlyThreeThriosModel model, Player player) {

    ArrayList<HashMap<String, Integer>> listOfBestMoves = strategy.chooseMove(model, player);

    if(listOfBestMoves.isEmpty()) {

      NoValidMoves(model.getGrid(), player);

    } else if(listOfBestMoves.size() == 1) {

      return listOfBestMoves.get(0);

    } else {



    }
  }


  private HashMap<String, Integer> NoValidMoves(Cell[][] grid, Player player) {

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

  private HashMap<String, Integer> breakTie(ArrayList<HashMap<String, Integer>> moves) {


    for(HashMap<String, Integer> move : moves) {



    }

  }




}
