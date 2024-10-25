package Model;

import Model.Cell.Cell;
import Model.ModelPlayer.Player;

public interface ThreeTriosModel {

  void playToGrid(int row, int col, int handIndex);

  Cell[][] getGrid();

  Player currentPlayer();

  boolean isGameOver();

  void startGame();

  Player getWinner();


}
