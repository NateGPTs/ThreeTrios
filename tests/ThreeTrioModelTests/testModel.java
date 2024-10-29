package ThreeTrioModelTests;

import Model.Card.Card;
import Model.Cell.Cell;
import Model.ModelUtils;
import Model.StandardThreeTrios;
import Model.ThreeTriosModel;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class testModel {

  ThreeTriosModel model;
  ThreeTriosModel model2;
  ModelUtils utils;
  Cell[][] fourByFourBoard;
  Cell[][] fiveByFiveBoardWithHoles;
  ArrayList<Card> cardDeck;


  @Before
  public void setUp() throws Exception {

    utils = new ModelUtils();
    fourByFourBoard = utils.createBoard("FourByFourBoard");
    cardDeck = utils.createDeck("16CardDeck");
    model = new StandardThreeTrios(fourByFourBoard);
    fiveByFiveBoardWithHoles = utils.createBoard("FiveByFiveBoardWithHoles");

  }

  @Test
  public void testNotEnoughCards() throws Exception {

    Assert.assertThrows(IllegalStateException.class, () -> {

      model2 = new StandardThreeTrios(this.fiveByFiveBoardWithHoles);
      model2.startGame(this.cardDeck);

    });

  }


}
