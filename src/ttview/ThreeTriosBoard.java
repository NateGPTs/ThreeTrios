package ttview;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import model.ReadOnlyThreeThriosModel;
import model.StandardThreeTrios.PlayerKey;
import model.card.Card;
import model.card.Direction;
import model.cell.Cell;
import model.player.Player;

class ThreeTriosBoard extends JPanel implements MouseListener {
  private final ReadOnlyThreeThriosModel model;
  private final List<Card> hand1;
  private final List<Card> hand2;
  private final Color color1;
  private final Color color2;

  // Track selected card
  private Integer selectedCardIndex;
  private PlayerKey selectedHandOwner;

  public ThreeTriosBoard(ReadOnlyThreeThriosModel model) {
    this.model = model;
    Map<PlayerKey, Player> players = model.getPlayers();
    Player player1 = players.get(PlayerKey.ONE);
    Player player2 = players.get(PlayerKey.TWO);
    this.hand1 = player1.getHand();
    this.hand2 = player2.getHand();
    this.color1 = player1.getColor();
    this.color2 = player2.getColor();

    // Initialize selection tracking
    this.selectedCardIndex = null;
    this.selectedHandOwner = null;

    // Add mouse listener
    addMouseListener(this);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    int totalWidth = getWidth();
    int totalHeight = getHeight();
    int handWidth = totalWidth / 8;
    int boardWidth = totalWidth - (2 * handWidth);

    // Draw hands and board with selection highlight
    drawHand(g2d, hand1, color1, 0, 0, handWidth, totalHeight, PlayerKey.ONE);
    drawBoard(g2d, handWidth, 0, boardWidth, totalHeight);
    drawHand(g2d, hand2, color2, handWidth + boardWidth, 0, handWidth, totalHeight, PlayerKey.TWO);
  }

  private void drawHand(Graphics2D g2d, List<Card> hand, Color color, int x, int y,
      int width, int height, PlayerKey playerKey) {
    int cardHeight = height / hand.size();

    for (int card = 0; card < hand.size(); card++) {
      int cardY = y + (card * cardHeight);

      // Draw card background
      g2d.setColor(color);
      g2d.fillRect(x, cardY, width, cardHeight);

      // Draw selection highlight if this card is selected
      if (selectedCardIndex != null && selectedHandOwner == playerKey
          && selectedCardIndex == card) {
        g2d.setColor(Color.DARK_GRAY);
        g2d.setStroke(new BasicStroke(4));
        g2d.drawRect(x + 2, cardY + 2, width - 4, cardHeight - 4);
        g2d.setStroke(new BasicStroke(1));
      }

      // Draw card border
      g2d.setColor(Color.BLACK);
      g2d.drawRect(x, cardY, width, cardHeight);

      // Draw card values
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
          g2d.setColor(owner.equals(model.getPlayers().get(PlayerKey.ONE))
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

  // Helper method to get card index and player from click coordinates
  private Pair<Integer, PlayerKey> getClickedCard(int mouseX, int mouseY) {
    int totalWidth = getWidth();
    int handWidth = totalWidth / 8;
    int boardWidth = totalWidth - (2 * handWidth);

    // Check left hand (Player 1)
    if (mouseX < handWidth) {
      int cardHeight = getHeight() / hand1.size();
      int cardIndex = mouseY / cardHeight;
      if (cardIndex < hand1.size()) {
        return new Pair<>(cardIndex, PlayerKey.ONE);
      }
    }
    // Check right hand (Player 2)
    else if (mouseX >= handWidth + boardWidth) {
      int cardHeight = getHeight() / hand2.size();
      int cardIndex = mouseY / cardHeight;
      if (cardIndex < hand2.size()) {
        return new Pair<>(cardIndex, PlayerKey.TWO);
      }
    }
    return null;
  }

  // Helper method to get grid coordinates from click
  private Pair<Integer, Integer> getClickedGridCell(int mouseX, int mouseY) {
    int totalWidth = getWidth();
    int handWidth = totalWidth / 8;
    int boardWidth = totalWidth - (2 * handWidth);
    Cell[][] grid = model.getGrid();

    // Calculate grid dimensions
    int rows = grid.length;
    int cols = grid[0].length;
    int cellSize = Math.min(boardWidth / cols, getHeight() / rows);
    int startX = handWidth + (boardWidth - (cellSize * cols)) / 2;
    int startY = (getHeight() - (cellSize * rows)) / 2;

    // Check if click is within grid bounds
    if (mouseX >= startX && mouseX < startX + (cellSize * cols) &&
        mouseY >= startY && mouseY < startY + (cellSize * rows)) {
      int col = (mouseX - startX) / cellSize;
      int row = (mouseY - startY) / cellSize;
      return new Pair<>(row, col);
    }
    return null;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    // Handle card clicks
    Pair<Integer, PlayerKey> cardClick = getClickedCard(e.getX(), e.getY());
    if (cardClick != null) {
      int clickedCardIndex = cardClick.getFirst();
      PlayerKey clickedHandOwner = cardClick.getSecond();

      // If clicking the same card that's selected, deselect it
      if (selectedCardIndex != null && selectedCardIndex == clickedCardIndex
          && selectedHandOwner == clickedHandOwner) {
        selectedCardIndex = null;
        selectedHandOwner = null;
      }
      // Otherwise, select the clicked card
      else {
        selectedCardIndex = clickedCardIndex;
        selectedHandOwner = clickedHandOwner;
      }

      System.out.println("Clicked card " + clickedCardIndex + " in " +
          clickedHandOwner + "'s hand");
      repaint();
      return;
    }

    // Handle grid clicks
    Pair<Integer, Integer> gridClick = getClickedGridCell(e.getX(), e.getY());
    if (gridClick != null) {
      System.out.println("Clicked grid cell at row " + gridClick.getFirst() +
          ", col " + gridClick.getSecond());
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {

    // For next assignments.
  }

  @Override
  public void mouseReleased(MouseEvent e) {

    // For next assignments.
  }

  @Override
  public void mouseEntered(MouseEvent e) {

    // For next assignments.
  }

  @Override
  public void mouseExited(MouseEvent e) {

    // For next assignments.
  }
}

