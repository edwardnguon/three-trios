package cs3500.threetrios.provider.view;

import cs3500.threetrios.provider.model.Card;
import cs3500.threetrios.provider.model.Color;
import cs3500.threetrios.provider.model.ReadOnlyTriosModel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;


/**
 * The portion of the gui that displays each player's cards.
 */
public class HandPanel extends PanelWithCards {
  private final Color playerColor;
  protected int selectedCardIndex = -1;
  private ReadOnlyTriosModel model;

  /**
   * Creates a hand panel given a read only trios model and player color.
   * @param model the read only model used to extract game state
   * @param playerColor the color for the player whose hand is displayed
   */
  public HandPanel(ReadOnlyTriosModel model, Color playerColor) {
    this.model = model;
    this.playerColor = playerColor;

    // Initially set preferred size
    updatePreferredSize();

    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        List<Card> hand = model.getPlayerHand(playerColor);
        if (hand.isEmpty()) {
          return;
        }

        int cardHeight = getHeight() / hand.size();
        int index = e.getY() / cardHeight;
        if (index >= hand.size()) {
          index = hand.size() - 1;
        }

        if (model.getCurrentPlayer() == playerColor) {
          if (selectedCardIndex == index) {
            selectedCardIndex = -1;
          } else {
            selectedCardIndex = index;
          }
          listeners.forEach((x) -> x.onCardSelected(selectedCardIndex));
          revalidate();
          repaint();
        }
      }
    });
  }

  private void updatePreferredSize() {
    List<Card> hand = model.getPlayerHand(playerColor);
    int handSize = hand.size();
    int preferredHeight = handSize * 75;
    setPreferredSize(new Dimension(100, preferredHeight));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    List<Card> hand = model.getPlayerHand(playerColor);

    updatePreferredSize();

    Graphics2D g2d = (Graphics2D) g.create();

    int handSize = hand.size();
    if (handSize == 0) {
      g2d.dispose();
      return;
    }

    int cardWidth = getWidth();
    int cardHeight = getHeight() / handSize;

    for (int i = 0; i < handSize; i++) {
      int y = i * cardHeight;
      Card card = hand.get(i);

      g2d.setColor(playerColor == Color.RED ? new java.awt.Color(255, 171, 173) :
          new java.awt.Color(72, 172, 255));
      g2d.fillRect(0, y, cardWidth, cardHeight);

      g2d.setColor(java.awt.Color.BLACK);
      g2d.drawRect(0, y, cardWidth, cardHeight);

      printCardNumbers(g2d, card, cardWidth, cardHeight, y, 0);

      if (i == selectedCardIndex) {
        g2d.setColor(new java.awt.Color(255, 255, 0, 100));
        g2d.fillRect(0, y, cardWidth, cardHeight);
      }
    }
    g2d.dispose();
  }
}

