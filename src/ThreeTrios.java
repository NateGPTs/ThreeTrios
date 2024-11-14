import java.util.List;
import model.ModelUtils;
import model.StandardThreeTrios;
import model.ThreeTriosModel;
import model.card.Card;
import model.cell.Cell;
import ttview.ThreeTriosView;

public class ThreeTrios {


  public static void main(String[] args) {

    ModelUtils modelUtils = new ModelUtils();
    Cell[][] grid = modelUtils.createBoard("FourByFourBoard");
    ThreeTriosModel model = new StandardThreeTrios(grid);
    List<Card> exampleDeck = modelUtils.createDeck("22CardDeck");
    model.startGame(exampleDeck);
    ThreeTriosView view = new ThreeTriosView(model);
    view.setVisible(true);

  }

}
