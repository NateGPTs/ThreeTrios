HW5:

OVERVIEW:
-----------------------------------------------------------------------
The Three Trios Game implements a strategic board game in which
players compete to dominate a grid of cells by playing distinct cards.
The game combines strategy and luck, asking players to navigate their
cards to victory by forming sets of three in a variety of configurations.
This project is intended for developers and hobbyists interested in
board game design, game mechanics, and Java programming!

You must be familiar with Java programming and back end coding to play this game.
The design enables for the inclusion of new card kinds, player roles,
and game rules. Customization is encouraged, but the essential gameplay
principles should be kept intact.

Java Development Kit (JDK) 8 or higher is required to play. (same with
familiarity in JUnit tests if you want to test your game)
-----------------------------------------------------------------------

QUICK START:
-----------------------------------------------------------------------
You can initialize the game model and view and then show the game state.
Below is a simple example on how to set up.

StandardThreeTrios model = new StandardThreeTrios(new ThreeTrioCell[3][3]);
ThreeTrioTextView view = new ThreeTrioTextView(model);

model.startGame(new ArrayList<>());
System.out.println(view.display());
-----------------------------------------------------------------------

KEY COMPONENTS:
-----------------------------------------------------------------------
Model (StandardThreeTrios):

This is the main model class which managed the game state, rules, and
player actions. It contains methods to start the game, validate game
conditions, and handle player moves.

View (ThreeTrioTextView):

Renders the game state in a readable way by communicating with the model to
get the current game state and display it.

Player (ThreeTriosPlayer):

Represents a player in the game, holding their color and their cards.
Each player interacts with the model to affect the game state and who wins/loses.

Grid (ThreeTrioCell):

This represents the game board and consists of cells that can hold cards or be empty.
The grid determines where players can place their cards.
-----------------------------------------------------------------------

KEY SUBCOMPONENTS:
-----------------------------------------------------------------------
Card (ThreeTrioCards):

Represents the cards used by players with associated attack values for
different directions. Cards have unique effects to manipulate the game.

Cell (ThreeTrioCell):

A single cell is a unit in the game grid. Each cell can be empty or contain a card.
Cells manage their state, including whether they are occupied and which card is placed in them.

Direction Enum:

Shows the possible directions for card attacks, aiding in the implementation
of game rules and card effects.
-----------------------------------------------------------------------

SOURCE ORGANIZATION:
-----------------------------------------------------------------------
package Model;

// Contains the Model utils (ModelUtils.java), the standard three trios class
(StandardThreeTrios.java), and it's interface (ThreeTriosModel)

package Model.Card;

// Contains classes related to the game cards, including Card, ThreeTrioCards, and Direction.

package Model.Cell;

// Contains classes related to the grid cells, including Cell and ThreeTrioCell.

package Model.CommandPlayToGrid;

// Contains classes related to the grid commands, including StandardPlay and GridCommands.

package View;

// Contains the view-related class ThreeTrioTextView, responsible for displaying the game state.

package ThreeTrioModelTests;

// Contains tests for the Three Trio Model

package ThreeTrioViewTests;

// Contains tests for the Three Trio View
-----------------------------------------------------------------------


HW6:
----

KEY CHANGES:
----

-Added a GUI view with swing.
-Created strategies and made the playtocpu method in the model work.
-Created a mock model for testing for these strategies.
-Created coordinate class to represent coordinates.

-------------------------

Command line arguments for running:

Main supports the following arguments:

mostflips
corners
human

So do the typical javac run, and the two arguments that follows it should be the strategy: mostflips or corners representing a AI CPU, or a human as "human".

EX:
mostflips human
human human
mostflips mostflips
corners corners

------------------------

What changed:


The three trios view is revamped to allow for the controller to listen in. 
Created the controller and interface to listen into players and model.
Player now has strategies and plays them if the player is a cpu.



