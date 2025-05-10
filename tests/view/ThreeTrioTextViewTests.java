package view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.ModelUtils;
import model.StandardThreeTrios;
import model.StandardThreeTrios.PlayerKey;
import model.card.Card;
import model.card.Direction;
import model.card.ThreeTrioCards;
import model.cell.Cell;
import model.player.Player;
import org.junit.Before;
import org.junit.Test;
import textview.ThreeTrioTextView;


/**
 * Represents tests for the text view.
 */
public class ThreeTrioTextViewTests {

  private StandardThreeTrios model;
  private ThreeTrioTextView view;
  private Cell[][] grid;
  private List<Card> deck;

  /**
   * Sets the initial state up.
   */
  @Before
  public void setUp() {
    ModelUtils util = new ModelUtils();
    deck = util.createDeck("16CardDeck");
    grid = util.createBoard("FourByFourBoard");
    model = new StandardThreeTrios(grid);
    view = new ThreeTrioTextView(model);
  }

  /**
   * Test displaying the red player.
   */
  @Test
  public void testDisplayRed() {
    model.startGame(this.deck);
    String output = view.display();
    assertEquals("Player: RED", output.substring(0, 11));
  }

  /**
   * Test displaying the blue player.
   */
  @Test
  public void testDisplayBlue() {
    model.startGame(this.deck);
    model.playToGrid(0, 0, 0);
    String output = view.display();
    assertEquals("Player: BLUE", output.substring(0, 12));
  }

  /**
   * Test rendering the grid hole and cards.
   */
  @Test
  public void testDisplayGridHoleAndCards() {

    model.startGame(this.deck);
    Player playerRed = model.getPlayers().get(PlayerKey.ONE);

    Map<Direction, Integer> attackVals = new HashMap<>() {{
        put(Direction.NORTH, 1);
        put(Direction.EAST, 2);
        put(Direction.SOUTH, 3);
        put(Direction.WEST, 4);
      }};

    String name = "Player: RED";
    ThreeTrioCards card = new ThreeTrioCards(name, attackVals, playerRed);

    this.grid[0][2].addCard(card);

    model.playToGrid(0, 1, 0);

    assertFalse(this.grid[0][2].isEmpty());
    assertEquals(this.grid[0][2].getCard(), card);
    String output = view.display();
    assertEquals("Player: RED\n  R _ \n_ _ _ \nHand:\n" +
            card.getName() + ": NORTH 1 EAST 2 SOUTH 3 WEST 4 \n",
        output);
  }

  /**
   * Test each cell isn't null.
   */
  @Test
  public void testDisplayGridNotNullCells() {
    for (Cell[] cells : grid) {
      for (Cell cell : cells) {
        assertNotEquals(null, cell);
      }
    }
  }
}