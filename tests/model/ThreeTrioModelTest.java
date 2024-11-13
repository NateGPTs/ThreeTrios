package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import model.Strategy.Coord;
import model.Strategy.Coordinate;
import model.Strategy.Corners;
import model.Strategy.MostFlips;
import model.Strategy.ThreeTriosStrategy;
import model.card.Card;
import model.card.Direction;
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
  private List<Card> cardDeck;
  private List<Card> cardDeck2;
  private List<Card> testDeck;

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
    testDeck = utils.createDeck("testCardDeck");

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
    assertEquals(playedCard, this.fourByFourBoard[0][0].getCard());

    // Test the playedCard is no longer in playerOnes hand.
    Assert.assertNotEquals(playedCard, playerOne.getHand().get(0));

    // Check the player turn switched to playerTwo
    assertEquals(playerTwo, this.model.currentPlayer());

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

    List<Card> playerOneHand = playerOne.getHand();

    List<Card> playerTwoHand = playerTwo.getHand();

    int actual = playerOneHand.size() + playerTwoHand.size();

    // Ensure the right amount of cards for each player.
    assertEquals(8, playerOneHand.size());

    // Ensure the right amount of cards for each player.
    assertEquals(8, playerTwoHand.size());

    // 16 because 1 more than the boards number of open cells.
    assertEquals(16, actual);

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

    assertTrue(this.model.isGameOver());

    Player playerTwo = this.model.getPlayers().get(PlayerKey.TWO);

    // Testing the winner is correct
    assertEquals(playerTwo, this.model.getWinner());

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
    assertEquals(playerOne, fiveByFiveBoardWithHoles[1][2].getCard().getPlayer());
    assertEquals(playerTwo, fiveByFiveBoardWithHoles[2][1].getCard().getPlayer());
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

    assertNotNull(grid[0][0]);
    assertNotNull(grid[0][4]);
    assertNotNull(grid[4][0]);
    assertNotNull(grid[4][4]);

    assertFalse(this.fiveByFiveBoardWithHoles[0][0].isHole());
    assertFalse(grid[0][0].isHole());
    assertTrue(grid[0][4].isHole());

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
    Cell[][] grid2 = this.model3.getGrid();

    assertNull(grid2[0][0].getCard());

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

  @Test
  public void debugMostFlipsStrategy() {
    ThreeTriosStrategy mostFlips = new MostFlips();
    this.model3.startGame(this.cardDeck2);
    Player player1 = this.model3.getPlayers().get(PlayerKey.ONE);
    Player player2 = this.model3.getPlayers().get(PlayerKey.TWO);

    // Print initial state
    System.out.println("Player 1 hand size: " + player1.getHand().size());
    for (Card card : player1.getHand()) {
      System.out.println("Card in hand: " +
          card.getAttackVal(Direction.NORTH) + "," +
          card.getAttackVal(Direction.SOUTH) + "," +
          card.getAttackVal(Direction.EAST) + "," +
          card.getAttackVal(Direction.WEST));
    }

    // Add weak card
    Card weakestCard = this.testDeck.get(1);
    weakestCard.setPlayer(player2);
    this.fiveByFiveBoardWithHoles[3][0].addCard(weakestCard);

    List<Map<String, Integer>> moves = mostFlips.chooseMove(this.model3, player1);

    // Print all moves found
    System.out.println("\nAll moves found:");
    for (Map<String, Integer> move : moves) {
      System.out.printf("Hand: %d, Row: %d, Col: %d%n",
          move.get("index"), move.get("row"), move.get("col"));
    }
  }

  @Test
  public void mostFlipsStrategy() {

    ThreeTriosStrategy mostFlips = new MostFlips();
    this.model3.startGame(this.cardDeck2);
    Player player1 = this.model3.getPlayers().get(PlayerKey.ONE);
    Player player2 = this.model3.getPlayers().get(PlayerKey.TWO);
    Card card1 = this.testDeck.get(21);
    card1.setPlayer(player2);
    this.fiveByFiveBoardWithHoles[0][0].addCard(card1);
    List<Map<String, Integer>> moves = mostFlips.chooseMove(this.model3, player1);

    // Card 1 has 9 9 9 9 for attack value.
    assertEquals(0, moves.size());

    // Card with attack vals of 1 1 1 1
    Card weakestCard = this.testDeck.get(1);
    weakestCard.setPlayer(player2);
    this.fiveByFiveBoardWithHoles[3][0].addCard(weakestCard);
    List<Map<String, Integer>> moves2 = mostFlips.chooseMove(this.model3, player1);
    assertEquals(33, moves2.size());

  }

  /**
   * Tests the grid dimension methods (gridWidth and gridHeight) to ensure they return
   * correct dimensions for different board sizes.
   */
  @Test
  public void testGridDimensions() {
    assertEquals("Wrong grid width", 4, model.gridWidth());
    assertEquals("Wrong grid height", 4, model.gridHeight());

    // Test five by five board
    assertEquals("Wrong grid width for 5x5", 5, model3.gridWidth());
    assertEquals("Wrong grid height for 5x5", 5, model3.gridHeight());
  }

  /**
   * Tests the getCell method to verify it correctly retrieves cells at given coordinates
   * and properly reflects game state changes.
   */
  @Test
  public void testGetCell() {
    model.startGame(cardDeck);

    // Test getting valid cell
    Coordinate coord = new Coord(0, 0);
    Cell cell = model.getCell(coord);
    assertNotNull("Cell should not be null", cell);
    assertTrue("Cell should be empty initially", cell.isEmpty());
    assertFalse("Cell should not be a hole", cell.isHole());

    // Test after playing a card
    model.playToGrid(0, 0, 0);
    cell = model.getCell(coord);
    assertFalse("Cell should not be empty after play", cell.isEmpty());
  }

  /**
   * Tests the whoOwns method to verify cell ownership tracking.
   * Checks both empty cells and cells after card placement.
   */
  @Test
  public void testWhoOwns() {

    model.startGame(cardDeck);

    // Play a card and check ownership
    model.playToGrid(0, 0, 0);
    Player owner = model.whoOwns(0, 0);
    assertNotNull("Cell should have an owner after play", owner);
    assertEquals("Wrong owner", model.getPlayers().get(PlayerKey.ONE), owner);
  }

  /**
   * Tests the flipCount method to verify correct calculation of potential card flips.
   * Uses known card values to test flip mechanics.
   */
  @Test
  public void testFlipCount() {
    model.startGame(testDeck);
    Player player1 = model.getPlayers().get(PlayerKey.ONE);
    Player player2 = model.getPlayers().get(PlayerKey.TWO);

    // Place a weak card (card2 from testDeck has 0,0,0,0)
    Card weakCard = testDeck.get(1);
    weakCard.setPlayer(player2);
    model.playToGrid(1, 1, 0);

    // Try to flip with strong card (card22 from testDeck has 9,9,9,9)
    Card strongCard = testDeck.get(21);
    int flips = model.flipCount(strongCard, 0, 1, player1);

    assertTrue("Strong card should be able to flip weak card", flips > 0);
  }

  /**
   * Tests the playerScore method to verify correct score calculation.
   * Checks scores both before and after card placement.
   */
  @Test
  public void testPlayerScore() {
    model.startGame(cardDeck);
    Player player1 = model.getPlayers().get(PlayerKey.ONE);

    // Initial score should be hand size
    int initialHandSize = player1.getHand().size();
    assertEquals("Initial score should match hand size",
        initialHandSize, model.playerScore(player1));

    // Play a card and check updated score
    model.playToGrid(0, 0, 0);
    assertEquals("Score should remain same after playing card",
        initialHandSize, model.playerScore(player1));
  }

  /**
   * Tests that constructor properly handles null grid parameter.
   * Expects an IllegalArgumentException to be thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullGridConstructor() {
    new StandardThreeTrios(null);
  }

  /**
   * Tests the strategy constructor to ensure proper initialization with custom strategies.
   */
  @Test
  public void testStrategyConstructor() {
    List<ThreeTriosStrategy> strategies = Arrays.asList(
        new MostFlips(),
        new Corners()
    );
    ThreeTriosModel stratModel = new StandardThreeTrios(fourByFourBoard, strategies);

    // Start game and verify model created successfully
    stratModel.startGame(testDeck);
    assertNotNull("Model should be properly initialized", stratModel);
  }




  /**
   * Tests the getAdjacentCells method to verify correct identification of
   * neighboring cells with various predicates.
   */
  @Test
  public void testGetAdjacentCells() {
    model.startGame(testDeck);

    // Test center cell (should have 4 adjacent cells)
    Coordinate centerCoord = new Coord(1, 1);
    Map<Direction, Cell> adjacentCells = model.getAdjacentCells(
        centerCoord,
        cell -> !cell.isHole()
    );

    assertEquals("Center cell should have 4 adjacent cells",
        4, adjacentCells.size());

    // Test corner cell (should have 2 adjacent cells)
    Coordinate cornerCoord = new Coord(0, 0);
    adjacentCells = model.getAdjacentCells(
        cornerCoord,
        cell -> !cell.isHole()
    );

    assertEquals("Corner cell should have 2 adjacent cells",
        2, adjacentCells.size());

    // Test with empty cell predicate
    adjacentCells = model.getAdjacentCells(
        centerCoord,
        Cell::isEmpty
    );

    // All cells should be empty initially
    assertEquals("All adjacent cells should be empty initially",
        4, adjacentCells.size());
  }


}
