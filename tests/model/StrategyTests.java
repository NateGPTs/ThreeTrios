package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;
import model.card.Card;
import model.cell.Cell;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the ThreeTrios game strategies. Tests both the MostFlips and Corners strategies to
 * verify correct implementation. of game rules and decisions.
 */
public class StrategyTests {

  private MockModel model;
  private Cell[][] grid;

  /**
   * Sets up the test environment before each test. Initializes a new mock model with a standard
   * board and test deck.
   */
  @Before
  public void setUp() {
    ModelUtils utils = new ModelUtils();
    grid = utils.createBoard("FourByFourBoard");
    List<Card> testDeck = utils.createDeck("testCardDeck");
    model = new MockModel(grid);
    model.startGame(testDeck);
  }

  /**
   * Tests that the MostFlips strategy inspects all valid positions on the board. Verifies that each
   * empty, non-hole cell is checked with each card in hand.
   */
  @Test
  public void testMostFlipsChecksAllValidLocations() {
    // Play MostFlips strategy
    model.playMostFlips();
    List<Map<String, Integer>> inspectedMoves = model.getInspectedMoves();

    // Count number of non-hole cells in grid
    int validCellCount = 0;
    for (int row = 0; row < grid.length; row++) {
      for (int col = 0; col < grid[0].length; col++) {
        if (!grid[row][col].isHole() && grid[row][col].isEmpty()) {
          validCellCount++;
          assertTrue(
              String.format("Location (%d,%d) was not checked", row, col),
              containsMove(inspectedMoves, row, col)
          );
        }
      }
    }

    // Verify all valid cells were checked
    // (multiplied by hand size since each cell is checked with each card)
    assertEquals(
        "Not all valid locations were checked",
        validCellCount * model.currentPlayer().getHand().size(),
        inspectedMoves.size()
    );
  }

  /**
   * Tests that the MostFlips strategy correctly identifies and chooses the move that results in the
   * most card flips. Sets up different flip values for different positions and verifies the
   * strategy chooses the position with the highest value.
   */
  @Test
  public void testMostFlipsChoosesHighestValueMove() {

    model.setFlipValue(1, 1, 2); // Some flips
    model.setFlipValue(2, 2, 5); // Most flips - should be chosen
    model.setFlipValue(0, 0, 1); // Few flips

    // Play MostFlips strategy
    model.playMostFlips();

    // Get the moves that were considered best
    List<Map<String, Integer>> bestMoves = model.getInspectedMoves();

    // Verify the strategy chose the move with most flips (2,2)
    assertTrue("Strategy did not choose highest value move",
        bestMoves.stream().anyMatch(move ->
            move.get("row") == 2 && move.get("col") == 2
        ));
  }

  /**
   * Tests how the MostFlips strategy handles multiple moves that result in the same number of
   * flips. Verifies that all equally good moves are identified and included in the possible moves
   * list.
   */
  @Test
  public void testMostFlipsHandlesEqualValues() {
    // Set up multiple locations with same flip value
    model.setFlipValue(1, 1, 3);
    model.setFlipValue(2, 2, 3);
    model.setFlipValue(0, 0, 1);

    // Play MostFlips strategy
    model.playMostFlips();

    // Get the moves that were considered best
    List<Map<String, Integer>> bestMoves = model.getInspectedMoves();

    // 16 positions from two equal moves.
    assertEquals("Should find 16 best moves", 16, bestMoves.size());

    // Verify both equal-value moves are included
    boolean found1_1 = false;
    boolean found2_2 = false;

    for (Map<String, Integer> move : bestMoves) {
      if (move.get("row") == 1 && move.get("col") == 1) {
        found1_1 = true;
      }
      if (move.get("row") == 2 && move.get("col") == 2) {
        found2_2 = true;
      }
    }

    assertTrue("Missing some equal-value moves", found1_1 && found2_2);
  }

  /**
   * Tests that the Corners strategy properly evaluates all valid corner positions. Verifies that
   * each corner that is neither a hole nor occupied is checked.
   */
  @Test
  public void testCornersChecksAllValidCorners() {
    // Play Corners strategy
    model.playCorners();
    List<Map<String, Integer>> inspectedMoves = model.getInspectedMoves();

    // Define all corner coordinates
    int[][] corners = {{0, 0}, {0, 3}, {3, 0}, {3, 3}};

    // Check each corner was inspected if it's valid
    for (int[] corner : corners) {
      int row = corner[0];
      int col = corner[1];
      if (!grid[row][col].isHole() && grid[row][col].isEmpty()) {
        assertTrue(
            String.format("Corner (%d,%d) was not checked", row, col),
            containsMove(inspectedMoves, row, col)
        );
      }
    }
  }

  /**
   * Tests that the Corners strategy properly ignores non-corner positions. Verifies that the
   * strategy does not consider moves in the middle or along the edges of the board (except
   * corners).
   */
  @Test
  public void testCornersIgnoresNonCornerPositions() {
    // Play Corners strategy
    model.playCorners();
    List<Map<String, Integer>> inspectedMoves = model.getInspectedMoves();

    // Check some non-corner positions
    assertFalse("Should not check non-corner (1,1)",
        containsMove(inspectedMoves, 1, 1));
    assertFalse("Should not check non-corner (2,2)",
        containsMove(inspectedMoves, 2, 2));
    assertFalse("Should not check non-corner (1,2)",
        containsMove(inspectedMoves, 1, 2));
  }

  /**
   * Tests that the Corners strategy properly handles invalid corner positions. Verifies that
   * corners marked as invalid are not considered as possible moves.
   */
  @Test
  public void testCornersHandlesInvalidCorners() {

    // Make a corner invalid
    model.setValidMove(0, 0, false);

    // Play Corners strategy
    model.playCorners();
    List<Map<String, Integer>> inspectedMoves = model.getInspectedMoves();

    // Verify invalid corner was not included in moves
    assertFalse("Should not include invalid corner in moves",
        containsMove(inspectedMoves, 0, 0));
  }


  private boolean containsMove(List<Map<String, Integer>> moves, int row, int col) {
    return moves.stream().anyMatch(move ->
        move.get("row") == row && move.get("col") == col
    );
  }
}