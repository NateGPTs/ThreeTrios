package Model.ModelPlayer;

import Model.Card.Card;
import java.awt.Color;
import java.util.ArrayList;

public interface Player {

  public Color getColor();

  public ArrayList<Card> getHand();


  /**
   * This method removes the card at the players Index, and returns it.
   *
   * @param cardIndex represents the card you want to remove and return.
   * @return Card
   */
  public Card playCard(int cardIndex);


  public Boolean isCPU();
}
