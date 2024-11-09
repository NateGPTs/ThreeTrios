package model.grid;

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

  /**
   * Executes a play action by a CPU-controlled player. Details are determined by the game rules and
   * based on CPU's strategy.
   */
  void executeCPUPlay();

}
