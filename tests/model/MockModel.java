package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Strategy.Corners;
import model.Strategy.MostFlips;
import model.Strategy.StrategyUtils;
import model.Strategy.ThreeTriosStrategy;
import model.card.Card;
import model.cell.Cell;
import model.player.Player;

/**
 * A MockModel to test the functionalities of the StandardThreeTrios model.
 */
public class MockModel extends StandardThreeTrios implements MockModelInterface {

  private final List<Map<String, Integer>> inspectedMoves;
  private final Map<String, Integer> flipValues;
  private final Map<String, Boolean> validMoves;
  private final List<Map<String, Integer>> log;
  private ThreeTriosStrategy cornerStrat;
  private ThreeTriosStrategy mostFlipStrat;

  /**
   * Constructor creates a mock model based on the given grid.
   *
   * @param grid to test the model on.
   */
  public MockModel(Cell[][] grid) {
    super(grid);
    this.inspectedMoves = new ArrayList<Map<String, Integer>>();
    this.flipValues = new HashMap<String, Integer>();
    this.validMoves = new HashMap<String, Boolean>();
    this.log = new ArrayList<Map<String, Integer>>();
    this.strategies = constructStrategies();
    initializeDefaultValidMoves();
  }

  private void initializeDefaultValidMoves() {
    Cell[][] grid = super.getGrid();
    for (int row = 0; row < grid.length; row++) {
      for (int col = 0; col < grid[0].length; col++) {
        if (!grid[row][col].isHole()) {
          String key = row + "," + col;
          validMoves.put(key, true);
        }
      }
    }
  }

  @Override
  public int flipCount(Card given, int row, int col, Player player) {
    Map<String, Integer> move = new StrategyUtils().createMoveInfo(0, row, col);
    inspectedMoves.add(move);
    String key = row + "," + col;
    return flipValues.getOrDefault(key, 0);
  }

  @Override
  public void setFlipValue(int row, int col, int value) {
    String key = row + "," + col;
    flipValues.put(key, value);
  }

  @Override
  public void setValidMove(int row, int col, boolean isValid) {
    String key = row + "," + col;
    validMoves.put(key, isValid);
  }

  @Override
  public List<Map<String, Integer>> getInspectedMoves() {
    return new ArrayList<Map<String, Integer>>(inspectedMoves);
  }

  @Override
  public void clearInspectionLog() {
    inspectedMoves.clear();
  }

  private List<ThreeTriosStrategy> constructStrategies() {
    this.mostFlipStrat = new MostFlips(this.log);
    this.cornerStrat = new Corners(this.log);
    List<ThreeTriosStrategy> strategies = new ArrayList<ThreeTriosStrategy>();
    strategies.add(mostFlipStrat);
    strategies.add(cornerStrat);
    return strategies;
  }

  @Override
  public void playCorners() {
    clearInspectionLog();
    this.cornerStrat.chooseMove(this, this.currentPlayer());
    this.inspectedMoves.addAll(((Corners)this.cornerStrat).moveLog());
  }

  @Override
  public void playMostFlips() {
    clearInspectionLog();
    this.mostFlipStrat.chooseMove(this, this.currentPlayer());
  }
}