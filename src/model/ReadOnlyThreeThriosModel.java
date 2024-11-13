package model;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import model.StandardThreeTrios.PlayerKey;
import model.Strategy.Coordinate;
import model.card.Card;
import model.card.Direction;
import model.cell.Cell;
import model.player.Player;

public interface ReadOnlyThreeThriosModel {

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

  int gridWidth();

  int gridHeight();

  Cell getCell(Coordinate coord);

  Player whoOwns(int row, int col);

  int flipCount(Card given, int row, int col, Player player);

  int playerScore(Player given);

  boolean isGameOver();

  Map<Direction, Cell> getAdjacentCells(Coordinate coord, Predicate<Cell> cellPredicate);

}
