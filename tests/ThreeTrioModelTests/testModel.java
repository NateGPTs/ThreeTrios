package ThreeTrioModelTests;

import Model.Card.Card;
import Model.Cell.Cell;
import Model.ModelPlayer.Player;
import Model.ModelUtils;
import Model.StandardThreeTrios;
import Model.StandardThreeTrios.PlayerKey;
import Model.ThreeTriosModel;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class testModel {

  private ThreeTriosModel model;
  private ThreeTriosModel model2;
  private ThreeTriosModel model3;
  private ModelUtils utils;
  private Cell[][] fourByFourBoard;
  private Cell[][] fiveByFiveBoardWithHoles;
  private ArrayList<Card> cardDeck;
  private ArrayList<Card> cardDeck2;


  @Before
  public void setUp() throws Exception {

    utils = new ModelUtils();
    fourByFourBoard = utils.createBoard("FourByFourBoard");
    cardDeck = utils.createDeck("16CardDeck");
    cardDeck2 = utils.createDeck("22CardDeck");
    model = new StandardThreeTrios(fourByFourBoard);
    fiveByFiveBoardWithHoles = utils.createBoard("FiveByFiveBoardWithHoles");
    model3 = new StandardThreeTrios(fiveByFiveBoardWithHoles);

  }

  @Test
  public void testNotEnoughCards() throws Exception {

    Assert.assertThrows(IllegalStateException.class, () -> {

      model2 = new StandardThreeTrios(this.fiveByFiveBoardWithHoles);
      model2.startGame(this.cardDeck);

    });

  }

  @Test
  public void testPlayToGridChangesBoardAndHand() throws Exception {

    this.model.startGame(this.cardDeck);

    Player playerOne = this.model.getPlayers().get(PlayerKey.ONE);

    Player playerTwo = this.model.getPlayers().get(PlayerKey.TWO);

    Card playedCard = playerOne.getHand().get(0);

    this.model.playToGrid(0, 0, 0);

    // Check the correct card is on [0][0]
    Assert.assertEquals(playedCard, this.fourByFourBoard[0][0].getCard());

    // Test the playedCard is no longer in playerOnes hand.
    Assert.assertNotEquals(playedCard, playerOne.getHand().get(0));

    // Check the player turn switched to playerTwo
    Assert.assertEquals(playerTwo, this.model.currentPlayer());

  }


  @Test
  public void testHandAmountWithBiggerDeck() throws Exception {

    this.model.startGame(this.cardDeck2);

    Player playerOne = this.model.getPlayers().get(PlayerKey.ONE);

    Player playerTwo = this.model.getPlayers().get(PlayerKey.TWO);

    ArrayList<Card> playerOneHand = playerOne.getHand();

    ArrayList<Card> playerTwoHand = playerTwo.getHand();

    int actual = playerOneHand.size() + playerTwoHand.size();

    // Ensure the right amount of cards for each player.
    Assert.assertEquals(8, playerOneHand.size());

    // Ensure the right amount of cards for each player.
    Assert.assertEquals(8, playerTwoHand.size());

    // 16 because 1 more than the boards number of open cells.
    Assert.assertEquals(16, actual);

  }

  @Test
  public void testGameToOver() throws Exception {

    this.model.startGame(this.cardDeck2);

    this.model.playToGrid(0, 0, 0);
    this.model.playToGrid(0, 1, 0);
    this.model.playToGrid(0, 2, 0);
    this.model.playToGrid(1, 0, 0);
    this.model.playToGrid(1, 1, 0);
    this.model.playToGrid(1, 2, 0);
    this.model.playToGrid(1, 3, 0);
    this.model.playToGrid(2, 0, 0);
    this.model.playToGrid(2, 1, 0);
    this.model.playToGrid(2, 2, 0);
    this.model.playToGrid(2, 3, 0);
    this.model.playToGrid(3, 0, 0);
    this.model.playToGrid(3, 1, 0);
    this.model.playToGrid(3, 2, 0);
    this.model.playToGrid(3, 3, 0);

    Assert.assertTrue(this.model.isGameOver());

    Player playerTwo = this.model.getPlayers().get(PlayerKey.TWO);

    // Testing the winner is correct
    Assert.assertEquals(playerTwo, this.model.getWinner());

  }



  @Test
  public void playToInvalidCells() throws Exception {

    this.model.startGame(this.cardDeck2);


    Assert.assertThrows(IllegalArgumentException.class, () -> {

      this.model.playToGrid(0, 3, 0);

    });

    Assert.assertThrows(IllegalArgumentException.class, () -> {

      this.model.playToGrid(0, 2, 0);
      this.model.playToGrid(0, 2, 0);

    });

  }

  @Test
  public void playToGridInfections() throws Exception {

    this.model3.startGame(this.cardDeck2);

    Player playerOne = this.model3.getPlayers().get(PlayerKey.ONE);

    Player playerTwo = this.model3.getPlayers().get(PlayerKey.TWO);

    this.model3.playToGrid(1, 2, 0);

    this.model3.playToGrid(2, 1, 0);

    // Testing cards cant infect each other diagonally.
    Assert.assertEquals(playerOne, fiveByFiveBoardWithHoles[1][2].getCard().getPlayer());
    Assert.assertEquals(playerTwo, fiveByFiveBoardWithHoles[2][1].getCard().getPlayer());
  }


}
