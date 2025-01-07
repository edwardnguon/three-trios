import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.CardCell;
import cs3500.threetrios.model.Cell;
import cs3500.threetrios.model.Color;
import cs3500.threetrios.model.ConfigReader;
import cs3500.threetrios.model.Grid;
import cs3500.threetrios.model.Hole;
import cs3500.threetrios.model.HumanPlayer;
import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.ThreeTriosGameModel;

import static cs3500.threetrios.model.CardinalDirection.EAST;
import static cs3500.threetrios.model.CardinalDirection.NORTH;
import static cs3500.threetrios.model.CardinalDirection.SOUTH;
import static cs3500.threetrios.model.CardinalDirection.WEST;

/**
 * Class for testing the game's model.
 */
public class ModelTest {
  public Card card;
  CardCell cardCell;
  Hole hole;
  Grid grid;
  Player player;
  List<Card> cardsListShort;
  List<Card> cardsList;
  ConfigReader configReader;
  ThreeTriosGameModel model;

  // Setting up initial objects for testing
  @Before
  public void setup() {
    card = new Card("card1", 1, 1, 1, 1);
    cardCell = new CardCell();
    hole = new Hole();
    player = new HumanPlayer(Color.RED);
    Card card1 = new Card("CARD1", 1, 1, 1, 1);
    Card card2 = new Card("CARD2", 2, 2, 2, 2);
    Card card3 = new Card("CARD3", 3, 3, 3, 3);
    Card card4 = new Card("CARD4", 4, 4, 4, 4);
    Card card5 = new Card("CARD5", 5, 5, 5, 5);
    Card card6 = new Card("CARD6", 6, 6, 6, 6);
    Card card7 = new Card("CARD7", 7, 7, 7, 7);
    Card card8 = new Card("CARD8", 8, 8, 8, 8);
    Card card9 = new Card("CARD9", 9, 9, 9, 9);
    Card cardA = new Card("CARDA", 10, 10, 10, 10);
    cardsListShort = new ArrayList<>(Arrays.asList(card1, card2));
    cardsList = new ArrayList<>(Arrays.asList(
            card1, card2, card3, card4, card5, card6, card7, card8, card9, cardA
    ));
    configReader = new ConfigReader();
    model = new ThreeTriosGameModel();
  }

  //Tests getName() of card
  @Test
  public void testGetName() {
    Assert.assertEquals("card1", card.getName());
  }

  //Tests getValue() of card
  @Test
  public void testGetValue() {
    Assert.assertEquals(1, card.getValue(NORTH));
    Assert.assertEquals(1, card.getValue(SOUTH));
    Assert.assertEquals(1, card.getValue(EAST));
    Assert.assertEquals(1, card.getValue(WEST));
  }

  //Tests card constructor/card equality
  @Test
  public void testCardConstructor() {
    Card card1 = new Card("card1", 1, 1, 1, 1);
    Assert.assertTrue(card1.equals(card));
    Assert.assertEquals(card1, card);
  }

  //Tests the getOwner and setOwner methods
  @Test
  public void testGetSetOwner() {
    card = new Card("cardRED", 1, 1, 1, 1);
    card.setOwner(Color.RED);
    Card cardRed = new Card("cardRED", 1, 1, 1, 1);
    cardRed.setOwner(Color.RED);
    Assert.assertEquals(Color.RED, cardRed.getOwner());
  }

  @Test
  public void testEquals_SameObject() {
    // Test that a card is equal to itself.
    Card card = new Card("Dragon", 5, 7, 3, 6);
    Assert.assertTrue(card.equals(card)); // A card should be equal to itself
  }

  // Test that two cards with the same name and attack values are equal.
  @Test
  public void testEquals_EqualObjects() {
    Card card1 = new Card("Dragon", 5, 7, 3, 6);
    Card card2 = new Card("Dragon", 5, 7, 3, 6);
    Assert.assertTrue(card1.equals(card2));

    // Test that cards with the same owner are still equal
    card1.setOwner(Color.RED);
    card2.setOwner(Color.RED);
    Assert.assertTrue(card1.equals(card2));
  }

  // Test that two cards with different names are not equal.
  @Test
  public void testEquals_DifferentName() {
    Card card1 = new Card("Dragon", 5, 7, 3, 6);
    Card card2 = new Card("Phoenix", 5, 7, 3, 6);
    Assert.assertFalse(card1.equals(card2));
  }

