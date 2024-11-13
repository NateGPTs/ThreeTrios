package model.player;

import model.card.Card;
import java.awt.Color;
import java.util.ArrayList;

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
  ArrayList<Card> getHand();


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


}
