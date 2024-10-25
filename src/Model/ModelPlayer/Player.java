package Model.ModelPlayer;

import Model.Card.Card;
import java.awt.Color;
import java.util.ArrayList;

public interface Player {

  public Color getColor();

  public ArrayList<Card> getHand();

  public boolean cardExists(int cardIndex);
}