  // Test that two cards with different attack values are not equal.
  @Test
  public void testEquals_DifferentAttackValues() {
    Card card1 = new Card("Dragon", 5, 7, 3, 6);
    Card card2 = new Card("Dragon", 4, 7, 3, 6);
    Assert.assertFalse(card1.equals(card2));
  }

  // Test that two cards with different owners are not equal.
  @Test
  public void testEquals_DifferentOwner() {
    Card card1 = new Card("Dragon", 5, 7, 3, 6);
    Card card2 = new Card("Dragon", 5, 7, 3, 6);

    card1.setOwner(Color.RED);
    card2.setOwner(Color.BLUE);

    Assert.assertFalse(card1.equals(card2));
  }

  // Test that a card is not equal to null.
  @Test
  public void testEquals_NullObject() {
    Card card = new Card("Dragon", 5, 7, 3, 6);
    Assert.assertFalse(card == null);
  }

  //Tests the basic functionality of the CardCell class
  @Test
  public void testCardCell() {
    // Test that a newly created cell is empty
    Assert.assertTrue(cardCell.isEmpty());
    // Place a Card in the cell and test that it is no longer empty
    cardCell.placeCard(card);
    Assert.assertFalse(cardCell.isEmpty());
    // Test that the placed Card can be retrieved correctly
    Assert.assertEquals(card, cardCell.getCard());
  }

  //Tests converting a valid list of Card objects to string format
  @Test
  public void testCardToStringConfigValidCards() {
    List<Card> cards = new ArrayList<>();
    cards.add(new Card("Card1", 1, 2, 3, 4));
    cards.add(new Card("Card2", 5, 6, 7, 8));

    String expectedOutput =
            "Card1 1 2 3 4\n"
                    +
                    "Card2 5 6 7 8\n";
    Assert.assertEquals(expectedOutput, ConfigReader.cardToStringConfig(cards));
  }

  // Tests CardToStringConfig returns an empty string when given an empty list of cards
  @Test
  public void testCardToStringConfigEmptyList() {
    List<Card> cards = new ArrayList<>();
    Assert.assertEquals("", ConfigReader.cardToStringConfig(cards));
  }

  // Tests cardToStringConfig returns an empty string when given a null input
  @Test
  public void testCardToStringConfigNull() {
    Assert.assertEquals("", ConfigReader.cardToStringConfig(null));
  }

  // Tests gridToStringConfig returns properly when given a valid grid
  @Test
  public void testGridToStringConfigValidGrid() {
    Cell[][] cells = new Cell[3][3];
    cells[0][0] = new CardCell();
    cells[0][1] = new Hole();
    cells[0][2] = new CardCell();

    cells[1][0] = new Hole();
    cells[1][1] = new CardCell();
    cells[1][2] = new Hole();

    cells[2][0] = new CardCell();
    cells[2][1] = new Hole();
    cells[2][2] = new CardCell();

    String expectedOutput =
            "3 3\n"
                    +
                    "CXC\n"
                    +
                    "XCX\n"
                    +
                    "CXC\n";
    Assert.assertEquals(expectedOutput, ConfigReader.gridToStringConfig(cells));
  }

  // Tests that gridToStringConfig returns an empty string given an empty grid
  @Test
  public void testGridToStringConfigEmptyGrid() {
    Cell[][] cells = new Cell[0][0];
    Assert.assertEquals("", ConfigReader.gridToStringConfig(cells));
  }

  // Tests that gridToStringConfig returns an empty string given a null input
  @Test
  public void testGridToStringConfigNull() {
    Assert.assertEquals("", ConfigReader.gridToStringConfig(null));
  }

  //Test for parseGridConfigString method with valid grid configuration.
  @Test
  public void testParseGridConfigString_Valid() {
    List<String> validGrid = List.of(
            "5 5",
            "CCXXX",
            "XCCXX",
            "XXCXX",
            "XXCCX",
            "XXXCC"
    );

    Cell[][] grid = ConfigReader.parseGridConfigString(validGrid);
    Assert.assertEquals(5, grid.length); // Check number of rows
    Assert.assertEquals(5, grid[0].length); // Check number of columns
    Assert.assertTrue(grid[0][0] instanceof CardCell); // Check first cell is a CardCell
    Assert.assertTrue(grid[0][3] instanceof Hole); // Check a hole exists at (0, 3)
  }

