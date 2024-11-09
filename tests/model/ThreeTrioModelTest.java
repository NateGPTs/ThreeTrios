package model;

import model.card.Card;
import model.cell.Cell;
import model.player.Player;
import model.StandardThreeTrios.PlayerKey;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Represents a ThreeTrioModelTesting class.
 */
public class ThreeTrioModelTest {

  private ThreeTriosModel model;
  private ThreeTriosModel model2;
  private ThreeTriosModel model3;
  private Cell[][] fourByFourBoard;
  private Cell[][] fiveByFiveBoardWithHoles;
  private ArrayList<Card> cardDeck;
  private ArrayList<Card> cardDeck2;

  /**
   * Sets up the initial state of the game.
   *
   * @throws Exception if needed.
   */
  @Before
  public void setUp() throws Exception {

    ModelUtils utils = new ModelUtils();
    fourByFourBoard = utils.createBoard("FourByFourBoard");
    cardDeck = utils.createDeck("16CardDeck");
    cardDeck2 = utils.createDeck("22CardDeck");
    model = new StandardThreeTrios(fourByFourBoard);
    fiveByFiveBoardWithHoles = utils.createBoard("FiveByFiveBoardWithHoles");
    model3 = new StandardThreeTrios(fiveByFiveBoardWithHoles);

  }

  /**
   * Test the model with not enough cards.
   *
   * @throws Exception if needed.
   */
  @Test
  public void testNotEnoughCards() throws Exception {

    Assert.assertThrows(IllegalStateException.class, () -> {

      model2 = new StandardThreeTrios(this.fiveByFiveBoardWithHoles);
      model2.startGame(this.cardDeck);

    });

  }

  /**
   * Test play to grid, if it changes the board and hand.
   *
   * @throws Exception if needed.
   */
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

  /**
   * Test the model giving cards to players when the deck is bigger than amount of Open cells in the
   * board.
   *
   * @throws Exception if needed.
   */
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

  /**
   * Test the game all the way to over.
   *
   * @throws Exception when needed.
   */
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


  /**
   * Test playing to invalid cells.
   *
   * @throws Exception when needed.
   */
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

    Assert.assertThrows(IllegalArgumentException.class, () -> {

      this.model.playToGrid(-1, -1, -1);

    });

    Assert.assertThrows(IllegalArgumentException.class, () -> {

      this.model.playToGrid(-1, 0, 0);

    });

    Assert.assertThrows(IllegalArgumentException.class, () -> {

      this.model.playToGrid(0, -1, -1);

    });

    Assert.assertThrows(IllegalArgumentException.class, () -> {

      this.model.playToGrid(0, 0, -1);

    });

  }

  /**
   * Test how the model infects cells.
   *
   * @throws Exception when needed.
   */
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

  /**
   * Test that get grid actually returns a valid grid.
   *
   * @throws Exception when needed.
   */
  @Test
  public void getGridReturnsAGrid() throws Exception {

    this.model3.startGame(this.cardDeck2);
    Cell[][] grid = model3.getGrid();

    Assert.assertNotNull(grid[0][0]);
    Assert.assertNotNull(grid[0][4]);
    Assert.assertNotNull(grid[4][0]);
    Assert.assertNotNull(grid[4][4]);

    Assert.assertFalse(this.fiveByFiveBoardWithHoles[0][0].isHole());
    Assert.assertFalse(grid[0][0].isHole());
    Assert.assertTrue(grid[0][4].isHole());

  }

  /**
   * Test the grid returned is a copy and doesnt mutate the actual grid.
   *
   * @throws Exception when needed.
   */
  @Test
  public void getGridDoesNotReferenceOriginalGrid() throws Exception {

    this.model3.startGame(this.cardDeck2);
    Cell[][] grid = this.model3.getGrid();
    Card testCard = model3.getPlayers().get(PlayerKey.TWO).getHand().get(0);
    grid[0][0].addCard(testCard);

    Assert.assertThrows(IllegalArgumentException.class, () -> {

      fiveByFiveBoardWithHoles[0][0].getCard();

    });

  }

  /**
   * Test WhoWon.
   *
   * @throws Exception when needed.
   */
  @Test
  public void whoWon() throws Exception {

    model2 = new StandardThreeTrios(this.fiveByFiveBoardWithHoles);
    model2.startGame(this.cardDeck2);

    Assert.assertThrows(IllegalStateException.class, () -> {

      model2.getWinner();

    });

  }

  /**
   * Test starting a game when it already started.
   *
   * @throws Exception when needed.
   */
  @Test
  public void startGameWhenAlreadyStarted() throws Exception {

    model2 = new StandardThreeTrios(this.fiveByFiveBoardWithHoles);
    model2.startGame(this.cardDeck2);

    Assert.assertThrows(IllegalStateException.class, () -> {

      model2.startGame(this.cardDeck2);

    });

  }


}
