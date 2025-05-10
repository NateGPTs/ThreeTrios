package ttview;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JButton;
import model.cell.Cell;

public class CellButton extends JButton {

  private final Cell cell;

  public CellButton(Cell cell) {
    this.cell = cell;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    if (cell.isHole()) {
      setBackground(Color.YELLOW);
    } else if (cell.isEmpty()) {
      setBackground(Color.GRAY);
    } else {
      int width = getWidth();
      int height = getHeight();
      Graphics2D g2d = (Graphics2D) g;
      RenderGraphics graphics = new CardGraphic(cell.getAllAttackVals(), width, height);
      setBackground(this.cell.getColor());
      graphics.render(g2d);
    }

    setOpaque(true);
    setBorderPainted(true);
  }



  public Cell getCell() {
    return cell;
  }

  public int getRow() {

    return cell.getRow();

  }

  public int getCol() {

    return cell.getCol();
  }


}
