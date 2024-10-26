package Model.CommandPlayToGrid;

import Model.Card.Card;
import Model.ModelPlayer.Player;
import java.util.ArrayList;

public interface GridCommands {

  void executePlay(int row, int col, int handIndex, Player player);

  void executeCPUPlay();

}
