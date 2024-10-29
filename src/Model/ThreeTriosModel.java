package Model;

import Model.Card.Card;
import Model.Cell.Cell;
import Model.ModelPlayer.Player;
import Model.StandardThreeTrios.PlayerKey;
import java.util.ArrayList;
import java.util.HashMap;

public interface ThreeTriosModel {

  void playToGrid(int row, int col, int handIndex);

  void playToGridCPU();

  Cell[][] getGrid();

  Player currentPlayer();

  boolean isGameOver();

  void startGame(ArrayList<Card> deck);

  Player getWinner();

  HashMap<PlayerKey, Player> getPlayers();
}
