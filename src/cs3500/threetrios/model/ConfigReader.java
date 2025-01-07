package cs3500.threetrios.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static cs3500.threetrios.model.CardinalDirection.EAST;
import static cs3500.threetrios.model.CardinalDirection.NORTH;
import static cs3500.threetrios.model.CardinalDirection.SOUTH;
import static cs3500.threetrios.model.CardinalDirection.WEST;

/**
 * Provides methods to convert configurations of cards and grids to string formats.
 */
public class ConfigReader {

  /**
   * Converts a list of Card objects into a string format.
   * Each card is represented as "CARD_NAME NORTH SOUTH EAST WEST".
   *
   * @param cards the list of Card objects
   * @return a string representation of the card configurations
   */
  public static String cardConfigToString(List<Card> cards) {
    if (cards == null || cards.isEmpty()) {
      return "";  // Return an empty string if the input is null or empty
    }

    StringBuilder builder = new StringBuilder();
    for (Card card : cards) {
      builder.append(card.getName()).append(" ")
              .append(card.getValue(CardinalDirection.NORTH)).append(" ")
              .append(card.getValue(CardinalDirection.SOUTH)).append(" ")
              .append(card.getValue(CardinalDirection.EAST)).append(" ")
              .append(card.getValue(CardinalDirection.WEST)).append("\n");
    }
    return builder.toString();
  }

