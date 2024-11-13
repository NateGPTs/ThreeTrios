package model.grid;

import java.util.Map;
import java.util.function.Predicate;
import model.card.Card;
import model.card.Direction;
import model.cell.Cell;
import model.player.Player;

/**
 * Represents a set of commands executing game actions in the game. Defines operations for player
 * actions within the game.
 */
public interface GridCommands {

  /**
   * Executes a play action by placing a card at the specified row and column on the grid. The card
   * is selected from the player's hand by its index.
   *
   * @param row       the row index where the card will be placed
   * @param player    the player executing the move
   * @param col       the column index where the card will be placed
   * @param handIndex the index of the card to play in the player's hand
   * @throws IllegalArgumentException if the position is out of bounds, the cell is not empty, or
   *                                  the player or hand index is invalid
   */
  void executePlay(int row, int col, int handIndex, Player player);


  int countPotentialFlips(int row, int col, Card card, Player player);

  Map<Direction, Cell> getAdjacentCells(Cell cell, Predicate<Cell> cellPredicate);

}
