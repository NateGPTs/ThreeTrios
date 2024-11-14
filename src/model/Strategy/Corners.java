package model.Strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.ReadOnlyThreeTriosModel;
import model.card.Card;
import model.card.Direction;
import model.cell.Cell;
import model.player.Player;

/**
 * A strategy implementation for ThreeTrios game that focuses on playing cards in corner positions.
 * This strategy prioritizes placing cards with high attack values in the corners of the game board.
 */
public class Corners implements ThreeTriosStrategy {

  /** Maintains a record of moves and cell inspections performed by this strategy. */
  private final List<Map<String, Integer>> log;

  /**
   * Constructs a new Corners strategy with an empty move log.
   */
  public Corners() {
    this.log = new ArrayList<Map<String, Integer>>();
  }

  /**
   * Constructs a new Corners strategy with a pre-existing move log.
   *
   * @param log the list of previous move records to initialize with
   */
  public Corners(List<Map<String, Integer>> log) {
    this.log = log;
  }

  @Override
  public List<Map<String, Integer>> chooseMove(ReadOnlyThreeTriosModel model, Player player) {
    Map<Integer, Coordinate> cornerCells = getCornerCells(model);
    List<Map<String, Integer>> moves = new ArrayList<Map<String, Integer>>();

    // For each corner, create a move inspection record
    for (Map.Entry<Integer, Coordinate> entry : cornerCells.entrySet()) {
      Coordinate coord = entry.getValue();
      Cell cell = model.getGrid()[coord.getRow()][coord.getCol()];

      // Only inspect non-hole cells that are empty
      if (!cell.isHole() && cell.isEmpty()) {
        // Log that we inspected this corner
        Map<String, Integer> inspection = createMoveInfo(0, coord.getRow(), coord.getCol());
        this.log.add(inspection);

        // Then proceed with move evaluation
        Map<Direction, Cell> adjacentCell =
            model.getAdjacentCells(coord, c -> !c.isHole() && c.isEmpty());
        List<Direction> directions = Arrays.asList(Direction.values());
        moves.addAll(highestAttackVal(player, directions, coord));
      }
    }

    return moves;
  }

  @Override
  public List<Map<String, Integer>> moveLog() {
    return this.log;
  }

  private Map<Integer, Coordinate> getCornerCells(ReadOnlyThreeTriosModel model) {

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
        this.log.add(createMoveInfo(index, coord.getRow(), coord.getCol()));
      } else if (currHighestVal > highestAttackVal) {
        highestAttackMap.clear();
        highestAttackVal = currHighestVal;
        highestAttackMap.add(createMoveInfo(index, coord.getRow(), coord.getCol()));
        this.log.add(createMoveInfo(index, coord.getRow(), coord.getCol()));
      }
    }

    return highestAttackMap;
  }

  private Map<String, Integer> createMoveInfo (int index, int row, int col) {

    return new StrategyUtils().createMoveInfo(index, row, col);
  }

}