  /**
   * Converts a 2D array of Cell objects into a string format.
   * The grid is represented with rows, columns, and cell types ('X' for holes, 'C' for card cells).
   *
   * @param cells the 2D array of Cell objects
   * @return a string representation of the grid configuration
   */
  public static String gridConfigToString(Cell[][] cells) {
    if (cells == null || cells.length == 0 || cells[0].length == 0) {
      return "";  // Return an empty string if the input is null or empty
    }

    int rows = cells.length;
    int cols = cells[0].length;

    StringBuilder builder = new StringBuilder();
    builder.append(rows).append(" ").append(cols).append("\n");

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (cells[i][j] instanceof Hole) {
          builder.append('X');
        } else if (cells[i][j] instanceof CardCell) {
          builder.append('C');
        }
      }
      builder.append("\n");
    }
    return builder.toString();
  }

  /**
   * Parses a list of strings representing the grid configuration into a 2D array of Cells.
   *
   * @param lines the lines from the grid configuration file
   * @return a 2D array of Cells representing the grid
   */
  public static Cell[][] parseGridConfig(List<String> lines) {
    String[] dimensions = lines.get(0).split(" ");
    int rows = Integer.parseInt(dimensions[0]);
    int cols = Integer.parseInt(dimensions[1]);

    Cell[][] cells = new Cell[rows][cols];
    for (int row = 0; row < rows; row++) {
      String line = lines.get(row + 1); // Skip the first line with dimensions
      for (int col = 0; col < cols; col++) {
        cells[row][col] = (line.charAt(col) == 'X') ? new Hole() : new CardCell();
      }
    }
    return cells;
  }

  /**
   * Parses a list of strings representing the card configuration into a list of Card objects.
   *
   * @param lines the lines from the card configuration file
   * @return a list of Card objects
   */
  public static List<Card> parseCardConfig(List<String> lines) {
    List<Card> cards = new ArrayList<>();
    for (String line : lines) {
      String[] parts = line.split(" ");
      if (parts.length == 5) {
        String name = parts[0];
        int north = parseAttackValue(parts[1]);
        int south = parseAttackValue(parts[2]);
        int east = parseAttackValue(parts[3]);
        int west = parseAttackValue(parts[4]);

        cards.add(new Card(name, north, south, east, west));
      }
    }
    return cards;
  }

  /**
   * Parses an attack value from a string.
   *
   * @param value the string representation of the attack value
   * @return the integer attack value
   */
  private static int parseAttackValue(String value) {
    if (isAcceptableValue(value)) {
      if (value.equals("A")) {
        return 10; // 'A' represents 10
      } else {
        return Integer.parseInt(value);
      }
    } else {
      throw new IllegalArgumentException("Value has to be an integer");
    }
  }

  /**
   * Checks if the given string value is an acceptable value: a number between 1 and 9,
   * or "A" (which represents 10).
   *
   * @param value the string to check
   * @return true if the value is a number between 1 and 9 or "A", false otherwise
   */
  public static boolean isAcceptableValue(String value) {
    if (value.equals("A")) {
      return true; // "A" is acceptable, representing 10
    }

    try {
      int number = Integer.parseInt(value);
      return number >= 1 && number <= 9;
    } catch (NumberFormatException e) {
      // If parsing fails, the value is not a valid integer
      return false;
    }
  }

  /**
   * Reads and returns a list of Card objects based on file configuration.
   *
   * @param filePath the path to the card configuration file
   * @return a list of Card objects
   * @throws IOException if an I/O error occurs
   */
  public static List<Card> readCards(String filePath) throws IOException {
    List<Card> cards = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(" ");
        if (parts.length == 5) {
          String name = parts[0];
          int north = parseAttackValue(parts[1]);
          int south = parseAttackValue(parts[2]);
          int east = parseAttackValue(parts[3]);
          int west = parseAttackValue(parts[4]);

          cards.add(new Card(name, north, south, east, west));
        }
      }
    }

    return cards;
  }

  /**
   * Reads and returns a Grid object based on file configuration.
   *
   * @param filePath the path to the grid configuration file
   * @return a Grid object
   * @throws IOException if an I/O error occurs
   */
  public static Grid readGrid(String filePath) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String[] dimensions = reader.readLine().split(" ");
      int rows = Integer.parseInt(dimensions[0]);
      int cols = Integer.parseInt(dimensions[1]);

      List<String> gridConfig = new ArrayList<>();
      for (int i = 0; i < rows; i++) {
        String line = reader.readLine();
        if (line != null && line.length() == cols) {
          gridConfig.add(line);
        }
      }

      return new Grid(rows, cols, parseGridConfig(Files.readAllLines(Path.of(filePath))));
    }
  }

  /**
   * Converts a list of Card objects into a string format.
   * Each card is represented as "CARD_NAME NORTH SOUTH EAST WEST".
   *
   * @param cards the list of Card objects
   * @return a string representation of the card configurations
   */
  public static String cardToStringConfig(List<Card> cards) {
    if (cards == null || cards.isEmpty()) {
      return "";  // Return an empty string if the input is null or empty
    }

    StringBuilder builder = new StringBuilder();
    for (Card card : cards) {
      builder.append(card.getName()).append(" ")
              .append(card.getValue(NORTH)).append(" ")
              .append(card.getValue(SOUTH)).append(" ")
              .append(card.getValue(EAST)).append(" ")
              .append(card.getValue(WEST)).append("\n");
    }
    return builder.toString();
  }

  /**
   * Converts a 2D array of Cell objects into a string format.
   * The grid is represented with rows, columns, and cell types ('X' for holes, 'C' for card cells).
   *
   * @param cells the 2D array of Cell objects
   * @return a string representation of the grid configuration
   */
  public static String gridToStringConfig(Cell[][] cells) {
    if (cells == null || cells.length == 0 || cells[0].length == 0) {
      return "";  // Return an empty string if the input is null or empty
    }

    int rows = cells.length;
    int cols = cells[0].length;

    StringBuilder builder = new StringBuilder();
    builder.append(rows).append(" ").append(cols).append("\n");

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (cells[i][j] instanceof Hole) {
          builder.append('X');
        } else if (cells[i][j] instanceof CardCell) {
          builder.append('C');
        }
      }
      builder.append("\n");
    }
    return builder.toString();
  }

  /**
   * Parses a list of strings representing the grid configuration into a 2D array of Cells.
   *
   * @param lines the lines from the grid configuration file
   * @return a 2D array of Cells representing the grid
   * @throws IllegalArgumentException if grid dimensions are invalid or invalid characters
   */
  public static Cell[][] parseGridConfigString(List<String> lines) {
    if (lines == null || lines.isEmpty()) {
      throw new IllegalArgumentException("Grid configuration cannot be null or empty.");
    }

    String[] dimensions = lines.get(0).split(" ");
    int rows = Integer.parseInt(dimensions[0]);
    int cols = Integer.parseInt(dimensions[1]);

    if (rows <= 0 || cols <= 0) {
      throw new IllegalArgumentException("Grid dimensions must be positive integers.");
    }

    Cell[][] cells = new Cell[rows][cols];
    int cardCellCount = 0;

    for (int row = 0; row < rows; row++) {
      String line = lines.get(row + 1); // Skip the first line with dimensions
      if (line.length() != cols) {
        throw new IllegalArgumentException("Row length does not match column count.");
      }

      for (int col = 0; col < cols; col++) {
        char cellChar = line.charAt(col);
        if (cellChar == 'X') {
          cells[row][col] = new Hole();
        } else if (cellChar == 'C') {
          cells[row][col] = new CardCell();
          cardCellCount++;
        } else {
          throw new IllegalArgumentException("Invalid character in grid: " + cellChar);
        }
      }
    }

    // Ensure there is an odd number of card cells
    if (cardCellCount % 2 == 0) {
      throw new IllegalStateException("The number of card cells must be odd.");
    }

    return cells;
  }
}