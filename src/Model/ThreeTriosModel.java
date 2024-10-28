package Model;

import Model.Card.Card;
import Model.Cell.Cell;
import Model.ModelPlayer.Player;
import java.util.ArrayList;

public interface ThreeTriosModel {

  void playToGrid(int row, int col, int handIndex);

  void playToGridCPU();

  Cell[][] getGrid();

  Player currentPlayer();

  boolean isGameOver();

  void startGame(ArrayList<Card> deck);

  Player getWinner();


}
