package model.Strategy;

public class Coord implements Coordinate {

  private final int row;
  private final int col;

  public Coord(int row, int col) {

    this.row = row;
    this.col = col;
  }

  public int getRow() {

    return this.row;
  }

  public int getCol() {

    return this.col;
  }

}
