package ttview;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import model.ReadOnlyThreeThriosModel;
import model.StandardThreeTrios;
import model.StandardThreeTrios.PlayerKey;
import model.card.Card;
import model.card.Direction;
import model.cell.Cell;
import model.player.Player;

public class ThreeTriosBoard extends JPanel {

  private final ReadOnlyThreeThriosModel model;
  private final List<Card> hand1;
  private final List<Card> hand2;
  private final Color color1;
  private final Color color2;

  public ThreeTriosBoard(ReadOnlyThreeThriosModel model) {
    this.model = model;
    Map<PlayerKey, Player> players = model.getPlayers();
    Player player1 = players.get(PlayerKey.ONE);
    Player player2 = players.get(PlayerKey.TWO);
    this.hand1 = player1.getHand();
    this.hand2 = player2.getHand();
    this.color1 = player1.getColor();
    this.color2 = player2.getColor();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    int totalWidth = getWidth();
    int totalHeight = getHeight();
    int handWidth = totalWidth / 8;
    int boardWidth = totalWidth - (2 * handWidth);  // Remaining 60%
    drawHand(g2d, hand1, color1, 0, 0, handWidth, totalHeight);
    drawBoard(g2d, handWidth, 0, boardWidth, totalHeight);
    drawHand(g2d, hand2, color2, handWidth + boardWidth, 0, handWidth, totalHeight);
  }

  private void drawHand(Graphics2D g2d, List<Card> hand, Color color, int x, int y, int width, int height) {
    int cardHeight = height / hand.size();

    for (int card = 0; card < hand.size(); card++) {
      int cardY = y + (card * cardHeight);

      g2d.setColor(color);
      g2d.fillRect(x, cardY, width, cardHeight);
      g2d.setColor(Color.BLACK);
      g2d.drawRect(x, cardY, width, cardHeight);

      int centerX = x + width / 2;
      int centerY = cardY + cardHeight / 2;

      for (Direction direction : Direction.values()) {
        int attack = hand.get(card).getAttackVal(direction);

        switch (direction) {
          case NORTH:
            g2d.drawString(String.valueOf(attack), centerX - 5, cardY + 15);
            break;
          case SOUTH:
            g2d.drawString(String.valueOf(attack), centerX - 5, cardY + cardHeight - 5);
            break;
          case EAST:
            g2d.drawString(String.valueOf(attack), x + width - 15, centerY + 5);
            break;
          case WEST:
            g2d.drawString(String.valueOf(attack), x + 5, centerY + 5);
            break;
          default:
            throw new IllegalStateException("Unexpected value: " + direction);
        }
      }
    }
  }

  private void drawBoard(Graphics2D g2d, int x, int y, int width, int height) {
    Cell[][] grid = model.getGrid();
    int rows = grid.length;
    int cols = grid[0].length;

    int cellSize = Math.min(width / cols, height / rows);
    int startX = x + (width - (cellSize * cols)) / 2;
    int startY = y + (height - (cellSize * rows)) / 2;

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        Cell cell = grid[row][col];
        int cellX = startX + (col * cellSize);
        int cellY = startY + (row * cellSize);

        if (cell.isHole()) {
          g2d.setColor(Color.YELLOW);
        } else if (cell.isEmpty()) {
          g2d.setColor(Color.GRAY);
        } else {
          Player owner = cell.getCard().getPlayer();
          g2d.setColor(owner.equals(model.getPlayers().get(StandardThreeTrios.PlayerKey.ONE))
              ? Color.PINK : Color.BLUE);
        }

        g2d.fillRect(cellX, cellY, cellSize, cellSize);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(cellX, cellY, cellSize, cellSize);

        if (!cell.isHole() && !cell.isEmpty()) {
          g2d.setColor(Color.WHITE);
          g2d.drawString("?", cellX + cellSize/2 - 5, cellY + cellSize/2 + 5);
        }
      }
    }
  }
}
