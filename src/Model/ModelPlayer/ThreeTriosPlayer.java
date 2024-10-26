package Model.ModelPlayer;

import Model.Card.Card;
import java.awt.Color;
import java.util.ArrayList;

public class ThreeTriosPlayer implements Player {

  Color playerColor;
  ArrayList<Card> cards;

  public ThreeTriosPlayer(Color playerColor, ArrayList<Card> cards) {
    this.playerColor = playerColor;
    this.cards = cards;

    for(Card c : cards) {

      c.setPlayer(this);

    }
  }

  @Override
  public Color getColor() {
    return this.playerColor;
  }

  @Override
  public ArrayList<Card> getHand() {
    return this.cards;
  }

  @Override
  public Card playCard(int cardIndex) {
    return this.cards.remove(cardIndex);
  }


}
