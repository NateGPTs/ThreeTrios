package controller;

import model.ThreeTriosModel;
import model.player.Player;
import ttview.ViewToControllerFeature;

public class ThreeTriosController implements ModelFeatures, PlayerFeatures {

  private ThreeTriosModel model;
  private ViewToControllerFeature view;
  private Player player;

  public ThreeTriosController(ThreeTriosModel model, Player player, ViewToControllerFeature view) {

    this.model = model;
    this.player = player;
    this.view = view;

    player.addListener(this);
    view.addListener(this);
    model.addListener(this);
    view.setPlayer(player);
    view.setVisible(true);
  }

  public boolean isOurTurn() {

    return this.model.currentPlayer().equals(this.player);

  }

  @Override
  public void notifyPlay(Player player, Integer handIndex, int row, int col) {

    if (!isOurTurn()) {
      this.view.showPanel("Not your turn maggot.", "!WARNING!");
    } else if (handIndex == null) {
      this.view.showPanel("No cards selected.", "Select a card!");
    } else {
      try {
        this.model.playToGrid(row, col, handIndex);
      } catch (IllegalArgumentException e) {
        System.out.println("Error message: " + e.getMessage());
        this.view.showPanel("Invalid move.", "!WARNING!");
      }
    }

  }


  @Override
  public void notifyUpdateView() {
    this.view.updateView();
  }

  @Override
  public void notifyGameEnded() {

  }
}
