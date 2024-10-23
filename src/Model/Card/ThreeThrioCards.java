package Model.Card;

import Model.Card.CardFactory.ThreeThrioCardCreator;
import Model.ModelPlayer.Player;
import java.util.HashMap;

public class ThreeThrioCards implements Card {

  private final HashMap<direction, Integer> AttackVals;
  private Player player;

  public ThreeThrioCards(HashMap<direction, Integer> attackVals, Player player) {

    this.AttackVals = attackVals;
    this.player = player;
  }

  public ThreeThrioCards(Player player) {

    this.AttackVals = new ThreeThrioCardCreator().create();
    this.player = player;
  }

  @Override
  public int getAttackVal(direction direction) {
    return this.AttackVals.get(direction);
  }

  @Override
  public Player getPlayer() {
    return this.player;
  }


}
