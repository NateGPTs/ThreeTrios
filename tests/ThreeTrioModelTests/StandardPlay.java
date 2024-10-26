package ThreeTrioModelTests;

import static org.junit.Assert.assertEquals;

import Model.Card.Card;
import Model.Card.Direction;
import Model.Card.ThreeTrioCards;
import Model.Cell.Cell;
import Model.CommandPlayToGrid.GridCommands;
import Model.Grid.GridCreator;
import Model.ModelPlayer.Player;
import Model.ModelPlayer.ThreeTriosPlayer;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;

public class StandardPlay {

  HashMap<Direction, Integer> AttackVal0;
  HashMap<Direction, Integer> AttackVal1;
  HashMap<Direction, Integer> AttackVal2;
  HashMap<Direction, Integer> AttackVal3;
  Card card0;
  Card card1;
  Card card2;
  ArrayList<Card> deck1;
  GridCommands testPlay;
  Cell[][] grid;
  Player player1;
  Player player2;



  @Before
  public void setUp() {

    GridCreator gridCreator = new GridCreator();
    grid = gridCreator.create(3, 3);

    player2 = new ThreeTriosPlayer(Color.green, new ArrayList<Card>());
    AttackVal0 = new HashMap<Direction, Integer>();
    AttackVal0.put(Direction.NORTH, 1);
    AttackVal0.put(Direction.SOUTH, 1);
    AttackVal0.put(Direction.EAST, 1);
    AttackVal0.put(Direction.WEST, 1);
    AttackVal1 = new HashMap<Direction, Integer>();
    AttackVal1.put(Direction.NORTH, 1);
    AttackVal1.put(Direction.SOUTH, 2);
    AttackVal1.put(Direction.EAST, 3);
    AttackVal1.put(Direction.WEST, 4);
    AttackVal2 = new HashMap<Direction, Integer>();
    AttackVal2.put(Direction.NORTH, 4);
    AttackVal2.put(Direction.SOUTH, 5);
    AttackVal2.put(Direction.EAST, 6);
    AttackVal2.put(Direction.WEST, 4);

  }

  @Test
  public void testInfect2RowsAway() {


    card0 = new ThreeTrioCards(AttackVal0, player2);
    card1 = new ThreeTrioCards(AttackVal1, player2);
    card2 = new ThreeTrioCards(AttackVal2);

    deck1 = new ArrayList<>();
    deck1.add(card2);

    player1 = new ThreeTriosPlayer(Color.red, deck1);
    grid[1][1].addCard(card1);
    grid[1][2].addCard(card0);

    testPlay = new Model.CommandPlayToGrid.StandardPlay(grid);

    testPlay.executePlay(1, 0, 0, player1);


    assertEquals(grid[1][0].getCard(), card2);
    assertEquals(grid[1][1].getCard(), card1);
    assertEquals(grid[1][2].getCard(), card0);

    assertEquals(card1.getPlayer(), player1);
    assertEquals(card2.getPlayer(), player1);

  }

  @Test
  public void middleInfectsAll() {

    AttackVal3 = new HashMap<Direction, Integer>();
    AttackVal3.put(Direction.NORTH, 10);
    AttackVal3.put(Direction.SOUTH, 10);
    AttackVal3.put(Direction.EAST, 10);
    AttackVal3.put(Direction.WEST, 10);

    card0 = new ThreeTrioCards(AttackVal0, player2);
    card1 = new ThreeTrioCards(AttackVal1, player2);
    card2 = new ThreeTrioCards(AttackVal2, player2);
    Card card3 = new ThreeTrioCards(AttackVal0, player2);
    Card card4 = new ThreeTrioCards(AttackVal3);

    deck1 = new ArrayList<Card>();
    deck1.add(card4);

    player1 = new ThreeTriosPlayer(Color.red, deck1);
    grid[1][0].addCard(card0);
    grid[0][1].addCard(card1);
    grid[1][2].addCard(card2);
    grid[2][1].addCard(card3);
    testPlay = new Model.CommandPlayToGrid.StandardPlay(grid);
    testPlay.executePlay(1, 1, 0, player1);

    assertEquals(grid[1][0].getCard(), card0);
    assertEquals(grid[0][1].getCard(), card1);
    assertEquals(grid[1][2].getCard(), card2);
    assertEquals(grid[1][1].getCard(), card4);

    assertEquals(grid[1][0].getCard().getPlayer(), player1);
    assertEquals(grid[0][1].getCard().getPlayer(), player1);
    assertEquals(grid[1][2].getCard().getPlayer(), player1);
    assertEquals(grid[1][1].getCard().getPlayer(), player1);


  }


  @Test
  public void weakerCardDoesNotInfectStrongerOnes() {

    AttackVal3 = new HashMap<Direction, Integer>();
    AttackVal3.put(Direction.NORTH, 1);
    AttackVal3.put(Direction.SOUTH, 1);
    AttackVal3.put(Direction.EAST, 1);
    AttackVal3.put(Direction.WEST, 1);

    card0 = new ThreeTrioCards(AttackVal0, player2);
    card1 = new ThreeTrioCards(AttackVal1, player2);
    card2 = new ThreeTrioCards(AttackVal2, player2);
    Card card3 = new ThreeTrioCards(AttackVal0, player2);
    Card card4 = new ThreeTrioCards(AttackVal3);

    deck1 = new ArrayList<Card>();
    deck1.add(card4);

    player1 = new ThreeTriosPlayer(Color.red, deck1);
    grid[1][0].addCard(card0);
    grid[0][1].addCard(card1);
    grid[1][2].addCard(card2);
    grid[2][1].addCard(card3);
    testPlay = new Model.CommandPlayToGrid.StandardPlay(grid);
    testPlay.executePlay(1, 1, 0, player1);

    assertEquals(grid[1][0].getCard(), card0);
    assertEquals(grid[0][1].getCard(), card1);
    assertEquals(grid[1][2].getCard(), card2);
    assertEquals(grid[1][1].getCard(), card4);

    assertEquals(grid[1][0].getCard().getPlayer(), player2);
    assertEquals(grid[0][1].getCard().getPlayer(), player2);
    assertEquals(grid[1][2].getCard().getPlayer(), player2);
    assertEquals(grid[1][1].getCard().getPlayer(), player1);


  }


}
