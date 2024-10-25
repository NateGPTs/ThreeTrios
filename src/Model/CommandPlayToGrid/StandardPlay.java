package Model.CommandPlayToGrid;

import Model.Card.Card;
import Model.Card.Direction;
import Model.Cell.Cell;
import Model.ModelPlayer.Player;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;
import java.util.function.BiFunction;

public class StandardPlay implements GridCommands {

  Cell[][] grid;
  Player player;
  int handIndex;
  HashMap<Direction, BiFunction<Integer, Integer, Cell>> DirectionalValues;


  public StandardPlay(Cell[][] grid, Player player, int handIndex) {

    if(handIndex > player.getHand().size() - 1) {
      throw new IllegalArgumentException("Hand index out of bounds");
    }

    this.grid = grid;
    this.player = player;
    this.handIndex = handIndex;
    DirectionalValues = new HashMap<>();
    DirectionalValues.put(Direction.EAST, (r, c) -> this.grid[r][c + 1]);
    DirectionalValues.put(Direction.WEST, (r, c) -> this.grid[r][c - 1]);
    DirectionalValues.put(Direction.NORTH, (r, c) -> this.grid[r - 1][c]);
    DirectionalValues.put(Direction.SOUTH, (r, c) -> this.grid[r + 1][c]);
  }


  private ArrayList<Cell> getAdjacentCells(int row, int col) {

    ArrayList<Cell> adjacentCells = new ArrayList<>();

    for(Direction direction : Direction.values()) {

      try {
        BiFunction<Integer, Integer, Cell> currDirection = DirectionalValues.get(direction);
        adjacentCells.add(currDirection.apply(row, col));

      } catch (IndexOutOfBoundsException e) {
        // The catch block just skips to the next item in the loop, if it finds an index
        // out of bounds exception.
      }

    }

    return adjacentCells;
  }


  private ArrayList<Card> getAdjacentCards(int row, int col) {

    ArrayList<Cell> adjacentCells = getAdjacentCells(row, col);
    ArrayList<Card> cards = new ArrayList<Card>();

    for(Cell cell : adjacentCells) {
      if(!cell.isEmpty() && !cell.isHole()) {
        cards.add(cell.getCard());

      }
    }

    return cards;
  }


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


  private void cardCombo() {



  }


  @Override
  public void executePlay(int row, int col) {

    if(!isInBounds(row, col)) {
      throw new IllegalArgumentException("Row/Column out of bounds");
    }

    if(this.grid[row][col].isEmpty()) {

      this.grid[row][col].addCard(this.player.getHand().get(handIndex));


    } else {
      throw new IllegalArgumentException("Cannot play to a empty cell");
    }

  }



}
