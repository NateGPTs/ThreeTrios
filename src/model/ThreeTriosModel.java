package model;

import java.util.List;
import model.card.Card;

/**
 * Interface represents the model for a Three Trios game. Methods to manage the game state, players,
 * and grid interactions.
 */
public interface ThreeTriosModel extends ReadOnlyThreeTriosModel {

  /**
   * Plays a card from the current player's hand to the grid location.
   *
   * @param row       the row index of the cell
   * @param col       the column index of the cell
   * @param handIndex the index of the card in hand
   * @throws IllegalStateException    if the game is not ongoing
   * @throws IllegalArgumentException if the move is invalid
   */
  void playToGrid(int row, int col, int handIndex);

  /**
   * Plays a move on behalf of the CPU player (not implemented yet).
   */
  void playToGridCPU();

  /**
   * Starts the game with the given deck of cards, and sets up the game state, players, and initial
   * hands.
   *
   * @param deck the deck of cards
   * @throws IllegalStateException    if the game has already started or is over.
   * @throws IllegalArgumentException if the deck does not have enough cards.
   */
  void startGame(List<Card> deck);

}
