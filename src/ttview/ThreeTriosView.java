package ttview;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;
import model.StandardThreeTrios.PlayerKey;
import model.ThreeTriosModel;
import model.player.Player;

public class ThreeTriosView extends JFrame implements ActionListener {

  ThreeTriosModel model;

  public ThreeTriosView(ThreeTriosModel model) {
    this.model = model;
    setTitle("ThreeTriosGame");
    setLayout(new BorderLayout());

    JPanel boardPanel = new ThreeTriosBoard(model);
    add(boardPanel, BorderLayout.CENTER);

    setSize(800, 600);
    setLocationRelativeTo(null);
  }


  @Override
  public void actionPerformed(ActionEvent e) {

  }



}
