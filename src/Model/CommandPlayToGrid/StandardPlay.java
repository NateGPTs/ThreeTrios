package Model.CommandPlayToGrid;

import Model.Card.Card;
import Model.Card.Direction;
import Model.Cell.Cell;
import Model.ModelPlayer.Player;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiFunction;

public class StandardPlay implements GridCommands {

  private final Cell[][] grid;
  private Player player;
  private final HashMap<Direction, BiFunction<Integer, Integer, Cell>> DirectionalValues;


  public StandardPlay(Cell[][] grid) {

    this.grid = grid;
    this.player = null;
    DirectionalValues = new HashMap<>();
    DirectionalValues.put(Direction.EAST, (r, c) -> this.grid[r][c + 1]);
    DirectionalValues.put(Direction.WEST, (r, c) -> this.grid[r][c - 1]);
    DirectionalValues.put(Direction.NORTH, (r, c) -> this.grid[r - 1][c]);
    DirectionalValues.put(Direction.SOUTH, (r, c) -> this.grid[r + 1][c]);
  }

  /**
   * Get all the neighbouring cells that have cards and aren't marked as holes.
   *
   * @param given cell.
   * @return HashMap with direction as a key and Cell as the value.
   */
  private HashMap<Direction, Cell> getValidNeighbour(Cell given) {

    HashMap<Direction, Cell> adjacentCells = new HashMap<Direction, Cell>();

    for(Direction direction : Direction.values()) {
      try {
        BiFunction<Integer, Integer, Cell> currDirection = DirectionalValues.get(direction);
        Cell adjacentCell = currDirection.apply(given.getRow(), given.getCol());

        if (!adjacentCell.isHole() && !adjacentCell.isEmpty() && !adjacentCell.whoOwns()
            .equals(this.player)) {
          adjacentCells.put(direction, currDirection.apply(given.getRow(), given.getCol()));
        }
      } catch (ArrayIndexOutOfBoundsException e) {

        // dont add anything if the array doesnt have the cells position.
      }

    }

    return adjacentCells;
  }

  /**
   * Check if the specified row and column is within the bounds of the 2D grid array.
   *
   * @param row the row.
   * @param col the column.
   * @return yes if within bounds no otherwise.
   */
  private boolean isInBounds(int row, int col) {

    // Check if row is within the array's number of rows
    if (row < 0 || row >= this.grid.length) {
      return false; // Out of bounds
    }

    // Check if column is within the number of columns for that row
    if (col < 0 || col >= this.grid[row].length) {
      return false; // Out of bounds
    }

    return true;
  }


  /**
   * For a given cell, check neighbouring cells with cards, and if the neighbouring cells
   * have an opposite direction attack value that is less than the given one,
   * that card cell flips owners.
   *
   * @param given Cell.
   */
  private void cardClash(Cell given) {

    HashMap<Direction, Cell> adjacentCells = getValidNeighbour(given);
    ArrayList<Cell> convertedCells = new ArrayList<>();

    Card theCard = given.getCard();

    for(Direction direction : adjacentCells.keySet()) {

      Cell adjacentCell = adjacentCells.get(direction);
      Card adjacentCard = adjacentCell.getCard();

      if(theCard.getAttackVal(direction) > adjacentCard.getAttackVal(direction.getOpposite())) {
        adjacentCard.setPlayer(this.player);
        convertedCells.add(adjacentCell);
      }

    }

    for(Cell cell : convertedCells) {
      cardClash(cell);

    }

  }


  /**
   * Play a card to an empty Cell in the grid. And have the card clash with
   * Neighbouring cards of a different owner.
   *
   * @param row the row to play to.
   * @param col column to play to.
   * @param handIndex the handIndex, for picking which card in the hand to play with.
   */
  @Override
  public void executePlay(int row, int col, int handIndex, Player given) {

    if(given == null) {
      throw new IllegalArgumentException("Given player must not be null");
    } else {
      this.player = given;
    }

    if(!isInBounds(row, col)) {
      throw new IllegalArgumentException("Row/Column out of bounds");
    }

    if(handIndex > player.getHand().size() - 1) {
      throw new IllegalArgumentException("Hand index out of bounds");
    }

    if(this.grid[row][col].isEmpty()) {

      this.grid[row][col].addCard(this.player.playCard(handIndex));
      cardClash(this.grid[row][col]);

    } else {
      throw new IllegalArgumentException("Cannot play to a empty cell");
    }

  }

  /**
   * Represents a CPU playing a move.
   */
  @Override
  public void executeCPUPlay() {

    // Implementation not required for this HW.


  }


}
