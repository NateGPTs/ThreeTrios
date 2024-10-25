package Model.ModelPlayer;

import Model.Card.Card;
import java.awt.Color;
import java.util.ArrayList;

public class ThreeTriosPlayer implements Player {

  Color playerColor;
  ArrayList<Card> cards;

  @Override
  public Color getColor() {
    return this.playerColor;
  }

  @Override
  public ArrayList<Card> getHand() {
    return this.cards;
  }

  @Override
  public boolean cardExists(int cardIndex) {

    if(this.cards.size() - 1 < cardIndex) {
      return false;

    } else {
      return true;
    }
  }
}
