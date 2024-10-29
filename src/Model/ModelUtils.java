package Model;

import Model.Card.Card;
import Model.Card.Direction;
import Model.Card.ThreeTrioCards;
import Model.Cell.Cell;
import Model.Cell.ThreeTrioCell;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ModelUtils {


  public Cell[][] createBoard(String configName) {

    String path = "ConfigDatabase" + File.separator + configName;
    File file = new File(path);
    Cell[][] board;
    board = null;

    try {

      FileReader fileReader = new FileReader(file);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String[] dimensions = bufferedReader.readLine().split(" ");
      int rows = Integer.parseInt(dimensions[0]);
      int cols = Integer.parseInt(dimensions[1]);
      board = new Cell[rows][cols];

      for(int row = 0; row < rows; row++) {

        char[] line = bufferedReader.readLine().toCharArray();

        for(int col = 0; col < cols; col++) {

          if(line[col] == 'C') {

            board[row][col] = new ThreeTrioCell(row, col);

          } else {

            board[row][col] = new ThreeTrioCell(true, row, col);

          }

        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

    if(board == null) {
      throw new IllegalStateException("Board could not be created");
    }

    return board;
  }


  private HashMap<Direction, Integer> createAttackVal(String[] card) {

    HashMap<Direction, Integer> attackVal = new HashMap<Direction, Integer>();

    try {
      attackVal.put(Direction.NORTH, Integer.parseInt(card[1]));
      attackVal.put(Direction.SOUTH, Integer.parseInt(card[2]));
      attackVal.put(Direction.EAST, Integer.parseInt(card[3]));
      attackVal.put(Direction.WEST, Integer.parseInt(card[4]));
    } catch (Exception e) {
      throw new RuntimeException("Invalid Card Format");
    }

    return attackVal;
  }


  public ArrayList<Card> createDeck(String configName) {

    String path = "ConfigDatabase" + File.separator + configName;
    File file = new File(path);
    ArrayList<Card> deck = new ArrayList<>();

    try {

      FileReader fileReader = new FileReader(file);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String line;

      while((line = bufferedReader.readLine()) != null) {

        String[] card = line.split(" ");
        HashMap<Direction, Integer> attackVal = createAttackVal(card);
        Card newCard = new ThreeTrioCards(attackVal);
        deck.add(newCard);

      }

    } catch (IOException e) {
      e.printStackTrace();
    }

    return deck;
  }


}
