package model.Strategy;

import model.card.Direction;

public class Vector extends Coord implements Vectors {

  Direction direction;

  public Vector(int row, int col, Direction direction) {
    super(row, col);
    this.direction = direction;
  }

  @Override
  public Direction getDirection() {
    return null;
  }
}
