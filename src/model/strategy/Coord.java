package model.strategy;

/**
 * Represents a coordinate: which is a row and column integer.
 */
public class Coord implements Coordinate {

  private final int row;
  private final int col;

  /**
   * Constructs the coordinate based on a row and column given.
   *
   * @param row integer representing the index of the row.
   * @param col integer representing the index of the column.
   */
  public Coord(int row, int col) {

    this.row = row;
    this.col = col;
  }

  /**
   * Get the row of the coordinate.
   *
   * @return an integer representing the index of the row.
   */
  public int getRow() {

    return this.row;
  }

  /**
   * Get the column of the coordinate.
   *
   * @return an integer representing the index of the column.
   */
  public int getCol() {

    return this.col;
  }

}
