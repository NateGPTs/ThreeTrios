package Model;

import Model.CommandPlayToGrid.StandardPlay;
import Model.Cell.Cell;
import Model.ModelPlayer.Player;
import java.util.HashMap;

public class StandardThreeTrios implements ThreeTriosModel {

  public enum state {

    notStarted, Over, Ongoing

  }

  public enum playerKey {

    ONE, TWO

  }

  private state gameState;
  private final Cell[][] grid;
  private Player whoseTurn;
  private HashMap<playerKey, Player> players;

  //Invariant: Grid cannot be null.
  public StandardThreeTrios(Cell[][] grid) {

    if(grid == null) {
      throw new IllegalArgumentException("Grid cannot be null.");
    }

    this.gameState = state.notStarted;
    this.grid = grid;
  }

  @Override
  public void playToGrid(int row, int col, int handIndex) {

    if(this.gameState != state.Ongoing) {
      throw new IllegalStateException("Game is over or not started");
    }

    new StandardPlay(this.grid, this.whoseTurn).executePlay(row, col, handIndex);

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

    for(Cell[] row : this.grid) {



    }

    return false;
  }

  @Override
  public void startGame() {

    if(this.gameState != state.notStarted) {
      throw new IllegalStateException("Game has already started or is over");
    }


  }

  @Override
  public Player getWinner() {

    if(this.gameState != state.Over) {
      throw new IllegalStateException("Game is  not over");
    }

    return null;
  }




}
