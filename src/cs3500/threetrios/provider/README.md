# Three Trios Game

## HOW TO USE .JAR
Our program takes 2 command line arguments: 
1. Player type for the red player
2. Player type for the blue player

Player type must be either "human" for a human player or a strategy name for computer.

Here are the names of available strategies:
- "cornerstrategy" - Go for corners
- "maxflipstrategy" - Go for maximum number of flips

Example: Red robot with max flip strategy vs blue human
```
java -jar Assignment7.jar maxflipstrategy human
```

Example: Red human vs blue human
```
java -jar Assignment7.jar human human
```

Example: Red robot with max flip strategy vs blue robot with corner strategy
```
java -jar Assignment7.jar maxflipstrategy cornerstrategy
```


## Changes for part 3
**New classes for part 3:**  
- `HumanPlayer`: Represents a human player in the Three Trios game.
- `MachinePlayer`: Represents a computer-controlled player in the Three Trios game.
`HumanPlayer` and `MachinePlayer` live in the player package and implements the `Player` interface. 
This means that the `Player` interface is generic to both human and machine players.
- `TriosControllerImp`: Controls the interaction between a player, their view, and the game model in Three Trios.
- `ReadOnlyTriosModelImp`: An implementation of the read-only model, allowing the game state to be observed without modification.
- `PlayerFactory`: A factory for creating players we used to implement our command line functionality
- `Utilities`: Located in the threetrios package,  this class provides helper methods for improved error handling and input validation.


**New interfaces:**
- `Player`: Represents a player in the game
- `TriosController`: Handles interactions for the Three Trios Game
- `PlayerListener`: Listens to the player to know when the player has moved
- `ModelListener`: Listens to the model to know when the turn has changed or the game ended
- `ViewListener`: Listens to the view to know when a hand or grid card have been selected
- `ControllerListener`: Listens to the controller to know when the player changed or game is over

A player package was added to create a clear separation between move generation and game control, 
using the observer pattern to handle both human and computer player.

A utilities class was added for better error handling with the static method
`public static void betterRequireNonNull(Object obj, String message)`.
This method was added so instead of throwing a NullPointerException when a null argument is passed in,
we throw an IllegalArgumentException.

**Refer to the documentation in the code for further specifications of these interfaces and classes.**


### How to zip
zip -r hw07 assets guiScreenshots src test Assignment7.jar corner-transcript.txt README.md

### Changes for part 2
The `getAttackPowers()` method returns an enum instead of an int.

The `getCurrentPlayer()` method returns a Color instead of a hand.

`public boolean equals(Object other)` was added to the Position class.

Additionally, the methods
````java
public Color getWinner();
public List<Card> getPlayerHand(Color color);
public int getBoardWidth();
public int getBoardHeight();
public Cell getCellAt(int row, int col);
public int getScore(Color playerColor);
````
were added into the ReadOnlyTrios2Model interface. 

Read the Java documentation to learn about the functionality.

##### Reasons the methods were missing beforehand:

`getWinner()` because there was no need to check for winners mid-game for part 1

`getPlayerHand(Color color)` because part 1 focused on individual card actions rather 
than overall hand management.

`getBoardWidth()` and `getBoardHeight()` because the board's dimensions were assumed to be fixed,
so there was no need for dynamic size retrieval.

`getCellAt(int row, int col)` because accessing individual cells directly wasn't necessary in part 1.

`getScore(Color playerColor)` because there was no need to get scored mid-game in part 1.

## Overview

**Three Trios** is a two-hand, turn-based card game on a customizable grid.

Players place cards on the board to capture opponents' cards by comparing directional attack values.
The hand with the most cards added by the cards in their hand wins.

This codebase manages game setup, turn-based mechanics, and capturing logic.
This is an ongoing project which will feature a GUI in the future.

## Game Configuration Files

The game relies on two configuration files:

1. **Card Configuration File**: Lists each card’s name and attack values in four directions.
    - Example:
      ```
      dragon 8 3 4 5
      mouse 1 3 1 1
      ```
    - Note that the card's names must be unique, but the attack values do not.


2. **Grid Configuration File**: Defines grid dimensions and cell types (card cells or holes).
    - Example:
      ```
      3 5
      CCCCC
      CCCCC
      CXXXX
      ```
    - Each character represents a cell: `C` for card cells, `X` for holes.
    - Note that the number of card cells must be odd.

## Quick Start

To start the game, initialize `ThreeTriosModel` and load configuration files.
Here is an example:

```java
ThreeTriosModel model = new ThreeTriosBasicModel();
// startGame() takes in a boolean for if you want to shuffle the cards
model.startGame(true, "path/to/card_config.txt", "path/to/grid_config.txt");

// place a card at position (0, 0) from 2nd card index in hand
model.playToCell(0, 0, 2);
```

## Key Components and Subcomponents

### ThreeTriosModel

- The model is the workhorse that powers the game. It tracks turns, grid state, winners, and is
  essentially the back-bone of the game. It relies on several subcomponents that I will explain below.

#### *BattleRule*

- A battle rule represents battle logic after placing a card. It is a command that determines whether
  a battle resulted in win, loss, or tie

#### *Card*

- Cards are named and have directional attack values. Each card also has an owner represented as a color.

#### *Cell*

- A cell represents a part of our grid. Cells contain the logic for blocking areas on the grid. Cells can
  contain a card.

#### *Hand*

- The hand class represents a colored list of cards with special operations. Hands are used to track
  cards that have not been placed on the board but are part of the game.

#### *TriosBoard*

- The TriosBoard class is used for all grid-related operations. It consists of a grouping of cells, and has logic to
  place cells, find neighbors of cells, etc. It uses a Position class to encapsulate coordinates. The model composes
  TriosBoard and leverages its operations.

### *ThreeTriosView*

- The view uses the model to get a view only version of the grid, and displays this grid along with the active
  players' hand. It can render data to an appendable or be printed directly with toString.

## Source Organization

Please refer to this map to find the components discussed above. For simplicity, only interfaces have been included.

```
-- src/
-- -- model/
-- -- -- BattleRule
-- -- -- Card
-- -- -- Cell
-- -- -- Hand
-- -- -- ThreeTriosModel
-- -- -- TriosBoard
-- -- view/
-- -- -- ThreeTriosView
```