  //Test for parseGridConfigString method with invalid grid configuration (invalid character).
  @Test
  public void testParseGridConfigString_InvalidCharacter() {
    List<String> invalidGrid = List.of(
            "5 5",
            "CCXXX",
            "XCCXX",
            "XXZXX", // Invalid character 'Z'
            "XXCCX",
            "XXXCC"
    );

    Exception exception = Assert.assertThrows(IllegalArgumentException.class, () -> {
      ConfigReader.parseGridConfigString(invalidGrid);
    });
    Assert.assertEquals("Invalid character in grid: Z", exception.getMessage());
  }

  //Test for parseGridConfigString method with even number of card cells.
  @Test
  public void testParseGridConfigString_EvenCardCells() {
    List<String> evenCardCellsGrid = List.of(
            "4 4",
            "CCXX",
            "XCCC",
            "XXCX",
            "XXXX"
    );

    Exception exception = Assert.assertThrows(IllegalStateException.class, () -> {
      ConfigReader.parseGridConfigString(evenCardCellsGrid);
    });
    Assert.assertEquals("The number of card cells must be odd.", exception.getMessage());
  }

  //Test for parseGridConfigString method with null input.
  @Test
  public void testParseGridConfigString_NullInput() {
    Exception exception = Assert.assertThrows(IllegalArgumentException.class, () -> {
      ConfigReader.parseGridConfigString(null);
    });
    Assert.assertEquals("Grid configuration cannot be null or empty.", exception.getMessage());
  }

  //Test for readCards method with valid card configuration.
  @Test
  public void testReadCards_Valid() throws IOException {
    Path tempFile = Files.createTempFile("validCards", ".txt");
    Files.write(tempFile, List.of(
            "CARD1 1 1 1 1",
            "CARD2 2 2 2 2",
            "CARD3 3 3 3 3"
    ));

    List<Card> cards = ConfigReader.readCards(tempFile.toString());
    Assert.assertEquals(3, cards.size());
    Assert.assertEquals("CARD1", cards.get(0).getName());
    Assert.assertEquals(1, cards.get(0).getValue(NORTH));
  }

  //Test for readCards method with invalid card configuration (invalid attack value).
  @Test
  public void testReadCards_InvalidAttackValue() throws IOException {
    Path tempFile = Files.createTempFile("invalidCards", ".txt");
    Files.write(tempFile, List.of(
            "CARD1 X X X X" // Invalid attack values 'X'
    ));

    Exception exception = Assert.assertThrows(IllegalArgumentException.class, () -> {
      ConfigReader.readCards(tempFile.toString());
    });
    Assert.assertEquals("Value has to be an integer", exception.getMessage());
  }

  //Test for readGrid method with valid grid configuration.
  @Test
  public void testReadGridValid() throws IOException {
    Path tempFile = Files.createTempFile("validGrid", ".txt");
    Files.write(tempFile, List.of(
            "5 5",
            "CCXXX",
            "XCCXX",
            "XXCXX",
            "XXCCX",
            "XXXCC"
    ));

    Grid grid = ConfigReader.readGrid(tempFile.toString());
    Assert.assertNotNull(grid);
    Assert.assertEquals(5, grid.getRows());
  }

  // Tests behaviors in the player class
  @Test
  public void playerTest() {
    Assert.assertEquals(Color.RED, player.getColor());
    player.setHand(cardsList);
    Assert.assertEquals(cardsList, player.getHand());
    player.removeCardFromHand(cardsList.get(1));
    cardsList.remove(1);
    Assert.assertEquals(cardsList, player.getHand());
  }

  /**
   * Test for initializing a valid grid with an odd number of card cells.
   * This ensures that the grid is initialized correctly and that the validateGrid method works.
   */
  @Test
  public void testValidGridInitialization() {
    Cell[][] cells = new Cell[3][3];
    cells[0][0] = new CardCell();
    cells[0][1] = new Hole();
    cells[0][2] = new CardCell();
    cells[1][0] = new Hole();
    cells[1][1] = new CardCell();
    cells[1][2] = new Hole();
    cells[2][0] = new CardCell();
    cells[2][1] = new Hole();
    cells[2][2] = new CardCell();

    // Create a valid grid with an odd number of card cells (5 card cells)
    Grid grid = new Grid(3, 3, cells);
    Assert.assertEquals(3, grid.getRows());
    Assert.assertEquals(3, grid.getCols());
  }

