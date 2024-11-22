package model.player;

import controller.PlayerFeatures;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.ThreeTriosModel;
import model.card.Card;
import model.strategy.StandardBestMove;
import model.strategy.ThreeTriosStrategy;

/**
 * Represents a player in the Three Trios game with a specific color, hand of cards, and whether
 * they are CPU-controlled.
 */
public class ThreeTriosPlayer implements Player {

  private final Color playerColor;
  private final List<Card> cards;
  private final Boolean isCPU;
  private final List<PlayerFeatures> features;
  private List<ThreeTriosStrategy> strategies;


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
    this.features = new ArrayList<>();
    this.strategies = null;

    for (Card c : cards) {

      c.setPlayer(this);

    }
  }

  public ThreeTriosPlayer(Color playerColor, List<Card> cards, List<ThreeTriosStrategy> strategies) {
    this(playerColor, cards);
    this.strategies = strategies;
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

  @Override
  public void addListener(PlayerFeatures listener) {
    this.features.add(listener);
  }

  @Override
  public void itsYourTurn(ThreeTriosModel model) {
    if (isCPU()) {
      Map<String, Integer> bestMove = new StandardBestMove(this.strategies).getBestMove(model, this);
      int index = bestMove.get("index");
      int column = bestMove.get("col");
      int row = bestMove.get("row");
      for (PlayerFeatures feature : features) {
        feature.notifyPlay(this, index, column, row);
      }
    }
  }


}
