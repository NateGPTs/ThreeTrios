package model;

import static org.junit.Assert.assertEquals;

import model.card.Direction;
import model.card.ThreeTrioCards;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for ThreeTrioCards implementation. Tests card functionality excluding Player-related
 * features.
 */
public class ThreeTrioCardsTest {

  private static final String VALID_NAME = "TestCard";
  private HashMap<Direction, Integer> validAttackVals;

  /**
   * Sets up the test environment before each test. Initializes a valid attack values map with
   * values for all directions.
   */
  @Before
  public void setUp() {
    validAttackVals = new HashMap<>();
    validAttackVals.put(Direction.NORTH, 5);
    validAttackVals.put(Direction.SOUTH, 3);
    validAttackVals.put(Direction.EAST, 4);
    validAttackVals.put(Direction.WEST, 2);
  }

  /**
   * Tests the constructor with valid name and attack values. Verifies that the card is properly
   * initialized with the provided values.
   */
  @Test
  public void testConstructorWithValidParameters() {
    ThreeTrioCards card = new ThreeTrioCards(VALID_NAME, validAttackVals);
    assertEquals(VALID_NAME, card.getName());
    assertEquals(5, card.getAttackVal(Direction.NORTH));
  }

  /**
   * Tests the constructor with null name parameter. Expects an IllegalArgumentException to be
   * thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNullName() {
    new ThreeTrioCards(null, validAttackVals);
  }

  /**
   * Tests the constructor with empty name parameter. Expects an IllegalArgumentException to be
   * thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithEmptyName() {
    new ThreeTrioCards("", validAttackVals);
  }

  /**
   * Tests the constructor with null attack values map. Expects an IllegalArgumentException to be
   * thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNullAttackVals() {
    new ThreeTrioCards(VALID_NAME, null);
  }

  /**
   * Tests the constructor with empty attack values map. Expects an IllegalArgumentException to be
   * thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithEmptyAttackVals() {
    new ThreeTrioCards(VALID_NAME, new HashMap<>());
  }

  /**
   * Tests getting attack values for all directions. Verifies that the correct attack value is
   * returned for each direction.
   */
  @Test
  public void testGetAttackValForAllDirections() {
    ThreeTrioCards card = new ThreeTrioCards(VALID_NAME, validAttackVals);
    assertEquals(5, card.getAttackVal(Direction.NORTH));
    assertEquals(3, card.getAttackVal(Direction.SOUTH));
    assertEquals(4, card.getAttackVal(Direction.EAST));
    assertEquals(2, card.getAttackVal(Direction.WEST));
  }

  /**
   * Tests getting attack values with different values for each direction. Verifies that the card
   * correctly stores and returns different attack values.
   */
  @Test
  public void testGetAttackValWithDifferentValues() {
    HashMap<Direction, Integer> differentVals = new HashMap<>();
    differentVals.put(Direction.NORTH, 10);
    differentVals.put(Direction.SOUTH, 7);
    differentVals.put(Direction.EAST, 8);
    differentVals.put(Direction.WEST, 9);

    ThreeTrioCards card = new ThreeTrioCards(VALID_NAME, differentVals);
    assertEquals(10, card.getAttackVal(Direction.NORTH));
    assertEquals(7, card.getAttackVal(Direction.SOUTH));
    assertEquals(8, card.getAttackVal(Direction.EAST));
    assertEquals(9, card.getAttackVal(Direction.WEST));
  }

  /**
   * Tests getting the name of the card. Verifies that the correct name is returned.
   */
  @Test
  public void testGetName() {
    String customName = "CustomCardName";
    ThreeTrioCards card = new ThreeTrioCards(customName, validAttackVals);
    assertEquals(customName, card.getName());
  }

  /**
   * Tests creating multiple cards with the same attack values. Verifies that cards maintain their
   * own attack values independently.
   */
  @Test
  public void testMultipleCardsWithSameAttackVals() {
    ThreeTrioCards card1 = new ThreeTrioCards("Card1", validAttackVals);
    ThreeTrioCards card2 = new ThreeTrioCards("Card2", validAttackVals);

    assertEquals(card1.getAttackVal(Direction.NORTH), card2.getAttackVal(Direction.NORTH));
    assertEquals(card1.getAttackVal(Direction.SOUTH), card2.getAttackVal(Direction.SOUTH));
    assertEquals(card1.getAttackVal(Direction.EAST), card2.getAttackVal(Direction.EAST));
    assertEquals(card1.getAttackVal(Direction.WEST), card2.getAttackVal(Direction.WEST));
  }
}
