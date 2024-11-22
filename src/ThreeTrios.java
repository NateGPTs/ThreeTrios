import controller.ThreeTriosController;
import java.util.List;
import java.util.Map;
import model.ModelUtils;
import model.StandardThreeTrios;
import model.StandardThreeTrios.PlayerKey;
import model.ThreeTriosModel;
import model.card.Card;
import model.cell.Cell;
import model.player.Player;
import ttview.ThreeTriosView;
import ttview.ViewToControllerFeature;

/**
 * Main entry point for the ThreeTrios game application. This class initializes and starts the game
 * by creating the model, loading the game board and deck configurations, and launching the
 * graphical user interface.
 */
public class ThreeTrios {

  /**
   * The main method that starts the ThreeTrios game. Performs the following initialization steps:
   * 1. Creates a utility instance for loading game configurations 2. Loads the game board
   * configuration from "FourByFourBoard" 3. Initializes the game model with the loaded board 4.
   * Loads the card deck from "22CardDeck" 5. Starts the game with the loaded deck 6. Creates and
   * displays the game window
   *
   * @param args command line arguments (not used)
   */
  public static void main(String[] args) {

    ModelUtils modelUtils = new ModelUtils();
    Cell[][] grid = modelUtils.createBoard("FourByFourBoard");
    ThreeTriosModel model = new StandardThreeTrios(grid);
    List<Card> exampleDeck = modelUtils.createDeck("22CardDeck");
    model.startGame(exampleDeck);
    ViewToControllerFeature playerOneView = new ThreeTriosView(model);
    ViewToControllerFeature playerTwoView = new ThreeTriosView(model);
    Map<PlayerKey, Player> players = model.getPlayers();
    Player player1 = players.get(PlayerKey.ONE);
    Player player2 = players.get(PlayerKey.TWO);
    ThreeTriosController controllerOne = new ThreeTriosController(model, player1, playerOneView);
    ThreeTriosController controllerTwo = new ThreeTriosController(model, player2, playerTwoView);

  }

}
