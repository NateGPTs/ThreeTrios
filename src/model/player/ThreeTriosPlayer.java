package model.player;

import java.util.List;
import model.card.Card;
import java.awt.Color;
import java.util.ArrayList;

/**
 * Represents a player in the Three Trios game with a specific color, hand of cards, and whether
 * they are CPU-controlled.
 */
public class ThreeTriosPlayer implements Player {

  private final Color playerColor;
  private final List<Card> cards;
  private final Boolean isCPU;


  /**
   * Creates a ThreeTriosPlayer with a specified color and hand of cards.
   *
   * @param playerColor the color representing the player
   * @param cards       the initial hand of cards
   */
  public ThreeTriosPlayer(Color playerColor, List<Card> cards) {
    this.playerColor = playerColor;
    this.cards = cards;
    this.isCPU = false;

    for (Card c : cards) {

      c.setPlayer(this);

    }
  }


  @Override
  public Color getColor() {
    return this.playerColor;
  }

  @Override
  public List<Card> getHand() {

    List<Card> cardCopied = new ArrayList<Card>();

    for (Card c : cards) {

      cardCopied.add(c);

    }

    return cardCopied;
  }

  @Override
  public Card playCard(int cardIndex) {
    return this.cards.remove(cardIndex);
  }

  @Override
  public Boolean isCPU() {
    return this.isCPU;
  }





}
