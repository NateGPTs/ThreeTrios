package model.cell;

import java.awt.Color;
import java.util.Map;
import model.card.Card;
import model.card.Direction;
import model.player.Player;

/**
 * Represents a cell on the grid in the Three Trios game. Each cell may be a hole or contain a
 * certain card.
 */
public interface Cell {

  /**
   * Determines if the cell is a hole.
   *
   * @return true if this cell is a hole; false otherwise.
   */
  boolean isHole();

  /**
   * Checks if cell is empty (contains no card and isn't a hole).
   *
   * @return true if this cell is empty; false otherwise.
   * @throws IllegalArgumentException if this cell is a hole.
   */
  boolean isEmpty();

  /**
   * Adds a card to this cell.
   *
   * @param given the cell placement card
   * @throws IllegalArgumentException if the cell already has a card
   */
  void addCard(Card given);

  /**
   * Gets the card.
   *
   * @throws IllegalArgumentException if the cell is empty or has a hole
   */
  Card getCard();

  /**
   * Checks if the given player returns the card.
   *
   * @return the True and False depending if the player does/does not.
   */
  Boolean checkOwnership(Player player);

  /**
   * Gets the row index of this cell.
   *
   * @return the row index
   */
  int getRow();

  /**
   * Gets the column index of this cell.
   *
   * @return the column index
   */
  int getCol();


  /**
   * Sets the player who owns the card in this cell.
   *
   * @param player the player to be assigned card in this cell.
   */
  void setPlayer(Player player);

  /**
   * Get the owner of the card.
   */
  Player getOwner();

  Map<Direction, Integer> getAllAttackVals();

  Color getColor();
}
