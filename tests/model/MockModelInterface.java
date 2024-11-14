package model;

import java.util.List;
import java.util.Map;

/**
 * Represents a mock model interface for testing Three Trios game strategies.
 * Extends the ReadOnlyThreeTriosModel to provide additional testing capabilities
 * for inspecting and controlling game behavior during tests.
 */
public interface MockModelInterface extends ReadOnlyThreeTriosModel {

  /**
   * Sets the flip value for a specific cell position on the grid.
   * This allows tests to control how many cards would be flipped
   * when a card is played at the specified position.
   *
   * @param row the row index of the cell.
   * @param col the column index of the cell.
   * @param value the number of cards that would be flipped if a card is played at this position.
   */
  void setFlipValue(int row, int col, int value);

  /**
   * Sets whether a move at the specified position is valid.
   * Allows tests to control which moves are considered legal
   * independent of the actual game state.
   *
   * @param row the row index of the cell.
   * @param col the column index of the cell.
   * @param isValid true if the move should be considered valid, false otherwise.
   */
  void setValidMove(int row, int col, boolean isValid);

  /**
   * Retrieves the list of moves that have been inspected by strategies.
   * Each move is represented as a Map containing the row, column, and card index
   * that was considered during strategy execution.
   *
   * @return a List of Maps, where each Map contains "row", "col", and "index" keys
   *         with their corresponding Integer values.
   */
  List<Map<String, Integer>> getInspectedMoves();

  /**
   * Clears the log of inspected moves.
   * Should be called before testing a new strategy or scenario.
   * to ensure the inspection log only contains relevant moves.
   */
  void clearInspectionLog();

  /**
   * Executes the Corners strategy and records which moves were inspected.
   * This method should be used to test whether the Corners strategy.
   * properly evaluates corner positions on the board.
   */
  void playCorners();

  /**
   * Executes the MostFlips strategy and records which moves were inspected.
   * This method should be used to test whether the MostFlips strategy.
   * properly evaluates all valid positions and selects moves that flip.
   * the most cards.
   */
  void playMostFlips();

}
