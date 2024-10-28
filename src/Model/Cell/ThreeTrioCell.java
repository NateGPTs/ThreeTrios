package Model.Cell;

import Model.Card.Card;
import Model.Card.Direction;
import Model.ModelPlayer.Player;
import java.util.HashMap;

public class ThreeTrioCell implements Cell {

  private Boolean hole;
  private Card card;
  private final int row;
  private final int col;

  public ThreeTrioCell(Boolean isHole, int row, int col) {
    this.hole = isHole;
    this.row = row;
    this.col = col;
  }

  public ThreeTrioCell(int row, int col) {
    this.card = null;
    this.hole = false;
    this.row = row;
    this.col = col;
  }

  public ThreeTrioCell(Boolean isHole, int row, int col, Card card) {
    this.card = card;
    this.hole = isHole;
    this.row = row;
    this.col = col;
  }

  @Override
  public boolean isHole() {
    return this.hole;
  }

  @Override
  public boolean isEmpty() {

    if(this.isHole()) {
      throw new IllegalArgumentException("This card is a hole.");
    }

    return (this.card == null);
  }


  @Override
  public void addCard(Card given) {

    if(!this.isEmpty()) {
      throw new IllegalArgumentException("This cell already has a card");
    }

    this.card = given;
  }

  @Override
  public Card getCard() {

    if(this.card == null || this.hole) {
      throw new IllegalArgumentException("This cell is empty. Or is a hole.");
    }

    return this.card;
  }

  @Override
  public Player whoOwns() {

    return this.card.getPlayer();
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
