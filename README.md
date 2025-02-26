# Three Trios Card Game

## Overview
Three Trios is a 2-player turn-based card game where players place cards onto a grid of cells to battle opposing cards and take control of them. The goal is to have the most cards under your control when all grid spaces are filled.

This codebase is implemented in **Java**, using **object-oriented programming** principles and **JUnit** for testing.

## Quick Start

### 1. Prepare Configuration Files
Preset grids and cards can be defined using configuration files. Example configurations are provided in the `Configs` folder.

#### Grid Configuration Example:
```
{Rows Columns}{Row1}{Row2}{etc}
3 3
CCC
CCC
CCC
```

#### Card Configuration Example:
```
{CardName North South East West}{etc}
CARD1 1 1 1 1
CARD2 2 2 2 2
CARD3 3 3 3 3
...
```

### 2. Initialize Game Components
Use the `ConfigReader` class to load grids and cards from configuration files:
```java
// Create a grid from the configuration file
Grid grid1 = ConfigReader.readGrid("Configs/NoHolesGrid:9C0H");

// Create a list of cards from the configuration file
List<Card> cards1 = ConfigReader.readCards("Configs/CardConfig:10");

// Initialize the game controller
GameController controller1 = new GameController(grid1, cards1);

// Start the game loop
controller1.runGame();
```

### 3. Start the Game
The game controller is not fully implemented yet, but running the `main` method in `ThreeTrios` will display the initial game state.

---

## Project Structure
The codebase follows an **MVC (Model-View-Controller)** structure:

```
/Configs   - Example configuration files
/src       - Main source code
  /controller  - Handles game logic and user interactions
    - GameController.java
  /model       - Defines core game mechanics
    - Card.java
    - Player.java
    - Grid.java
    - ThreeTriosGameModel.java
  /view        - Converts game state into a user-friendly output
    - TextualView.java
  ThreeTrios.java  - Entry point for running the game
/test      - Unit tests
  - ControllerTest.java
  - ModelTest.java
  - ViewTest.java
```

---

## Player Interface & AI Implementation
A future AI component will allow the game to be played against a computer opponent. The AI will:
- Choose a card from its hand.
- Decide the best position on the grid.
- Optimize moves based on card strengths and opponent positions.
- Implement different difficulty levels based on decision-making strategies.

---

## Recent Updates

### **Part 2 - Read-Only Model for View**
- Added `ReadonlyThreeTriosModel` to separate read-only operations from game logic.
- New methods:
  - `getGridSize()`, `getGridRows()`, `getGridCols()` for retrieving grid details.
  - `cellContents()`, `cellOwner()` for checking cell state.
  - `legalMove()`, `cardFlips()`, `playerScore()` for game mechanics.

### **Part 3 - Mock Classes for Testing**
- Implemented `MockView`, `MockPlayer`, `MockModel`, and `MockController`.
- Adjusted methods for unit testing using string-based logging.

### **Part 4 - Final Implementation**
- All game features have been implemented.
- The player using the **red** deck always goes first.
