package ttview;

import controller.PlayerFeatures;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.ThreeTriosModel;
import model.player.Player;

/**
 * The main window frame for the ThreeTrios game. Provides the primary user interface container and
 * manages the game's visual components. This class extends JFrame to create the main application
 * window and implements ActionListener for handling UI events.
 */
public class ThreeTriosView extends JFrame implements ViewToControllerFeature {

  private ThreeTriosModel model;
  private final ThreeTriosBoard panel;
  private Player player;

  /**
   * Constructs a new ThreeTriosView window. Initializes the main game window with a board panel and
   * configures basic window properties. The window is centered on the screen with a default size of
   * 800x600 pixels.
   *
   * @param model the ThreeTriosModel that this view will represent and interact with
   */
  public ThreeTriosView(ThreeTriosModel model) {
    setTitle("ThreeTriosGame");
    setLayout(new BorderLayout());

    ThreeTriosBoard boardPanel = new ThreeTriosBoard(model);
    this.panel = boardPanel;
    add(boardPanel, BorderLayout.CENTER);
    setSize(800, 600);
    setLocationRelativeTo(null);
  }

  @Override
  public void addListener(PlayerFeatures feature) {
    this.panel.addFeatures(feature);
  }

  @Override
  public void setPlayer(Player player) {
    this.player = player;
    this.panel.setPlayer(player);
  }

  @Override
  public void showPanel(String message, String title) {
    JOptionPane.showMessageDialog(
        this,
        message,
        title,
        JOptionPane.INFORMATION_MESSAGE
    );
  }

  @Override
  public void setVisible(Boolean visible) {
    super.setVisible(visible);
  }


  @Override
  public void updateView() {
    panel.updateView();
  }
}
