package Model.Card;

import Model.Card.Card;
import Model.Card.CardFactory.ThreeTrioAttackVal;
import Model.Card.Direction;
import Model.ModelPlayer.Player;
import java.util.HashMap;


/**
 *  Represents a card class specifically for the standard play of the ThreeTriosModel.
 *  Standard here refers to the model and its corresponding rules in the first assignment.
 */
public class ThreeTrioCards implements Card {

  /**
   * This is a crucial field in that, the AttackValues for a corresponding direction is stored
   * here.
   */
  private final HashMap<Direction, Integer> attackVals;

  /**
   * Represents the player who owns this specific card.
   */
  private Player player;

  /**
   * Name of the card, used for the view.
   */
  private final String name;

  /**
   * A card constructor. This card constructor allows you to add your custom attackVals for
   * testing.
   *
   * @param attackVals
   * @param player
   */
  public ThreeTrioCards(String name, HashMap<Direction, Integer> attackVals, Player player) {

    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Name cannot be null or empty.");
    }

    if (attackVals == null || attackVals.isEmpty()) {
      throw new IllegalArgumentException("Attack values cannot be null or empty.");
    }

    this.name = name;
    this.attackVals = attackVals;
    this.player = player;
  }


  /**
   * Constructs card with a name and default attack values.
   *
   * @param name   the name of the card.
   * @param player the player who owns this card.
   */
  public ThreeTrioCards(String name, Player player) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Name cannot be null or empty.");
    }
    this.name = name;
    this.attackVals = new ThreeTrioAttackVal().create();
    this.player = player;
  }

  /**
   * Constructs card with a name and attack values, with no owner.
   *
   * @param name  the name of the card
   * @param given the map of attack values
   */
  public ThreeTrioCards(String name, HashMap<Direction, Integer> given) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Name cannot be null or empty.");
    }
    if (given == null || given.isEmpty()) {
      throw new IllegalArgumentException("Attack values cannot be null or empty.");
    }
    this.name = name;
    this.attackVals = given;
    this.player = null;
  }

  /**
   * // Everything from the interface. But also emphasizing that: This method relies on the
   * AttackVals field. Make sure the AttackVals field is properly initialized, in that, each
   * direction enum key has a corresponding integer value representing the attack value for that
   * direction.
   *
   * @param direction
   * @return an integer representing the attack value for that particular direction.
   */
  @Override
  public int getAttackVal(Direction direction) {
    return this.attackVals.get(direction);
  }


  @Override
  public Player getPlayer() {
    return this.player;
  }

  @Override
  public void setPlayer(Player player) {
    this.player = player;
  }


  public String getName() {
    return this.name;
  }

}