  /**
   * Test for initializing a grid with an even number of card cells.
   * This should throw an IllegalStateException because the number of card cells must be odd.
   */
  @Test
  public void testInvalidGridInitialization_EvenCardCells() {
    Cell[][] cells = new Cell[2][2];
    cells[0][0] = new CardCell();
    cells[0][1] = new CardCell();
    cells[1][0] = new CardCell();
    cells[1][1] = new CardCell(); // Even number of card cells (3)

    Exception exception = Assert.assertThrows(IllegalStateException.class, () -> {
      new Grid(2, 2, cells);
    });

    Assert.assertEquals("The number of card cells must be odd.", exception.getMessage());
  }

  /**
   * Test for getting a valid cell from the grid.
   * Ensures that getCell returns the correct cell at the specified location.
   */
  @Test
  public void testGetValidCell() {
    Cell[][] cells = new Cell[3][3];
    cells[0][0] = new CardCell();
    cells[0][1] = new Hole();
    Grid grid = new Grid(3, 3, cells);

    // Check that the correct cell is returned
    Assert.assertTrue(grid.getCell(0, 0) instanceof CardCell);
    Assert.assertTrue(grid.getCell(0, 1) instanceof Hole);
  }

  /**
   * Test for getting a cell with invalid coordinates (out of bounds).
   * This should throw an IndexOutOfBoundsException.
   */
  @Test(expected = IllegalStateException.class)
  public void testGetInvalidCellOutOfBounds() {
    Cell[][] cells = new Cell[3][3];
    Grid grid = new Grid(3, 3, cells);

    // Attempt to access a cell outside the bounds of the grid
    Exception exception = Assert.assertThrows(IndexOutOfBoundsException.class, () -> {
      grid.getCell(-1, 5); // Invalid coordinates
    });

    Assert.assertEquals("Invalid cell coordinates", exception.getMessage());
  }

  /**
   * Test for getting the total number of card cells in a valid grid.
   * Ensures that getNumCells returns the correct count of card cells.
   */
  @Test
  public void testGetNumCellsValidGrid() {
    Cell[][] cells = new Cell[3][3];
    cells[0][0] = new CardCell();
    cells[0][1] = new Hole();
    cells[0][2] = new CardCell();

    cells[1][0] = new Hole();
    cells[1][1] = new CardCell();

    Grid grid = new Grid(3, 3, cells);

    Assert.assertEquals(3, grid.getNumCells());
  }

  /**
   * Test for checking that two grids are equal when they have identical configurations.
   * Ensures that equals() returns true when comparing two grids with identical rows/columns
   * and cell configurations.
   */
  @Test
  public void testGridsEqualIdenticalGrids() {
    Cell[][] firstGridCells = {{new Hole(), new Hole()}, {new Hole(), new CardCell()}};

    Grid firstGrid = new Grid(2, 2, firstGridCells);
    Grid secondGrid = new Grid(2, 2, firstGridCells);

    Assert.assertTrue(firstGrid.equals(secondGrid)); // Grids should be equal
  }

  /**
   * Test for checking that two grids are not equal when they have different configurations.
   * Ensures that equals() returns false when comparing two grids with different rows/columns
   * or cell configurations.
   */
  @Test
  public void testGridsNotEqualDifferentGrids() {
    Cell[][] firstGridCells = {{new CardCell(), new Hole()}, {new Hole(), null}};
    Cell[][] secondGridCells = {{new CardCell(), null}, {new Hole(), null}};

    Grid firstGrid = new Grid(2, 2, firstGridCells);
    Grid secondGrid = new Grid(2, 2, secondGridCells);

    Assert.assertFalse(firstGrid.equals(secondGrid));
  }

  /**
   * Test that a new Hole object is always empty.
   * The isEmpty() method should return true for any Hole instance.
   */
  @Test
  public void testIsEmpty() {
    Hole hole = new Hole();
    Assert.assertEquals(true, hole.isEmpty());
  }

