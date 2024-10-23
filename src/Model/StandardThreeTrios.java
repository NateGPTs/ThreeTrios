package Model;

import Model.CommandPlayToGrid.StandardPlay;
import Model.Cell.Cell;
import Model.ModelPlayer.Player;

public class StandardThreeTrios implements ThreeTriosModel {

  public enum state {

    notStarted, Over, Ongoing

  }

  private state gameState;
  private final Cell[][] grid;
  private Player whoseTurn;


  public StandardThreeTrios(Cell[][] grid) {
    this.gameState = state.notStarted;
    this.grid = grid;
  }

  @Override
  public void playToGrid(int row, int col) {

    if(this.gameState != state.Ongoing) {
      throw new IllegalStateException("Game is over or not started");
    }

    if((row < 0 || row >= this.grid.length) || (col < 0 || col >= this.grid[row].length) ) {
      throw new IllegalArgumentException("Not a valid placement");
    }

    new StandardPlay(this.grid).executePlay(row, col);

  }

  @Override
  public Cell[][] getGrid() {


  }

  @Override
  public Player currentPlayer() {
    return this.whoseTurn;
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public void StartGame() {

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

  private void enforceEndGame() {


  }




}
