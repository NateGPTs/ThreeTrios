package textview;

import java.awt.Color;
import model.ReadOnlyThreeTriosModel;
import model.StandardThreeTrios.PlayerKey;
import model.card.Card;
import model.card.Direction;
import model.card.ThreeTrioCards;
import model.cell.Cell;
import model.player.Player;

/**
 * Represents a text-based view of the Three Trios game. Responsible for rendering the game state in
 * a readable format, includes the current player, game grid, and the player's hand.
 */
public class ThreeTrioTextView implements TextView {

  private final ReadOnlyThreeTriosModel model;

  /**
   * Constructs a ThreeTrioTextView with the specified game model.
   *
   * @param model the StandardThreeTrios model holding the game state
   */
  public ThreeTrioTextView(ReadOnlyThreeTriosModel model) {

    this.model = model;
  }


  @Override
  public String display() {
    StringBuilder display = new StringBuilder();
    Player playerOne = model.getPlayers().get(PlayerKey.ONE);
    Player playerTwo = model.getPlayers().get(PlayerKey.TWO);
    Player currentPlayer = model.currentPlayer();
    display.append("Player: ").append(
        currentPlayer.getColor().equals(Color.RED) ? "RED" : "BLUE"
    ).append("\n");

    Cell[][] grid = model.getGrid();
    for (Cell[] cells : grid) {
      for (Cell cell : cells) {
        if (cell.isHole()) {
          display.append("X");
        }
        if (cell.getCard().getPlayer().equals(playerOne)) {
          display.append("R");
        }
        if (cell.getCard().getPlayer().equals(playerTwo)) {
          display.append("B");
        }
        if (cell.isEmpty()) {
          display.append("C");
        } else {
          display.append("?");
        }
      }
      display.append("\n");
    }

    display.append("Hand:\n");
    for (Card card : currentPlayer.getHand()) {
      if (card instanceof ThreeTrioCards) {
        ThreeTrioCards trioCard = (ThreeTrioCards) card;
        display.append(trioCard.getName()).append(": ");
        for (Direction direction : Direction.values()) {
          display.append(direction).append(" ")
              .append(trioCard.getAttackVal(direction)).append(" ");
        }
        display.append("\n");
      }
    }
    return display.toString();
  }

}
