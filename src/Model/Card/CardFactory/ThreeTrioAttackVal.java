package Model.Card.CardFactory;

import Model.Card.Direction;
import java.util.HashMap;
import java.util.Random;


public class ThreeTrioAttackVal implements CreateAttackVals {

  Random rand;

  public ThreeTrioAttackVal(Random rand) {
    this.rand = rand;
  }

  public ThreeTrioAttackVal() {
    this.rand = new Random();
  }

  @Override
  public HashMap<Direction, Integer> create() {

    HashMap<Direction, Integer> attackVal = new HashMap<Direction, Integer>();

    for(Direction d : Direction.values()) {
      attackVal.put(d, this.rand.nextInt(9) + 1);
    }

    return attackVal;
  }
}
