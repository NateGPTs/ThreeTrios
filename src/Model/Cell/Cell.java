package Model.Cell;

import Model.Card.Card;
import Model.Card.Direction;
import Model.ModelPlayer.Player;

public interface Cell {

  boolean isHole();

  boolean isEmpty();


  void addCard(Card given);

  Card getCard();

  Player whoOwns();

  int getRow();

  int getCol();

  void setPlayer(Player player);
}
