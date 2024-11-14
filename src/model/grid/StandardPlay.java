package model.grid;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import model.card.Card;
import model.card.Direction;
import model.card.ThreeTrioCards;
import model.cell.Cell;
import model.cell.ThreeTrioCell;
import model.player.Player;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiFunction;

/**
 * Represents a standard play system for a Three Trios grid-based game. Each cell on the grid can
 * take a card with directional attack values. You can execute player moves, handle card clashes
 * with neighboring cells, and do CPU-controlled plays.
 */
public class StandardPlay implements GridCommands {

  private final Cell[][] grid;
  private final Map<Direction, BiFunction<Integer, Integer, Cell>> DirectionalValues;
  private Player player;

  /**
   * Constructs a StandardPlay instance with a specific grid layout. Initializes based on North,
   * South, East, and West directions.
   *
   * @param grid a 2D array where each cell can contain a card or a hole
   */
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
  @Override
  public Map<Direction, Cell> getAdjacentCells(Cell given, Predicate<Cell> cellPredicate) {

    Map<Direction, Cell> adjacentCells = new HashMap<Direction, Cell>();

    for (Direction direction : Direction.values()) {
      try {
        BiFunction<Integer, Integer, Cell> currDirection = DirectionalValues.get(direction);
        Cell adjacentCell = currDirection.apply(given.getRow(), given.getCol());

        if (cellPredicate.test(adjacentCell)) {
          adjacentCells.put(direction, adjacentCell);
        }
      } catch (ArrayIndexOutOfBoundsException e) {

        // dont add anything if the array doesnt have the cells position.
      }

    }

    return adjacentCells;
  }

  private Map<Direction, Cell> getAdjacentOccupiedCells(Cell given) {
    return getAdjacentCells(given,
        cell -> !cell.isHole() &&
            !cell.isEmpty() &&
            !cell.checkOwnership(this.player)
    );
  }

  /**
   * Check if the specified row and column is within the bounds of the 2D grid array.
   *
   * @param row the row.
   * @param col the column.
   * @return True if within bounds false otherwise.
   */
  private boolean isInBounds(int row, int col) {

    if (row < 0 || row >= this.grid.length) {
      return false; // Out of bounds
    }

    return col >= 0 && col < this.grid[row].length; // Out of bounds
  }


  /**
   * For a given cell, check neighbouring cells with cards, and if the neighbouring cells have an
   * opposite direction attack value that is less than the given one, that card cell flips owners.
   *
   * @param given Cell.
   */
  private void cardClash(Cell given) {

    ArrayList<Cell> convertedCells = new ArrayList<>();

    clashAdjacentCards(given, convertedCells);

    for (Cell cell : convertedCells) {
      cardClash(cell);

    }
  }

  /**
   * Simulates a card play to count how many cells would flip, without actually making the moves.
   *
   * @param card Card to simulate playing
   * @param player Player making the move
   * @return Number of cells that would flip
   */
  public int countPotentialFlips(int row, int col, Card card, Player player) {

    if (!isInBounds(row, col) || card == null || player == null) {
      return 0;
    }

    if (!grid[row][col].isEmpty()) {
      return 0;
    }

    Cell tempCell = new ThreeTrioCell(row, col);
    tempCell.addCard(card);
    card.setPlayer(player);
    Set<Cell> cellsToFlip = new HashSet<>();
    simulateClash(tempCell, cellsToFlip, player);

    return cellsToFlip.size();
  }

  /**
   * Recursive simulation of card clash effects.
   */
  private void simulateClash(Cell given, Set<Cell> flippedCells, Player simulatedPlayer) {
    Map<Direction, Cell> adjacentCells = getAdjacentOccupiedCells(given);
    Card theCard = given.getCard();

    for (Direction direction : adjacentCells.keySet()) {
      Cell adjacentCell = adjacentCells.get(direction);
      Card adjacentCard = adjacentCell.getCard();

      if (theCard.getAttackVal(direction) > adjacentCard.getAttackVal(direction.getOpposite())) {
        if (flippedCells.add(adjacentCell)) {
          Cell tempCell = new ThreeTrioCell(adjacentCell.getRow(), adjacentCell.getCol());
          tempCell.addCard(new ThreeTrioCards(adjacentCard));
          tempCell.getCard().setPlayer(simulatedPlayer);
          simulateClash(tempCell, flippedCells, simulatedPlayer);
        }
      }
    }
  }




  private void clashAdjacentCards(Cell given, ArrayList<Cell> convertedCells) {

    Map<Direction, Cell> adjacentCells = getAdjacentOccupiedCells(given);

    Card theCard = given.getCard();

    for (Direction direction : adjacentCells.keySet()) {

      Cell adjacentCell = adjacentCells.get(direction);
      Card adjacentCard = adjacentCell.getCard();

      if (theCard.getAttackVal(direction) > adjacentCard.getAttackVal(direction.getOpposite())) {

        adjacentCard.setPlayer(this.player);
        convertedCells.add(adjacentCell);

      }
    }
  }


  private boolean inBoundsForHand(int handIndex) {

    return handIndex >= 0 && handIndex < this.grid.length;

  }

  /**
   * Play a card to an empty Cell in the grid. And have the card clash with Neighbouring cards of a
   * different owner.
   *
   * @param row       the row to play to.
   * @param col       column to play to.
   * @param handIndex the handIndex, for picking which card in the hand to play with.
   * @param given     the player given that is executing a play.
   */
  @Override
  public void executePlay(int row, int col, int handIndex, Player given) {

    if (given == null) {
      throw new IllegalArgumentException("Given player must not be null");
    } else {
      this.player = given;
    }

    if (!isInBounds(row, col)) {
      throw new IllegalArgumentException("Row/Column out of bounds");
    }

    if (!inBoundsForHand(handIndex)) {
      throw new IllegalArgumentException("Hand index out of bounds");
    }

    if (this.grid[row][col].isEmpty()) {

      this.grid[row][col].addCard(this.player.playCard(handIndex));
      cardClash(this.grid[row][col]);

    } else {
      throw new IllegalArgumentException("Cannot play to a empty cell");
    }

  }




}
