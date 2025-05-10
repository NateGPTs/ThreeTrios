package ttview;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JButton;
import model.card.Card;

public class CardButton extends JButton {

  private final Card card;
  private Integer index;

  public CardButton(Card card, Color color) {
    this.card = card;
    this.index = null;
    setBorderPainted(true);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    int width = getWidth();
    int height = getHeight();
    RenderGraphics graphic = new CardGraphic(card.getAllAttackVals(), width, height);
    setBackground(this.card.getColor());
    graphic.render(g2d);
  }


  public void setIndex(int index) {

    this.index = index;
  }

  public int getIndex() {
    return this.index;
  }

  public Card getCard() {
    return this.card;
  }


}
