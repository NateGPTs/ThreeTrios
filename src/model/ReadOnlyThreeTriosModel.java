package model;

import java.util.Map;
import java.util.function.Predicate;
import model.StandardThreeTrios.PlayerKey;
import model.Strategy.Coordinate;
import model.card.Card;
import model.card.Direction;
import model.cell.Cell;
import model.player.Player;

/**
 * Represents functions for the ThreeTriosModel that observe the state of the game.
 */
public interface ReadOnlyThreeTriosModel {

  /**
   * Gets the winner if game has ended.
   *
   * @return the winning player, or null if there is a tie
   * @throws IllegalStateException if the game is not over
   */
  Player getWinner();

  /**
   * Retrieves a map of the players in the game.
   *
   * @return a HashMap containing the players identified by their keys
   */
  Map<PlayerKey, Player> getPlayers();


  /**
   * Gets current state of the grid.
   *
   * @return a 2D array representing grid of cells
   */
  Cell[][] getGrid();

  /**
   * Retrieves player whose turn it currently is.
   *
   * @return the current player
   */
  Player currentPlayer();

  /**
   * Retrieve the width of the grid.
   *
   * @return Integer representing width.
   */
  int gridWidth();

  /**
   * Return the height of the grid.
   *
   * @return a Integer representing the height of the grid.
   */
  int gridHeight();

  /**
   * Given a coordinate class return the cell at that coordinate.
   *
   * @param coord representing the rows and column where the cell to return is.
   * @return a Cell class.
   */
  Cell getCell(Coordinate coord);

  /**
   * Checks who owns the card at the given rows and columns of the board.
   *
   * @param row Integer representing row.
   * @param col Integer representing column.
   * @return a Player.
   */
  Player whoOwns(int row, int col);

  /**
   * Calculate how many cards would be flipped if a given card was played at the given row and
   * column, for the given player.
   *
   * @param given the given card.
   * @param row Integer representing the row index of a grid in a 2d array.
   * @param col Integer representing the column index of a grid in a 2d array.
   * @param player the player this play is to simulate for.
   * @return an Integer representing how many cards will flip.
   */
  int flipCount(Card given, int row, int col, Player player);

  /**
   * Returns how many cards the player has in hand and board.
   *
   * @param given which player to calculate for.
   * @return how many cards the player has in hand and board.
   */
  int playerScore(Player given);

  /**
   * Checks if the game is over.
   *
   * @return a boolean true or false value.
   */
  boolean isGameOver();

  /**
   * Get the adjacent cell in North, South, East, West directions(if such exists) that meet.
   * The criteria of the given predicate.
   *
   * @param coord coordinates of the cell to check its adjacent neighbours.
   * @param cellPredicate the conditions adjacent cells have to meet to be put in the map.
   * @return a map of the direction to cell.
   */
  Map<Direction, Cell> getAdjacentCells(Coordinate coord, Predicate<Cell> cellPredicate);

}
