package Model.ModelPlayer;

import Model.Card.Card;
import java.awt.Color;
import java.util.ArrayList;

public class ThreeTriosPlayer implements Player {

  private Color playerColor;
  private ArrayList<Card> cards;
  private Boolean isCPU;

  public ThreeTriosPlayer(Color playerColor, ArrayList<Card> cards) {
    this.playerColor = playerColor;
    this.cards = cards;
    this.isCPU = false;

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

    ArrayList<Card> cardCopied = new ArrayList<Card>();

    for (Card c : cards) {

      cardCopied.add(c);

    }

    return cardCopied;
  }

  @Override
  public Card playCard(int cardIndex) {
    return this.cards.remove(cardIndex);
  }

  @Override
  public Boolean isCPU() {
    return this.isCPU;
  }


}