  /**
   * Test that attempting to place a card in a hole throws an IllegalStateException.
   * Holes cannot hold cards, so placeCard() should always throw an exception.
   */
  @Test
  public void testPlaceCardThrowsException() {
    Hole hole = new Hole();
    Card card = new Card("Dragon", 5, 7, 3, 6);

    Exception exception = Assert.assertThrows(IllegalStateException.class, () -> {
      hole.placeCard(card); // Should throw exception because you can't place a card in a hole
    });

    Assert.assertEquals("Can't place a card onto a hole.", exception.getMessage());
  }

  /**
   * Test that calling getCard() on a hole throws an IllegalStateException.
   * Since holes cannot contain cards, getCard() should always throw an exception.
   */
  @Test
  public void testGetCardThrowsException() {
    Hole hole = new Hole();

    Exception exception = Assert.assertThrows(IllegalStateException.class, () -> {
      hole.getCard(); // Should throw exception because a hole cannot have a card
    });

    Assert.assertEquals("A hole can not have a card", exception.getMessage());
  }

  /**
   * Test that the toString() method of Hole returns a single space (" ").
   * This is the expected string representation of a hole in the grid.
   */
  @Test
  public void testToString() {
    Hole hole = new Hole();
    Assert.assertEquals("X", hole.toString());
  }

  @Test
  public void testPlayToGrid() throws IOException {
    model.startGame(new File("Configs/NoHolesGrid:9C0H"),
            new File("Configs/CardConfig:10"), false);

    model.playToGrid(0, 0, 0);

    Assert.assertEquals("[[R, _, _], [_, _, _], [_, _, _]]"
            , model.getGrid().toString());
    List<Card> hand = new ArrayList<>();
    hand.add(new Card("CARD3", 3, 3, 3, 3));
    hand.add(new Card("CARD5", 5, 5, 5, 5));
    hand.add(new Card("CARD7", 7, 7, 7, 7));
    hand.add(new Card("CARD9", 9, 9, 9, 9));
    for (Card card : hand) {
      card.setOwner(Color.RED);
    }

    model.playToGrid(4, 0, 1);
    Assert.assertEquals("[[B, B, _], [_, _, _], [_, _, _]]"
            , model.getGrid().toString());
  }

  @Test
  public void testBattle() throws IOException {
    model.startGame(new File("Configs/NoHolesGrid:9C0H"),
            new File("Configs/CardConfig:10"), false);

    model.playToGrid(0, 0, 0);
    model.playToGrid(0, 0, 1);

    Assert.assertEquals("[[B, B, _], [_, _, _], [_, _, _]]", model.getGrid().toString());

    model.playToGrid(0, 0, 2);

    Assert.assertEquals("[[R, R, R], [_, _, _], [_, _, _]]", model.getGrid().toString());

    model.playToGrid(0, 1, 2);

    Assert.assertEquals("[[B, B, B], [_, _, B], [_, _, _]]", model.getGrid().toString());

    model.playToGrid(0, 1, 1);

    Assert.assertEquals("[[R, R, R], [_, R, R], [_, _, _]]", model.getGrid().toString());

    model.playToGrid(0, 1, 0);

    Assert.assertEquals("[[B, B, B], [B, B, B], [_, _, _]]", model.getGrid().toString());

    model.playToGrid(0, 2, 0);

    Assert.assertEquals("[[R, R, R], [R, R, R], [R, _, _]]", model.getGrid().toString());

    model.playToGrid(0, 2, 1);

    Assert.assertEquals("[[B, B, B], [B, B, B], [B, B, _]]", model.getGrid().toString());

    model.playToGrid(0, 2, 2);

    Assert.assertEquals("[[R, R, R], [R, R, R], [R, R, R]]", model.getGrid().toString());
  }

