package model.Strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.ReadOnlyThreeThriosModel;
import model.card.Card;
import model.card.Direction;
import model.cell.Cell;
import model.player.Player;

public class Corners implements ThreeTriosStrategy {


  @Override
  public List<Map<String, Integer>> chooseMove(ReadOnlyThreeThriosModel model, Player player) {

    Map<Integer, Coordinate> cornerCells = getCornerCells(model);
    List<Map<String, Integer>> moves = new ArrayList<Map<String, Integer>>();

    for (Map.Entry<Integer, Coordinate> entry : cornerCells.entrySet()) {
      Coordinate value = entry.getValue();
      Map<Direction, Cell> adjacentCell =
          model.getAdjacentCells(value, cell -> !cell.isHole() && cell.isEmpty());
      List<Direction> directions = Arrays.asList(Direction.values());
      moves.addAll(highestAttackVal(player, directions, value));
    }

    return moves;
  }

  private Map<Integer, Coordinate> getCornerCells(ReadOnlyThreeThriosModel model) {

    Cell[][] grid = model.getGrid();
    Map<Integer, Coordinate> cornerCells = new HashMap<Integer, Coordinate>();
    cornerCells.put(0, new Coord(0, 0));
    cornerCells.put(1, new Coord(0, -1 + model.gridWidth()));
    cornerCells.put(2, new Coord(-1 + model.gridHeight(), 0));
    cornerCells.put(3, new Coord(-1 + model.gridHeight(), -1 + model.gridWidth()));

    return cornerCells;
  }

  private List<Map<String, Integer>> highestAttackVal(Player player, List<Direction> directions, Coordinate coord) {

    List<Card> deck = player.getHand();
    List<Map<String, Integer>> highestAttackMap = new ArrayList<Map<String, Integer>>();
    int highestAttackVal = 0;

    for(int index = 0; index < deck.size(); index++) {
      Card currCard = deck.get(index);
      int currHighestVal = 0;
      for(Direction direction : directions) {
        currHighestVal += currCard.getAttackVal(direction);
      }

      if (currHighestVal == highestAttackVal) {
        highestAttackMap.add(createMoveInfo(index, coord.getRow(), coord.getCol()));
      } else if (currHighestVal > highestAttackVal) {
        highestAttackMap.clear();
        highestAttackVal = currHighestVal;
        highestAttackMap.add(createMoveInfo(index, coord.getRow(), coord.getCol()));
      }
    }

    return highestAttackMap;
  }

  private Map<String, Integer> createMoveInfo (int index, int row, int col) {

    return new StrategyUtils().createMoveInfo(index, row, col);
  }

}
