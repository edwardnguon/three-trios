package cs3500.threetrios.model;

import java.util.Arrays;
import java.util.Objects;

/**
 * Represents the game grid in the Three Trios game.
 * The grid contains a rectangular arrangement of cells, which can either be card cells or holes.
 * The number of card cells must be odd to ensure balanced gameplay.
 * **Coordinate System**:
 * - The origin (0, 0) is located at the top-left corner of the grid.
 * - The rows increase downward on the vertical axis, and the columns increase to the right on the
 *   horizontal axis.
 * - The grid uses a 2D array (`Cell[][]`) to store its data. The first index represents the row,
 *   and the second index represents the column.
 * **Hex-Coordinates Mapping**:
 * - Although this grid uses a 2D array for storage, it supports hexagonal-like gameplay.
 *   Each cell is mapped to Cartesian coordinates (row, column).
 * - For example, `cells[0][0]` refers to the top-left corner of the grid, and
 *   `cells[rows-1][cols-1]` refers to the bottom-right corner.
 */
public class Grid {
  private final Cell[][] cells;
  private final int rows;
  private final int cols;

  /**
   * Initializes the grid from a configuration file.
   * Ensures that the grid is valid by checking that it contains an odd number of card cells.
   *
   * @param rows       the number of rows in the grid
   * @param cols       the number of columns in the grid
   * @param gridConfig a 2D array of Cell objects representing the grid configuration
   * @throws IllegalStateException if there is an even number of card cells
   */
  public Grid(int rows, int cols, Cell[][] gridConfig) {
    this.rows = rows;
    this.cols = cols;
    cells = new Cell[rows][cols];
    initializeGrid(gridConfig);
    validateGrid();
  }

  /**
   * Initializes a grid given instructions in the form of a 2D array of Cell objects.
   *
   * @param cells a 2D array of Cell objects representing the grid configuration
   * @throws IllegalArgumentException if the provided cell configuration is null
   */
  private void initializeGrid(Cell[][] cells) {
    if (cells == null) {
      throw new IllegalArgumentException("Cells cannot be null");
    }
    for (int row = 0; row < rows; row++) {
      for (int column = 0; column < cols; column++) {
        this.cells[row][column] = cells[row][column];
      }
    }
  }

  /**
   * Validates the grid to ensure it meets class invariants.
   * Specifically, it checks that there is an odd number of card cells and that all cell types
   * are valid.
   *
   * @throws IllegalStateException if there is an even number of card cells or if any invalid cell
   *                               type is detected
   */
  private void validateGrid() {
    if (getNumCells() % 2 == 0) {
      throw new IllegalStateException("The number of card cells must be odd.");
    }
  }

  /**
   * Gets the number of rows in the grid.
   *
   * @return the number of rows in the grid
   */
  public int getRows() {
    return rows;
  }

  /**
   * Gets the number of columns in the grid.
   *
   * @return the number of columns in the grid
   */
  public int getCols() {
    return cols;
  }

  /**
   * Gets the cell at the specified row and column.
   *
   * @param row the row index (must be within bounds)
   * @param col the column index (must be within bounds)
   * @return the cell at the specified location
   * @throws IndexOutOfBoundsException if the specified coordinates are out of bounds
   */
  public Cell getCell(int row, int col) {
    if (row >= 0 && row < rows && col >= 0 && col < cols) {
      return cells[row][col];
    }
    throw new IllegalArgumentException("Invalid cell coordinates");
  }

  /**
   * Checks if the cell at the specified row and column is a hole.
   * A hole is a type of cell where no card can be placed.
   *
   * @param row The row index of the cell to check.
   * @param col The column index of the cell to check.
   * @return true if the cell is a hole, false otherwise.
   * @throws IndexOutOfBoundsException if the specified coordinates are out of bounds.
   */
  public boolean isHole(int row, int col) {
    if (row >= 0 && row < rows && col >= 0 && col < cols) {
      return cells[row][col] instanceof Hole;
    }
    throw new IndexOutOfBoundsException("Invalid cell coordinates");
  }

  /**
   * Gets the total number of card cells in this grid.
   *
   * @return the total number of card cells in this grid
   */
  public int getNumCells() {
    int count = 0;
    for (int row = 0; row < rows; row++) {
      for (int column = 0; column < cols; column++) {
        if (cells[row][column] instanceof CardCell) {
          count++;
        }
      }
    }
    return count;
  }

  /**
   * Makes a copy of the grid.
   *
   * @return a copy of the grid
   */
  public Grid copy() {
    Cell[][] cellsCopy = new Cell[rows][cols];
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        cellsCopy[row][col] = cells[row][col].copy();
      }
    }
    return new Grid(rows, cols, cellsCopy);
  }

  /**
   * Searches the grid for a cell containing a given card.
   *
   * @param card card to search for
   * @return cell where the card is found
   */
  public Cell searchCell(Card card) {
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        Cell cell = cells[row][col];
        if (cell instanceof CardCell) {
          if (!cell.isEmpty()) {
            if (card == cell.getCard()) {
              return cell;
            }
          }
        }
      }
    }
    return null;
  }

  /**
   * Provides a string representation of this grid for testing purposes.
   *
   * @return a string representation of this grid's cells
   */
  @Override
  public String toString() {
    return Arrays.deepToString(cells);
  }

  /**
   * Checks whether this Grid is equal to another object.
   *
   * @param obj another object to compare to
   * @return true if this Grid is equal to obj, false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Grid other = (Grid) obj;
    return Arrays.deepEquals(cells, other.cells) && rows == other.rows && cols == other.cols;
  }

  /**
   * Generates a hash code for this Grid based on its fields.
   *
   * @return a hash code for this Grid
   */
  @Override
  public int hashCode() {
    return Objects.hash(cells, rows, cols);
  }
}