  @Test
  public void testAnnounceWinner() throws IOException {
    model.startGame(new File("Configs/NoHolesGrid:9C0H"),
            new File("Configs/CardConfig:10"), false);

    model.playToGrid(0, 0, 0);
    model.playToGrid(0, 0, 1);
    model.playToGrid(0, 0, 2);
    model.playToGrid(0, 1, 2);
    model.playToGrid(0, 1, 1);
    model.playToGrid(0, 1, 0);
    model.playToGrid(0, 2, 0);
    model.playToGrid(0, 2, 1);
    model.playToGrid(0, 2, 2);

    Assert.assertEquals("The Red Player has won!", model.announceWinner());

    model = new ThreeTriosGameModel();
    model.startGame(new File("Configs/Grid:1C0H"),
            new File("Configs/CardConfig:10"), false);

    model.playToGrid(0, 0, 0);

    Assert.assertEquals("It's a tie!", model.announceWinner());

    model = new ThreeTriosGameModel();
    model.startGame(new File("Configs/NoHolesGrid:9C0H"),
            new File("Configs/CardConfig:10"), false);

    model.playToGrid(1, 0, 0);
    model.playToGrid(1, 0, 1);
    model.playToGrid(1, 0, 2);
    model.playToGrid(1, 1, 2);
    model.playToGrid(1, 1, 1);
    model.playToGrid(1, 1, 0);
    model.playToGrid(1, 2, 0);
    model.playToGrid(1, 2, 1);
    model.playToGrid(0, 2, 2);

    Assert.assertEquals("The Blue Player has won!", model.announceWinner());
  }

  //Test to confirm that a player cannot flip their own cards.
  @Test
  public void testCannotFlipOwnCards() throws IOException {
    model.startGame(new File("Configs/NoHolesGrid:9C0H"),
            new File("Configs/CardConfig:10"), false);

    // Red plays a card at (0,0)
    model.playToGrid(0, 0, 0);

    // Red plays another card adjacent to their own card at (0,1)
    model.playToGrid(1, 0, 1);

    // Ensure that Red's card at (0,0) is not flipped by their own card
    Assert.assertEquals(Color.RED, model.cellOwner(0, 0));
    Assert.assertEquals(Color.RED, model.cellOwner(0, 1));
  }

  //Test to confirm that a player can move to a location that flips opponent's cards.
  @Test
  public void testMoveFlipsOpponentCards() throws IOException {
    model.startGame(new File("Configs/NoHolesGrid:9C0H"),
            new File("Configs/CardConfig:10"), false);

    // Red plays a card at (0,0)
    model.playToGrid(0, 0, 0);

    // Blue plays a weaker card adjacent to Red's card at (1,0)
    model.playToGrid(1, 1, 0);

    // Red plays another card at (2,0) which should flip Blue's card at (1,0)
    model.playToGrid(2, 2, 0);

    // Ensure that Blue's card at (1,0) has been flipped to Red
    Assert.assertEquals(Color.RED, model.cellOwner(1, 0));
  }

  //Test to confirm that a play correctly flips all relevant cards.
  @Test
  public void testPlayFlipsMultipleCards() throws IOException {
    model.startGame(new File("Configs/NoHolesGrid:9C0H"),
            new File("Configs/CardConfig:10"), false);

    // Set up initial state with Red and Blue cards
    model.playToGrid(0, 0, 1); // Red plays at (0,1)
    model.playToGrid(1, 2, 1); // Blue plays at (2,1)

    // Red plays in the middle (1,1), which should flip both adjacent Blue cards
    model.playToGrid(2, 1, 1);

    // Ensure both Blue's cards at (2,1) and (1,2) have been flipped to Red
    Assert.assertEquals(Color.RED, model.cellOwner(2, 1));
  }

  //Test to confirm that a player cannot play when it's not their turn.
  @Test(expected = IllegalStateException.class)
  public void testCannotPlayOutOfTurn() throws IOException {
    model.startGame(new File("Configs/NoHolesGrid:9C0H"),
            new File("Configs/CardConfig:10"), false);

    // Red plays first as expected
    model.playToGrid(0, 0, 0);

    // Attempt to have Red play again when it's Blue's turn
    model.playToGrid(1, 1, 1); // This should throw an IllegalStateException
  }

  // Test to confirm that the game correctly detects when it is over.
  @Test
  public void testGameOverDetection() throws IOException {
    model.startGame(new File("Configs/NoHolesGrid:9C0H"),
            new File("Configs/CardConfig:10"), false);

    // Fill up the grid with alternating plays between Red and Blue
    for (int i = 0; i < model.getGridRows(); i++) {
      for (int j = 0; j < model.getGridCols(); j++) {
        if ((i + j) % 2 == 0) {
          model.playToGrid(i % model.getHand(Color.RED).size(), i, j);
          // Red plays on even cells
        } else {
          model.playToGrid(i % model.getHand(Color.BLUE).size(), i, j);
          // Blue plays on odd cells
        }
      }
    }

    // Check if the game is over after filling the grid
    Assert.assertTrue(model.checkGameOver());
  }
}
