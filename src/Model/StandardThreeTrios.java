package Model;

import Model.Card.Card;
import Model.CommandPlayToGrid.GridCommands;
import Model.CommandPlayToGrid.StandardPlay;
import Model.Cell.Cell;
import Model.ModelPlayer.Player;
import Model.ModelPlayer.ThreeTriosPlayer;
import java.awt.Color;
import java.util.ArrayList;
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
   */
  public StandardThreeTrios(Cell[][] grid) {

    this.players = new HashMap<>();
    this.gameState = GameState.NOT_STARTED;
    this.grid = grid;
    this.gridCommands = new StandardPlay(grid);

  }

  private int getNumOfOpenGridCells(Cell[][] grid) {

    int openGridCells = 0;

    for (Cell[] row : grid) {
      for (Cell cell : row) {
        if (!cell.isHole()) {
          openGridCells++;
        }
      }
    }

    return openGridCells;
  }


  private void ensureNonHoleCellsAreOdd(Cell[][] grid) {


    if(((getNumOfOpenGridCells(grid)) % 2) != 1) {
      throw new IllegalArgumentException("Number of non hole grid cells must be odd");
    }

  }


  @Override
  public void startGame(ArrayList<Card> deck) {

    if (this.gameState != GameState.NOT_STARTED) {
      throw new IllegalStateException("Game has already started or is over");
    }

    ensureNonHoleCellsAreOdd(this.grid);

    if (deck.size() < getNumOfOpenGridCells(this.grid) + 1) {
      throw new IllegalStateException("Number of cards in a "
          + "deck must be at least one greater than the grid");
    }

    int middlePoint = (getNumOfOpenGridCells(this.grid) + 1) / 2;
    ArrayList<Card> hand1 = new ArrayList<Card>();
    ArrayList<Card> hand2 = new ArrayList<Card>();

    for (int i = 0; i < middlePoint; i++) {
      hand1.add(deck.get(i));
    }

    for (int i = middlePoint; i < middlePoint * 2; i++) {
      hand2.add(deck.get(i));
    }

    Player playerOne = new ThreeTriosPlayer(Color.red, hand1);
    Player playerTwo = new ThreeTriosPlayer(Color.blue, hand2);

    this.players.put(PlayerKey.ONE, playerOne);
    this.players.put(PlayerKey.TWO, playerTwo);
    this.whoseTurn = playerOne;
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
        !grid[row][col].isHole() && grid[row][col].isEmpty();
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
        if (!cell.isHole() && cell.isEmpty()) {
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

        if (cell.isHole()) {
          break;
        }

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

  @Override
  public HashMap<PlayerKey, Player> getPlayers() {

    HashMap<PlayerKey, Player> copiedMap = new HashMap<>();

    copiedMap.putAll(this.players);

    return copiedMap;
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
        if (!cell.isHole() && !cell.isEmpty() && cell.getCard().getPlayer().equals(player)) {
          count++;
        }
      }
    }

    count += player.getHand().size();
    return count;
  }


}
