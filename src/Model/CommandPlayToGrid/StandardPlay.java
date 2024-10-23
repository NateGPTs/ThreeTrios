package Model.CommandPlayToGrid;

import Model.Cell.Cell;
import java.util.ArrayList;

public class StandardPlay implements GridCommands {

  Cell[][] grid;


  public StandardPlay(Cell[][] grid) {
    this.grid = grid;
  }

  private ArrayList<Cell> getAdjacentCells() {

    ArrayList<Cell> adjacentCells = new ArrayList<>();



  }

  @Override
  public void executePlay(int row, int col) {



  }


}
