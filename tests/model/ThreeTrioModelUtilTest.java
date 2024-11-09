package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import model.card.Card;
import model.cell.Cell;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the model util class.
 */
public class ThreeTrioModelUtilTest {

  private ModelUtils modelUtils;

  /**
   * Sets the game up.
   *
   * @throws Exception when needed.
   */
  @Before
  public void setUp() throws Exception {

    modelUtils = new ModelUtils();
  }

  /**
   * Test creating the board.
   */
  @Test
  public void testCreateBoard() {

    Cell[][] board = modelUtils.createBoard("FourByFourBoard");
    assertNotNull(board);
    assertEquals(4, board.length);
    assertEquals(4, board[0].length);
    assertNotNull(board[3][3]);
    assertNotNull(board[2][2]);
    assertNotNull(board[1][1]);
    assertNotNull(board[0][0]);
    assertFalse(board[0][0].isHole());
    assertTrue(board[0][3].isHole());
    assertEquals(0, board[0][0].getRow());
    assertEquals(0, board[0][0].getCol());
    assertEquals(0, board[0][3].getRow());
    assertEquals(3, board[0][3].getCol());
    assertEquals(3, board[3][0].getRow());
    assertEquals(3, board[3][3].getCol());
    assertEquals(2, board[2][0].getRow());
    assertEquals(2, board[0][2].getCol());

  }

  /**
   * Tests creating the deck.
   */
  @Test
  public void testCreateDeck() {

    ArrayList<Card> deck = modelUtils.createDeck("16CardDeck");

    assertNotNull(deck);

    assertEquals(16, deck.size());

    assertNotNull(deck.get(0).getPlayer());

  }


}
