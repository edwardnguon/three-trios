package cs3500.threetrios.view;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Graphics;

import javax.swing.JPanel;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.CardinalDirection;
import cs3500.threetrios.model.ReadonlyThreeTriosModel;

/**
 * Represents the grid panel for the Three Trios game.
 * This panel is responsible for rendering the game grid, including empty cells, holes, and cards.
 * Cards are displayed with their respective colors (based on ownership)
 * and attack values (North, East, South, West).
 * The grid dynamically adjusts its size based on the panel's dimensions.
 */
public class GridPanel extends JPanel {

  private final ReadonlyThreeTriosModel model;

  /**
   * Constructs a GridPanel to display the game grid.
   * Initializes the panel with a read-only model of the game state. The model provides information
   * about the grid cells, including whether they are empty, contain holes, or have cards. The panel
   * will render each cell accordingly.
   *
   * @param model The read-only model of the Three Trios game.
   *              This model provides the state of the grid that will be displayed in this panel.
   */
  public GridPanel(ReadonlyThreeTriosModel model) {
    this.model = model;
    // Remove setPreferredSize with getParent() as it causes NullPointerException
    // The layout manager will handle resizing
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    int rows = model.getGridRows();
    int cols = model.getGridCols();

    // Calculate dynamic cell width and height based on panel size
    int cellWidth = getWidth() / cols;
    int cellHeight = getHeight() / rows;

    // Loop through grid cells and draw them based on their type (empty or hole or card)
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        if (model.isHole(row, col)) {
          g2d.setColor(Color.GRAY); // Holes are grey
          // Draw the cell as a rectangle with dynamic sizing
          g2d.fillRect(col * cellWidth, row * cellHeight, cellWidth, cellHeight);
        } else if (model.isEmpty(row, col)) {
          g2d.setColor(Color.YELLOW); // Empty cells are yellow
          // Draw the cell as a rectangle with dynamic sizing
          g2d.fillRect(col * cellWidth, row * cellHeight, cellWidth, cellHeight);
        } else {
          Card card = model.cellContents(row, col);
          if (model.cellOwner(row, col) == cs3500.threetrios.model.Color.RED) {
            g2d.setColor(Color.RED); // Red player's card in this cell
          } else if (model.cellOwner(row, col) == cs3500.threetrios.model.Color.BLUE) {
            g2d.setColor(Color.BLUE); // Blue player's card in this cell

          }
          // Draw the cell as a rectangle with dynamic sizing
          g2d.fillRect(col * cellWidth, row * cellHeight, cellWidth, cellHeight);


          g2d.setColor(Color.WHITE);  // Set text color to white for visibility

          String north = String.valueOf(card.getValue(CardinalDirection.NORTH));
          String east = String.valueOf(card.getValue(CardinalDirection.EAST));
          String south = String.valueOf(card.getValue(CardinalDirection.SOUTH));
          String west = String.valueOf(card.getValue(CardinalDirection.WEST));

          // Draw text for North, East, South, West directions inside the rectangle
          g2d.drawString(north, col * cellWidth + (cellWidth / 2) - 10,
                  row * cellHeight + 15);  // North value
          g2d.drawString(east, col * cellWidth + cellWidth - 15,
                  row * cellHeight + (cellHeight / 2));   // East value
          g2d.drawString(south, col * cellWidth + (cellWidth / 2) - 10,
                  row * cellHeight + cellHeight - 5);  // South value
          g2d.drawString(west, col * cellWidth + 5,
                  row * cellHeight + (cellHeight / 2));


        }
        g2d.setColor(Color.BLACK); // Draw border around each cell
        g2d.drawRect(col * cellWidth, row * cellHeight, cellWidth, cellHeight);

      }
    }
  }

  @Override
  public Dimension getPreferredSize() {
    // Return a sensible default size without relying on getParent()
    return new Dimension(500, 500);  // You can adjust this as needed
  }
}