package model.cell;

import java.awt.Color;
import java.util.Map;
import model.card.Card;
import model.card.Direction;
import model.card.ThreeTrioCards;
import model.player.Player;

/**
 * Represents a cell in the Three Trios game. A cell may be designated as a hole or contain a card,
 * and has row and column coordinates in the grid.
 */
public class ThreeTrioCell implements Cell {

  private final Boolean hole;
  private final int row;
  private final int col;
  private Card card;

  /**
   * Constructs a ThreeTrioCell and specifies whether it can be a hole and it's position on the
   * grid.
   *
   * @param isHole true if this cell should be designated as a hole; false otherwise.
   * @param row    the row index of the cell
   * @param col    the column index of the cell
   */
  public ThreeTrioCell(Boolean isHole, int row, int col) {

    if (isHole == null) {
      throw new IllegalArgumentException("Hole cant be null");
    }

    this.hole = isHole;
    this.row = row;
    this.col = col;
  }

  /**
   * Constructs an empty ThreeTrioCell that is not a hole but has a position in the grid.
   *
   * @param row the row index of the cell.
   * @param col the column index of the cell.
   */
  public ThreeTrioCell(int row, int col) {
    this.card = null;
    this.hole = false;
    this.row = row;
    this.col = col;
  }

  /**
   * Represents a constructor that copies a given Cell.
   *
   * @param cell to copy.
   */
  public ThreeTrioCell(Cell cell) {

    if (cell == null) {
      throw new IllegalArgumentException("Cell cannot be null");
    }

    this.hole = cell.isHole();
    this.card = copyCard(cell);
    this.row = cell.getRow();
    this.col = cell.getCol();
  }

  /**
   * Represents a constructor that copies a given Cell. And sets its card to the given card.
   *
   * @param cell to copy.
   */
  public ThreeTrioCell(Cell cell, Card card) {

    this(cell);
    this.card = card;

  }

  private Card copyCard(Cell cell) {

    if (cell.getCard() == null) {
      return null;
    } else {
      return new ThreeTrioCards(cell.getCard());
    }
  }

  private Boolean getHole() {

    return this.hole;
  }


  @Override
  public boolean isHole() {
    return this.hole;
  }

  @Override
  public boolean isEmpty() {

    if (this.hole) {
      throw new IllegalArgumentException("This card is a hole.");
    }

    return (this.card == null);
  }


  @Override
  public void addCard(Card given) {

    if (!this.isEmpty()) {
      throw new IllegalArgumentException("This cell already has a card");
    }

    this.card = given;
  }

  @Override
  public Card getCard() {

    return this.card;
  }


  @Override
  public Boolean checkOwnership(Player player) {

    return (this.card.getPlayer().equals(player));
  }

  @Override
  public Player getOwner() {

    return this.card.getPlayer();
  }

  @Override
  public Map<Direction, Integer> getAllAttackVals() {
    return this.card.getAllAttackVals();
  }

  @Override
  public Color getColor() {
    return this.card.getColor();
  }

  @Override
  public int getRow() {
    return this.row;
  }

  @Override
  public int getCol() {
    return this.col;
  }

  @Override
  public void setPlayer(Player player) {
    this.card.setPlayer(player);
  }

}
