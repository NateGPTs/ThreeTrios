package ThreeTrioModelTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import Model.Card.Card;
import Model.Cell.Cell;
import Model.ModelUtils;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class testModelUtils {

  private ModelUtils modelUtils;

  @Before
  public void setUp() throws Exception {

    modelUtils = new ModelUtils();
  }


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
  }

  @Test
  public void testCreateDeck() {

    ArrayList<Card> deck = modelUtils.createDeck("16CardDeck");

    assertNotNull(deck);

    assertEquals(16, deck.size());

  }


}
