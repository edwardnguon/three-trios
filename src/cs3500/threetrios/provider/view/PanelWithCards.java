package cs3500.threetrios.provider.view;

import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import cs3500.threetrios.provider.model.Card;
import cs3500.threetrios.provider.model.Direction;


/**
 * An abstract class for any j panel that will display trios cards.
 * Contains shared functionality from trios panels.
 */
public abstract class PanelWithCards extends JPanel {

  protected List<ViewListener> listeners = new ArrayList<>();

  protected void addInteractionListener(ViewListener listener) {
    listeners.add(listener);
  }

  /**
   * Renders card numbers in the appropriate position.
   * @param g2d the graphics object used to write the numbers
   * @param card the card that is being written to
   * @param cardWidth the width of the card in the panel
   * @param cardHeight the height of the card in the panel
   * @param y the minimum y location of the card
   * @param x the minimum x location of the card
   */
  protected void printCardNumbers(Graphics2D g2d, Card card,
                                  int cardWidth, int cardHeight, int y, int x) {
    int fontSize = Math.min(cardWidth, cardHeight) / 5;
    g2d.setFont(new Font("Arial", Font.BOLD, fontSize));
    g2d.setColor(java.awt.Color.BLACK);

    String east = card.getAttackPower(Direction.EAST).getvalueString();
    String south = card.getAttackPower(Direction.SOUTH).getvalueString();
    String west = card.getAttackPower(Direction.WEST).getvalueString();
    String north = card.getAttackPower(Direction.NORTH).getvalueString();

    int northX = x + (cardWidth - g2d.getFontMetrics().stringWidth(north)) / 2;
    g2d.drawString(north, northX, y + fontSize);

    int southX = x + (cardWidth - g2d.getFontMetrics().stringWidth(south)) / 2;
    g2d.drawString(south, southX, y + cardHeight - (fontSize / 3));

    int westY = y + (cardHeight / 2) + (fontSize / 3);
    g2d.drawString(west, x  + fontSize / 3, westY);

    int eastY = y + (cardHeight / 2) + (fontSize / 3);
    g2d.drawString(east, x + cardWidth - g2d.getFontMetrics().stringWidth(east)
        - (fontSize / 3), eastY);
  }
}
