package ttview;

import controller.PlayerFeatures;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import model.ReadOnlyThreeTriosModel;
import model.StandardThreeTrios.PlayerKey;
import model.card.Card;
import model.cell.Cell;
import model.player.Player;

/**
 * Represents a graphical board for the ThreeTrios game. Handles rendering of the game state and
 * user interactions.
 */
public class ThreeTriosBoard extends JPanel implements ViewFunctionFeatures {

  private final ReadOnlyThreeTriosModel model;
  private final Color color1;
  private final Color color2;
  private Integer selectedCardIndex;
  private Player viewPlayer;
  private JButton highlightedButton;
  private final List<PlayerFeatures> playerFeatures;
  private final List<CardButton> cardButton1;
  private final List<CardButton> cardButton2;
  private final CellButton[][] boardButtons;


  /**
   * Constructs a new ThreeTriosBoard with the given game model. Initializes the board with player
   * hands, colors, and mouse interaction capabilities.
   *
   * @param model the game model containing the current state of the game
   */
  public ThreeTriosBoard(ReadOnlyThreeTriosModel model) {
    this.model = model;
    Map<PlayerKey, Player> players = model.getPlayers();
    Player player1 = players.get(PlayerKey.ONE);
    Player player2 = players.get(PlayerKey.TWO);
    this.color1 = player1.getColor();
    this.color2 = player2.getColor();

    // Initialize selection tracking
    this.selectedCardIndex = null;
    this.viewPlayer = null;
    this.highlightedButton = null;
    this.playerFeatures = new ArrayList<>();
    Cell[][] grid = model.getGrid();
    this.boardButtons = new CellButton[grid.length][grid[0].length];
    cardButton1 = new ArrayList<>();
    cardButton2 = new ArrayList<>();
    initializeButtons();
    initializeBoardButtons();
  }

  public void setPlayer(Player player) {
    this.viewPlayer = player;
  }

  public void addFeatures(PlayerFeatures playerFeatures) {
    this.playerFeatures.add(playerFeatures);
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
    drawHand(g2d, color1, 0, 0, handWidth, totalHeight, PlayerKey.ONE, cardButton1);
    drawBoard(g2d, handWidth, 0, boardWidth, totalHeight);
    drawHand(g2d, color2, handWidth + boardWidth, 0, handWidth, totalHeight, PlayerKey.TWO
    , cardButton2);
  }

  private void initializeButtons() {
    // Create buttons for both hands
    cardButton1.addAll(createCardButtons(getHand(PlayerKey.ONE), color1));
    cardButton2.addAll(createCardButtons(getHand(PlayerKey.TWO), color2));

  }

  private List<CardButton> createCardButtons(List<Card> hand, Color color) {
    List<CardButton> buttons = new ArrayList<>();

    for (Card card : hand) {
      CardButton button = new CardButton(card, color);
      button.addActionListener(e -> {
        handleCardClick(button, card.getPlayer());
      });
      add(button);
      buttons.add(button);
    }

    return buttons;
  }

  private void drawHand(Graphics2D g2d, Color color, int x, int y,
      int width, int height, PlayerKey playerKey, List<CardButton> buttons) {
    List<Card> hand = getHand(playerKey);
    int cardHeight = height / hand.size();
    int index = 0;

    System.out.println("Drawing hand for " + playerKey + ":");
    for (CardButton button : buttons) {
      button.setBounds(x, y + (index * cardHeight), width, cardHeight);
      button.setIndex(index);
      System.out.println("Card at index " + index + " with values: " +
          button.getCard().getAllAttackVals());
      index++;
    }
  }

  private List<Card> getHand(PlayerKey key) {

    Map<PlayerKey, Player> players = this.model.getPlayers();
    Player player = players.get(key);
    return player.getHand();

  }

  private void handleCardClick(CardButton button, Player player) {

    if(isMyTurn()) {
      // If clicking the already highlighted button
      if (button == highlightedButton) {
        button.setBorder(BorderFactory.createEmptyBorder());
        highlightedButton = null;
        return;
      }

      // If there's already a highlighted button, unhighlight it
      if (highlightedButton != null) {
        highlightedButton.setBorder(BorderFactory.createEmptyBorder());
      }

      // Highlight the new button
      button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
      this.selectedCardIndex = button.getIndex();
      highlightedButton = button;
    }
  }

  private boolean isMyTurn() {
    boolean myTurn = true;

    for(PlayerFeatures feature : playerFeatures) {
      myTurn = feature.isOurTurn();
    }

    return myTurn;
  }


  // Method to create board buttons
  private void initializeBoardButtons() {
    Cell[][] grid = model.getGrid();
    for (int row = 0; row < grid.length; row++) {
      for (int col = 0; col < grid[0].length; col++) {
        Cell cell = grid[row][col];
        CellButton button = new CellButton(cell);
        button.addActionListener(e -> handleCellClick(button));
        add(button);
        boardButtons[row][col] = button;
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
        CellButton button = boardButtons[row][col];
        button.setBounds(startX + (col * cellSize),
            startY + (row * cellSize),
            cellSize, cellSize);
      }
    }
  }

  private void handleCellClick(CellButton button) {

    for (PlayerFeatures feature : playerFeatures) {
      System.out.println("Playing at row " + button.getRow() + ", column " + button.getCol()
          + " with card index " + this.selectedCardIndex);
      feature.notifyPlay(this.viewPlayer, this.selectedCardIndex, button.getRow(), button.getCol());
      System.out.println("Playing at row " + button.getRow() + ", column " + button.getCol()
          + " with card index " + this.selectedCardIndex);
    }
  }

  public void updateView() {
    // Clear all existing buttons
    removeAll();
    cardButton1.clear();
    cardButton2.clear();

    // Clear the board buttons
    for (int i = 0; i < boardButtons.length; i++) {
      for (int j = 0; j < boardButtons[0].length; j++) {
        boardButtons[i][j] = null;
      }
    }

    highlightedButton = null;  // Reset highlight state
    this.selectedCardIndex = null;

    // Reinitialize buttons with new state
    initializeButtons();     // Recreates hand buttons with new hands
    initializeBoardButtons(); // Recreates board buttons with new grid

    // Force a repaint to show new state
    revalidate();
    repaint();
  }



}

