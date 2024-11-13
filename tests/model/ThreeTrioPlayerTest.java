package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import model.card.Card;
import model.player.Player;
import model.player.ThreeTriosPlayer;
import java.awt.Color;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;


/**
 * Test class for ThreeTriosPlayer implementation. Tests player functionality including hand
 * management and player properties.
 */
public class ThreeTrioPlayerTest {

  private static final Color PLAYER_COLOR = Color.BLUE;
  private Player player;

  /**
   * Sets up the test environment before each test. Initializes a player with a hand of cards.
   */
  @Before
  public void setUp() throws Exception {
    ModelUtils util = new ModelUtils();
    List<Card> initialHand = util.createDeck("16CardDeck");
    player = new ThreeTriosPlayer(PLAYER_COLOR, initialHand);
  }

  /**
   * Tests player construction with valid parameters. Verifies that the player is properly
   * initialized with correct color and hand.
   */
  @Test
  public void testPlayerConstruction() {
    assertEquals(PLAYER_COLOR, player.getColor());
    assertFalse(player.isCPU());
    assertEquals(16, player.getHand().size());
  }

  /**
   * Tests player construction with empty hand. Verifies that a player can be created with no
   * cards.
   */
  @Test
  public void testPlayerConstructionWithEmptyHand() {
    Player emptyPlayer = new ThreeTriosPlayer(PLAYER_COLOR, new ArrayList<>());
    assertTrue(emptyPlayer.getHand().isEmpty());
  }

  /**
   * Tests getting player's color. Verifies that the correct color is returned.
   */
  @Test
  public void testGetColor() {
    assertEquals(PLAYER_COLOR, player.getColor());

    // Test with different color
    Player redPlayer = new ThreeTriosPlayer(Color.RED, new ArrayList<>());
    assertEquals(Color.RED, redPlayer.getColor());
  }

  /**
   * Tests that getHand returns a defensive copy. Verifies that modifications to the returned hand
   * don't affect the player's actual hand.
   */
  @Test
  public void testGetHandDefensiveCopy() {
    ArrayList<Card> hand = player.getHand();
    int initialSize = hand.size();

    // Modify the returned hand
    hand.clear();

    // Verify original hand is unchanged
    assertEquals(initialSize, player.getHand().size());
  }

  /**
   * Tests playing a card from valid index. Verifies that the correct card is removed and returned.
   */
  @Test
  public void testPlayCardValidIndex() {
    ArrayList<Card> handBefore = player.getHand();
    Card playedCard = player.playCard(0);

    // Verify card was removed from hand
    ArrayList<Card> handAfter = player.getHand();
    assertEquals(handBefore.size() - 1, handAfter.size());
    assertFalse(handAfter.contains(playedCard));
  }

  /**
   * Tests playing multiple cards. Verifies that cards are correctly removed from hand in sequence.
   */
  @Test
  public void testPlayMultipleCards() {
    int initialSize = player.getHand().size();

    player.playCard(0);
    assertEquals(initialSize - 1, player.getHand().size());

    player.playCard(0);
    assertEquals(initialSize - 2, player.getHand().size());
  }

  /**
   * Tests playing a card with invalid index. Expects IndexOutOfBoundsException.
   */
  @Test(expected = IndexOutOfBoundsException.class)
  public void testPlayCardInvalidIndex() {
    player.playCard(player.getHand().size());
  }

  /**
   * Tests playing a card with negative index. Expects IndexOutOfBoundsException.
   */
  @Test(expected = IndexOutOfBoundsException.class)
  public void testPlayCardNegativeIndex() {
    player.playCard(-1);
  }

  /**
   * Tests that player is not CPU. Verifies that isCPU returns false.
   */
  @Test
  public void testIsCPU() {
    assertFalse(player.isCPU());
  }

  /**
   * Tests that cards in player's hand are assigned to the player. Verifies that each card's player
   * reference is set correctly.
   */
  @Test
  public void testCardsAssignedToPlayer() {
    ArrayList<Card> hand = player.getHand();
    for (Card card : hand) {
      assertEquals(player, card.getPlayer());
    }
  }

  /**
   * Tests playing all cards from hand. Verifies that hand becomes empty after playing all cards.
   */
  @Test
  public void testPlayAllCards() {
    int handSize = player.getHand().size();
    for (int i = 0; i < handSize; i++) {
      player.playCard(0);
    }
    assertTrue(player.getHand().isEmpty());
  }
}
