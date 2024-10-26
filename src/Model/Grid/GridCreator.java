package Model.Grid;

import Model.Cell.Cell;
import Model.Cell.ThreeTrioCell;

public class GridCreator {

  public Cell[] [] create(int row, int col) {

    if(((row * col) % 2) != 1) {
      throw new IllegalArgumentException("number of cells must be ODD");
    }

    Cell[][] grid = new Cell[row][col];

    for(int rows = 0; rows < row; rows++) {

      for(int columns = 0; columns < col; columns++) {

       grid[rows][columns] = new ThreeTrioCell(rows, columns);

      }
    }

    return grid;
  }

}
