package model;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import model.Strategy.Coordinate;
import model.Strategy.Corners;
import model.Strategy.MostFlips;
import model.Strategy.ReturnBestMove;
import model.Strategy.StandardBestMove;
import model.Strategy.ThreeTriosStrategy;
import model.card.Card;
import model.card.Direction;
import model.cell.Cell;
import model.cell.ThreeTrioCell;
import model.grid.GridCommands;
import model.grid.StandardPlay;
import model.player.Player;
import model.player.ThreeTriosPlayer;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a standard Three Trios game. Manages the state, grid, players, and reenforces the
 * rules of the game/game progress.
 */
public class StandardThreeTrios implements ThreeTriosModel {

  private final Cell[][] grid;
  private final Map<Direction, BiFunction<Integer, Integer, Cell>> DirectionalValues;
  private final Map<PlayerKey, Player> players;
  private final GridCommands gridCommands;
  protected List<ThreeTriosStrategy> strategies;
  private GameState gameState;
  private Player whoseTurn;

  /**
   * Constructs a game model using the given grid.
   */
  public StandardThreeTrios(Cell[][] grid) {

    if (grid == null) {
      throw new IllegalArgumentException("Grid is null");
    }

    Map<PlayerKey, Player> players = new HashMap<>();
    this.players = players;
    this.gameState = GameState.NOT_STARTED;
    this.grid = grid;
    this.gridCommands = new StandardPlay(grid);
    DirectionalValues = new HashMap<>();
    DirectionalValues.put(Direction.EAST, (r, c) -> this.grid[r][c + 1]);
    DirectionalValues.put(Direction.WEST, (r, c) -> this.grid[r][c - 1]);
    DirectionalValues.put(Direction.NORTH, (r, c) -> this.grid[r - 1][c]);
    DirectionalValues.put(Direction.SOUTH, (r, c) -> this.grid[r + 1][c]);
    List<ThreeTriosStrategy> strategies = new ArrayList<>();
    this.strategies =  strategies;
    this.strategies.add(new MostFlips());
    this.strategies.add(new Corners());
  }

  public StandardThreeTrios(Cell[][] grid, List<ThreeTriosStrategy> strategies) {

    this(grid);
    this.strategies = strategies;
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

    if (((getNumOfOpenGridCells(grid)) % 2) != 1) {
      throw new IllegalArgumentException("Number of non hole grid cells must be odd");
    }

  }

  @Override
  public void startGame(List<Card> deck) {

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

    if(!this.currentPlayer().isCPU()) {
      throw new IllegalStateException("Player is not CPU");
    } else {

      ReturnBestMove cpuMove = new StandardBestMove(this.strategies);
      Map<String, Integer> move = cpuMove.getBestMove(this, this.currentPlayer());
      playToGrid(move.get("row"), move.get("col"), move.get("index"));

    }
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
   * Checks if the grid is full of occupied cards. Considered full if each cell contains a card.
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
   * Changes the turn between player one and two. The method switches to the other player after a
   * turn is made.
   */
  private void switchPlayerTurn() {
    this.whoseTurn = (this.whoseTurn == players.get(PlayerKey.ONE)) ?
        players.get(PlayerKey.TWO) : players.get(PlayerKey.ONE);
  }

  @Override
  public Cell[][] getGrid() {

    Cell[][] copiedArray = new ThreeTrioCell[this.grid.length][this.grid[0].length];

    for (int row = 0; row < this.grid.length; row++) {
      for (int col = 0; col < this.grid[row].length; col++) {
        copiedArray[row][col] = new ThreeTrioCell(this.grid[row][col]);
      }
    }

    return copiedArray;
  }

  @Override
  public Player currentPlayer() {
    return this.whoseTurn;
  }

  @Override
  public int gridWidth() {
    return this.grid.length;
  }

  @Override
  public int gridHeight() {
    return this.grid[0].length;
  }

  @Override
  public Cell getCell(Coordinate coord) {
    return grid[coord.getRow()][coord.getCol()];
  }

  @Override
  public Player whoOwns(int row, int col) {
    return grid[row][col].getOwner();
  }

  @Override
  public int flipCount(Card given, int row, int col, Player player) {
    return this.gridCommands.countPotentialFlips(row, col, given, player);
  }

  @Override
  public int playerScore(Player given) {
    return countCards(given);
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
  public Map<Direction, Cell> getAdjacentCells(Coordinate coord, Predicate<Cell> cellPredicate) {
    Cell getCell = grid[coord.getRow()][coord.getCol()];
    Map<Direction, Cell> adjacentCells = this.gridCommands.getAdjacentCells(getCell, cellPredicate);
    Map<Direction, Cell> deepCopiedCells = new HashMap<>();
    ModelUtils util = new ModelUtils();

    for (Map.Entry<Direction, Cell> entry : adjacentCells.entrySet()) {
      deepCopiedCells.put(entry.getKey(), util.deepCopyCell(entry.getValue()));
    }

    return deepCopiedCells;
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
   * Counts total number of cards owned by a player in their hand and on the grid.
   *
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


}
