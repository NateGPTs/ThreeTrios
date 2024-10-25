package Model.Card.CardFactory;

import Model.Card.Direction;
import java.util.HashMap;

/**
 * The following interface is for ThreeTrio attack val creator classes which creates a
 * HashMap with a Direction key to an int value.
 */
public interface CreateAttackVals {

  /**
   * Creates a HashMap representing a attack value for 4 directions.
   * @return HashMap<Direction, Integer>
   */
  HashMap<Direction, Integer> create();

}
