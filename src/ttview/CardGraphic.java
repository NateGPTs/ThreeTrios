package ttview;

import java.awt.Graphics2D;
import java.util.Map;
import model.card.Direction;

public class CardGraphic implements RenderGraphics {

  private Map<Direction, Integer> values;
  private final int width;
  private final int height;

  public CardGraphic(Map<Direction, Integer> values, int width, int height) {

    this.values = values;
    this.width = width;
    this.height = height;

  }

    @Override
    public void render(Graphics2D g2d) {
      int centerX = width / 2;
      int centerY = height / 2;

      for (Map.Entry<Direction, Integer> entry : values.entrySet()) {
        Direction direction = entry.getKey();
        int value = entry.getValue();

        switch (direction) {
          case NORTH:
            g2d.drawString(String.valueOf(value), centerX - 5, 15);
            break;
          case SOUTH:
            g2d.drawString(String.valueOf(value), centerX - 5, height - 5);
            break;
          case EAST:
            g2d.drawString(String.valueOf(value), width - 15, centerY + 5);
            break;
          case WEST:
            g2d.drawString(String.valueOf(value), 5, centerY + 5);
            break;
        }
      }
    }

}
