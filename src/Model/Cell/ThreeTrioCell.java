package Model.Cell;

import Model.Card.Card;
import Model.Card.Direction;
import Model.ModelPlayer.Player;
import java.util.HashMap;

public class ThreeTrioCell implements Cell {

  private Boolean hole;
  private Card card;

  @Override
  public boolean isHole() {
    return this.hole;
  }

  @Override
  public boolean isEmpty() {
    return (this.card == null);
  }

  @Override
  public void changeCardPlayer(Player player) {

    if(this.isEmpty()) {
      throw new IllegalArgumentException("This cell is empty");
    }

    this.card.changePlayer(player);
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
    return this.card;
  }

}
