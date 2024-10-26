package Model.Card;
import Model.Cell.Cell;
import Model.ModelPlayer.Player;

/**
 *  Represents a card Interface for the ThreeTrios model.
 *  So different models can create card classes implementing it,
 *  and have it compatible, as long as it implements the following methods.
 */
public interface Card {

  /**
   *  To quote the Assignment specifications:
   *  "The four attack values correspond to the four sides of the card.
   *  We will use compass directions to refer to each side (North, South, East, and West)."
   *  The direction enum will be used to get the AttackValue for the direction.
   *  "Attack values in this game are the integers between 1-9 and the letter A."
   *  Inputting the specific direction enum, will give the attack value for that direction.
   *
   * @return an integer representing the attack value for that card, an integer between 1-9.
   */
  int getAttackVal(Direction direction);

  /**
   * This is a getter method that returns the player owning the current card.
   *
   * @return the player who owns the card.
   */
  Player getPlayer();


  void setPlayer(Player player);

}
