package model.strategy;

/**
 * An interface representing coordinates with rows and columns in a grid.
 */
public interface Coordinate {

  /**
   * Get the row of the coordinate.
   *
   * @return an integer representing the index of the row.
   */
  int getRow();

  /**
   * Get the column of the coordinate.
   *
   * @return an integer representing the index of the column.
   */
  int getCol();
}
