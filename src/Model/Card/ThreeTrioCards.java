package Model.Card;

import Model.Card.CardFactory.ThreeTrioAttackVal;
import Model.ModelPlayer.Player;
import java.util.HashMap;


/**
 *  Represents a card class specifically for the standard play of the ThreeTriosModel.
 *  Standard here refers to the model and its corresponding rules in the first assignment.
 */
public class ThreeTrioCards implements Card {

  /**
   * This is a crucial field in that, the AttackValues for a corresponding
   * direction is stored here.
   */
  private final HashMap<Direction, Integer> AttackVals;

  /**
   * Represents the player who owns this specific card.
   */
  private Player player;

  /**
   * A card constructor. This card constructor allows you to add your custom attackVals
   * for testing.
   *
   * @param attackVals
   * @param player
   */
  public ThreeTrioCards(HashMap<Direction, Integer> attackVals, Player player) {

    this.AttackVals = attackVals;
    this.player = player;
  }


  public ThreeTrioCards(Player player) {

    this.AttackVals = new ThreeTrioAttackVal().create();
    this.player = player;
  }

  /**
   * // Everything from the interface.
   * But also emphasizing that: This method relies on the AttackVals field.
   * Make sure the AttackVals field is properly initialized, in that,
   * each direction enum key has a corresponding integer value representing the attack
   * value for that direction.
   *
   * @param direction
   * @return an integer representing the attack value for that particular direction.
   */
  @Override
  public int getAttackVal(Direction direction) {
    return this.AttackVals.get(direction);
  }


  @Override
  public Player getPlayer() {
    return this.player;
  }

  @Override
  public void changePlayer(Player player) {

    if(this.player == player) {
      throw new IllegalArgumentException("Given player already owns the card");
    }



    this.player = player;
  }


}
