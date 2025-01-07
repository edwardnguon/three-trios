package cs3500.threetrios.provider.view;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import cs3500.threetrios.provider.model.Card;
import cs3500.threetrios.provider.model.Cell;
import cs3500.threetrios.provider.model.Position;
import cs3500.threetrios.provider.model.ReadOnlyTriosModel;

/**
 * The portion of the ThreeTrios GUI that represents the game board.
 * Composes a read only trios model and controller.
 */
public class BoardPanel extends PanelWithCards {
  private final ReadOnlyTriosModel triosModel;

  protected Position selectedPosition;

  /**
   * Creates a board panel given a read only three trios model (for game state).
   * @param model the read only model used to check state.
   */
  public BoardPanel(ReadOnlyTriosModel model) {
    this.triosModel = model;
    setPreferredSize(new Dimension(400, 400));


    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        int cellWidth = getWidth() / triosModel.getBoardWidth();
        int cellHeight = getHeight() / triosModel.getBoardHeight();
        int col = e.getX() / cellWidth;
        int row = e.getY() / cellHeight;
        System.out.println("clicked cell (" + col + ", " + row + ")");
        Position position = new Position(col, row);

        if (selectedPosition != null && selectedPosition.equals(position)) {
          selectedPosition = null;
        } else {
          selectedPosition = position;
        }


        listeners.forEach((x) -> x.onCellSelected(selectedPosition));

        repaint();
      }
    });
  }



  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    int cellWidth = getWidth() / triosModel.getBoardWidth();
    int cellHeight = getHeight() / triosModel.getBoardHeight();
    Graphics2D g2d = (Graphics2D) g.create();
    for (int row = 0; row < triosModel.getBoardHeight(); row++) {
      for (int col = 0; col < triosModel.getBoardWidth(); col++) {
        Position position = new Position(col, row);
        Cell cell = triosModel.getCellAt(row, col);

        int x = col * cellWidth;
        int y = row * cellHeight;

        if (cell.hasCard()) {
          Card card = cell.getCard();
          g2d.setColor(card.getOwner() == cs3500.threetrios.provider.model.Color.RED ?
              new java.awt.Color(255, 171, 173) :
              new java.awt.Color(72, 172, 255));
          g2d.fillRect(x, y, cellWidth, cellHeight);

          printCardNumbers(g2d, card, cellWidth, cellHeight, y,x);
        } else {
          Color cellColor;
          if (cell.isBlocked()) {
            cellColor = new java.awt.Color(212, 200, 0);
          } else {
            cellColor = new java.awt.Color(192, 192, 192);
          }
          g.setColor(cellColor);

          g.fillRect(x, y, cellWidth, cellHeight);
          g.setColor(Color.BLACK);
        }
        g.drawRect(x, y, cellWidth, cellHeight);

        if (selectedPosition != null && selectedPosition.equals(position)) {
          g.setColor(new Color(0, 255, 0, 100));
          g.fillRect(x, y, cellWidth, cellHeight);
        }
      }
    }
  }
}
