package controller;

import model.player.Player;

public interface PlayerFeatures {

  void notifyPlay(Player player, Integer handIndex, int row, int col);

  boolean isOurTurn();
}
