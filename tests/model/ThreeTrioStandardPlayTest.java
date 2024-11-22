package model;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import model.card.Card;
import model.card.Direction;
import model.card.ThreeTrioCards;
import model.cell.Cell;
import model.grid.StandardPlay;
import model.player.Player;
import model.player.ThreeTriosPlayer;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the StandardPlay class.
 */
public class ThreeTrioStandardPlayTest {

  private Cell[][] grid;
  private Player player;
  private StandardPlay standardPlay;
  private List<Card> threeTrioCards;

  /**
   * Sets up the grid and player instance for each test. A board is created with a specified
   * configuration, and a player is initialized with a deck of cards.
   */
  @Before
  public void setUp() {
    ModelUtils util = new ModelUtils();
    this.grid = util.createBoard("FourByFourBoard");
    threeTrioCards = new ArrayList<>();
    this.player = new ThreeTriosPlayer(Color.red, threeTrioCards);
    this.standardPlay = new StandardPlay(grid);
  }

  /**
   * Tests that a card can be successfully played on an empty cell within grid bounds. Verifies that
   * the card is placed correctly and that a card clash with neighboring cells is initiated if
   * applicable.
   */
  @Test
  public void testExecutePlayOnEmptyCell() {
    // Add a card to the player's hand with mock attack values
    HashMap<Direction, Integer> attackVals = new HashMap<>();
    attackVals.put(Direction.NORTH, 3);
    attackVals.put(Direction.EAST, 2);
    attackVals.put(Direction.SOUTH, 1);
    attackVals.put(Direction.WEST, 4);
    Card card = new ThreeTrioCards("TestCard", attackVals, player);
    this.threeTrioCards.add(card);

    // Execute play
    standardPlay.executePlay(1, 1, 0, player);

    // Assert the card was placed in the grid
    assertEquals(card, grid[1][1].getCard());
    assertEquals(player, grid[1][1].getCard().getPlayer());
  }

  /**
   * Tests that attempting to place a card outside the grid bounds throws an
   * IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testExecutePlayOutOfBounds() {
    standardPlay.executePlay(-1, 1, 0, player);
  }

  /**
   * Tests that attempting to play to an occupied cell throws an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testExecutePlayOnOccupiedCell() {

    HashMap<Direction, Integer> attackVals = new HashMap<>();
    attackVals.put(Direction.NORTH, 3);
    attackVals.put(Direction.EAST, 2);
    attackVals.put(Direction.SOUTH, 1);
    attackVals.put(Direction.WEST, 4);
    threeTrioCards.add(new ThreeTrioCards("TestCard", attackVals, player));
    threeTrioCards.add(new ThreeTrioCards("TestCard2", attackVals, player));

    // Place a card in the grid cell
    standardPlay.executePlay(1, 1, 0, player);

    // Attempt to place another card in the same cell
    standardPlay.executePlay(1, 1, 0, player);
  }

  /**
   * Tests the cardClash mechanic where a card should clash with neighboring cards. If the played
   * card has a higher attack value than an adjacent card, it should flip ownership.
   */
  @Test
  public void testCardClashMechanic() {

    HashMap<Direction, Integer> attackValsPlayer = new HashMap<>();
    attackValsPlayer.put(Direction.NORTH, 5);
    attackValsPlayer.put(Direction.EAST, 3);
    attackValsPlayer.put(Direction.SOUTH, 4);
    attackValsPlayer.put(Direction.WEST, 2);

    HashMap<Direction, Integer> attackValsOpponent = new HashMap<>();
    attackValsOpponent.put(Direction.NORTH, 2);
    attackValsOpponent.put(Direction.EAST, 1);
    attackValsOpponent.put(Direction.SOUTH, 2);
    attackValsOpponent.put(Direction.WEST, 2);

    // Place an opponent card with lower attack values
    Card opponentCard = new ThreeTrioCards("OpponentCard", attackValsOpponent,
        new ThreeTriosPlayer(Color.blue, new ArrayList<>()));
    grid[1][2].addCard(opponentCard);

    // Place player card and initiate card clash
    Card playerCard = new ThreeTrioCards("PlayerCard", attackValsPlayer, player);
    this.threeTrioCards.add(playerCard);
    standardPlay.executePlay(1, 1, 0, player);

    // Assert the opponent's card has switched ownership to the player
    assertEquals(player, grid[1][2].getCard().getPlayer());
  }

  /**
   * Tests that an IllegalArgumentException is thrown when attempting to play with a null player.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testExecutePlayWithNullPlayer() {
    standardPlay.executePlay(1, 1, 0, null);
  }

}


