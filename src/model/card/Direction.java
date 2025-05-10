package model.card;

/**
 * Represents : "The four attack values correspond to the four sides of the card." (Bad grammar
 * above, but the gist is that the cards have four directions, and a attack value for each
 * direction). The direction enum represents each of those 4 directions.
 */
public enum Direction {

  NORTH, EAST, SOUTH, WEST;

  /**
   * Method that returns the opposite direction of a direction enum.
   *
   * @return The opposing direction.
   */
  public Direction getOpposite() {

    switch (this) {

      case NORTH:
        return SOUTH;
      case EAST:
        return WEST;
      case SOUTH:
        return NORTH;
      case WEST:
        return EAST;
      default:
        throw new IllegalArgumentException("Invalid direction");
    }

  }

}
