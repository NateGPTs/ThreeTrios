package ttview;

import java.awt.geom.Path2D;

public class DrawCard extends Path2D.Double {

  private double x, y, width, height;

  public DrawCard(double x, double y, double width, double height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;

    moveTo(x, y);
    lineTo(x + width, y);
    lineTo(x + width, y + height);
    lineTo(x, y + height);
    lineTo(x, y);
    closePath();
  }

}
