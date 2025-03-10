package model.player;

import controller.PlayerFeatures;
import java.awt.Color;
import java.util.List;
import model.ThreeTriosModel;
import model.card.Card;

/**
 * Represents a player in the Three Trios game with qualities such as their color, hand of cards,
 * and ability to play cards.
 */
public interface Player {

  /**
   * Gets the color assigned to the player.
   *
   * @return the color of the player
   */
  Color getColor();


  /**
   * Retrieves current hand of cards from player .
   *
   * @return an arraylist of cards in player's hand
   */
  List<Card> getHand();


  /**
   * This method removes the card at the players Index, and returns it.
   *
   * @param cardIndex represents the card you want to remove and return.
   * @return Card
   */
  Card playCard(int cardIndex);

  /**
   * Checks if player is controlled by CPU.
   *
   * @return true if the player is a CPU; false otherwise
   */
  Boolean isCPU();

  void addListener(PlayerFeatures listener);

  void itsYourTurn(ThreeTriosModel model);

}
