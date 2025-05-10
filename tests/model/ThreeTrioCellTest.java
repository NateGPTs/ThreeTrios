package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import model.cell.ThreeTrioCell;
import org.junit.Before;
import org.junit.Test;


/**
 * Test class for ThreeTrioCell implementation. Tests core cell functionality including constructors
 * and cell properties.
 */
public class ThreeTrioCellTest {

  private int row;
  private int col;

  @Before
  public void setUp() throws Exception {

    row = 1;
    col = 2;
  }

  /**
   * Tests constructor with hole parameter set to true. Verifies that a cell is properly initialized
   * as a hole.
   */
  @Test
  public void testConstructorWithHole() {
    ThreeTrioCell cell = new ThreeTrioCell(true, row, col);
    assertTrue(cell.isHole());
    assertEquals(row, cell.getRow());
    assertEquals(col, cell.getCol());
  }

  /**
   * Tests constructor with hole parameter set to false. Verifies that a cell is properly
   * initialized as a non-hole.
   */
  @Test
  public void testConstructorWithNonHole() {
    ThreeTrioCell cell = new ThreeTrioCell(false, row, col);
    assertFalse(cell.isHole());
    assertEquals(row, cell.getRow());
    assertEquals(col, cell.getCol());
  }

  /**
   * Tests constructor with null hole parameter. Expects an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNullHole() {
    new ThreeTrioCell(null, row, col);
  }

  /**
   * Tests default constructor. Verifies that cell is initialized as non-hole and empty.
   */
  @Test
  public void testDefaultConstructor() {
    ThreeTrioCell cell = new ThreeTrioCell(row, col);
    assertFalse(cell.isHole());
    assertTrue(cell.isEmpty());
    assertEquals(row, cell.getRow());
    assertEquals(col, cell.getCol());
  }

  /**
   * Tests isEmpty method on a hole cell. Expects an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIsEmptyOnHoleCell() {
    ThreeTrioCell cell = new ThreeTrioCell(true, row, col);
    cell.isEmpty();
  }

  /**
   * Tests isEmpty method on an empty cell. Verifies that an empty cell returns true.
   */
  @Test
  public void testIsEmptyOnEmptyCell() {
    ThreeTrioCell cell = new ThreeTrioCell(row, col);
    assertTrue(cell.isEmpty());
  }

  /**
   * Tests getting a card from an empty cell. Expects an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGetCardFromEmptyCell() {
    ThreeTrioCell cell = new ThreeTrioCell(row, col);
    cell.getCard();
  }

  /**
   * Tests getting a card from a hole cell. Expects an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGetCardFromHoleCell() {
    ThreeTrioCell cell = new ThreeTrioCell(true, row, col);
    cell.getCard();
  }

  /**
   * Tests cell position getters. Verifies that row and column values are correctly stored and
   * retrieved.
   */
  @Test
  public void testCellPosition() {
    ThreeTrioCell cell = new ThreeTrioCell(row, col);
    assertEquals(row, cell.getRow());
    assertEquals(col, cell.getCol());
  }

  /**
   * Tests cell creation with negative row and column values. Verifies that negative coordinates are
   * allowed.
   */
  @Test
  public void testCellWithNegativeCoordinates() {
    ThreeTrioCell cell = new ThreeTrioCell(-1, -1);
    assertEquals(-1, cell.getRow());
    assertEquals(-1, cell.getCol());
  }

  /**
   * Tests cell creation with zero coordinates. Verifies that zero coordinates are allowed.
   */
  @Test
  public void testCellWithZeroCoordinates() {
    ThreeTrioCell cell = new ThreeTrioCell(0, 0);
    assertEquals(0, cell.getRow());
    assertEquals(0, cell.getCol());
  }

  /**
   * Tests copy constructor with a non-hole cell. Verifies that all properties are correctly
   * copied.
   */
  @Test
  public void testCopyConstructorWithNonHoleCell() {
    ThreeTrioCell originalCell = new ThreeTrioCell(row, col);
    ThreeTrioCell copiedCell = new ThreeTrioCell(originalCell);

    assertEquals(originalCell.isHole(), copiedCell.isHole());
    assertEquals(originalCell.getRow(), copiedCell.getRow());
    assertEquals(originalCell.getCol(), copiedCell.getCol());
    assertTrue(copiedCell.isEmpty());
  }

  /**
   * Tests copy constructor with a hole cell. Verifies that hole status is correctly copied.
   */
  @Test
  public void testCopyConstructorWithHoleCell() {
    ThreeTrioCell originalCell = new ThreeTrioCell(true, row, col);
    ThreeTrioCell copiedCell = new ThreeTrioCell(originalCell);

    assertTrue(copiedCell.isHole());
    assertEquals(originalCell.getRow(), copiedCell.getRow());
    assertEquals(originalCell.getCol(), copiedCell.getCol());
  }

  /**
   * Tests that large coordinate values are handled correctly. Verifies that large integers are
   * stored and retrieved properly.
   */
  @Test
  public void testCellWithLargeCoordinates() {
    int largeRow = Integer.MAX_VALUE;
    int largeCol = Integer.MAX_VALUE;
    ThreeTrioCell cell = new ThreeTrioCell(largeRow, largeCol);
    assertEquals(largeRow, cell.getRow());
    assertEquals(largeCol, cell.getCol());
  }
}
