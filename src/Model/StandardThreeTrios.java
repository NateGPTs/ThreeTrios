package Model;

import Model.CommandPlayToGrid.GridCommands;
import Model.CommandPlayToGrid.StandardPlay;
import Model.Cell.Cell;
import Model.ModelPlayer.Player;
import Model.ModelPlayer.ThreeTriosPlayer;
import java.awt.Color;
import java.util.HashMap;

/**
 * Represents a standard Three Trios game.
 * Manages the state, grid, players, and reenforces the
 * rules of the game/game progress.
 */

public class StandardThreeTrios implements ThreeTriosModel {

  /**
   * Possible states of the game.
   */
  public enum GameState {
    NOT_STARTED, OVER, ONGOING
  }

  /**
   * Keys to identify players.
   */
  public enum PlayerKey {
    ONE, TWO
  }

  private GameState gameState;
  private final Cell[][] grid;
  private Player whoseTurn;
  private final HashMap<PlayerKey, Player> players;
  private final GridCommands gridCommands;


  /**
   * Constructs a game model using the given grid.
   *
   * @param grid a non-null game grid
   * @throws IllegalArgumentException if the grid is null
   */
  public StandardThreeTrios(Cell[][] grid, Player playerOne, Player playerTwo) {

    if (grid == null) {
      throw new IllegalArgumentException("Grid cannot be null.");
    }

    this.grid = grid;
    this.players = new HashMap<>();
    this.players.put(PlayerKey.ONE, playerOne);
    this.players.put(PlayerKey.TWO, playerTwo);
    this.gameState = GameState.NOT_STARTED;
    this.whoseTurn = playerOne;
    this.gridCommands = new StandardPlay(grid);

  }

  @Override
  public void startGame() {


    if (this.gameState != GameState.NOT_STARTED) {
      throw new IllegalStateException("Game has already started or is over");
    }

    this.gameState = GameState.ONGOING;
  }



  @Override
  public void playToGrid(int row, int col, int handIndex) {

    if (this.gameState != GameState.ONGOING) {
      throw new IllegalStateException("Game is over or not started");
    }

    if (!isValidMove(row, col)) {
      throw new IllegalArgumentException("Invalid move.");
    }

    this.gridCommands.executePlay(row, col, handIndex, this.whoseTurn);

    if (isGridFull()) {
      this.gameState = GameState.OVER;
    } else {
      switchPlayerTurn();
    }
  }

  @Override
  public void playToGridCPU() {

    // Not to be implemented for this HW.

  }

  /**
   * Determines if player can make the move to the specific location on the grid.
   *
   * @param row row index of the cell
   * @param col col index of the cell
   * @return true if valid, false if not
   */
  private boolean isValidMove(int row, int col) {
    return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length &&
        !grid[row][col].isHole() && grid[row][col].getCard() == null;
  }


  /**
   * Checks if the grid is full of occupied cards. Considered full if
   * each cell contains a card.
   *
   * @return true if all non-hole cells contains a card
   */
  private boolean isGridFull() {
    for (Cell[] row : grid) {
      for (Cell cell : row) {
        if (!cell.isHole() && cell.getCard() == null) {
          return false;
        }
      }
    }
    return true;

  }

  /**
   * Changes the turn between player one and two.
   * The method switches to the other player after a turn is made.
   */
  private void switchPlayerTurn() {
    this.whoseTurn = (this.whoseTurn == players.get(PlayerKey.ONE)) ?
        players.get(PlayerKey.TWO) : players.get(PlayerKey.ONE);
  }

  @Override
  public Cell[][] getGrid() {



    return this.grid;
  }

  @Override
  public Player currentPlayer() {
    return this.whoseTurn;
  }

  @Override
  public boolean isGameOver() {

    for (Cell[] row : this.grid) {
      for (Cell cell : row) {
        if (cell.getCard() != null && cell.isEmpty()) {
          return false;
        }
      }
    }

    this.gameState = GameState.OVER;
    return true;
  }


  @Override
  public Player getWinner() {
    if (this.gameState != GameState.OVER) {
      throw new IllegalStateException("Game is not over");
    }
    int playerOneCount = countCards(players.get(PlayerKey.ONE));
    int playerTwoCount = countCards(players.get(PlayerKey.TWO));

    if (playerOneCount > playerTwoCount) {
      return players.get(PlayerKey.ONE);
    } else if (playerTwoCount > playerOneCount) {
      return players.get(PlayerKey.TWO);
    }
    return null;
  }

  /**
   * Counts total number of cards owned by a player in their hand and on the grid
   * @param player player whose cards are to count
   * @return total number of cards owned by the player
   */
  private int countCards(Player player) {
    int count = 0;
    for (Cell[] row : this.grid) {
      for (Cell cell : row) {
        if (cell.getCard() != null && cell.getCard().getPlayer().equals(player)) {
          count++;
        }
      }
    }
    count += player.getHand().size();
    return count;
  }


}
