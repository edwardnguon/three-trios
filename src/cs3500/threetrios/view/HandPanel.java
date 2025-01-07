package cs3500.threetrios.view;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.CardinalDirection;

/**
 * Represents a panel displaying a player's hand of cards in the Three Trios game.
 * The panel renders each card as a rectangle, showing the card's attack values
 * (North, East, South, West) and highlights the selected card with a yellow border.
 * It allows for selecting and unselecting cards, and updates dynamically as cards are played.
 */
public class HandPanel extends JPanel {

  private final Color playerColor;
  private final List<Card> cards;
  private int selectedCardIndex = -1;  // Track which card is selected (-1 means no selection)

  /**
   * Constructs a HandPanel for a player in the Three Trios game.
   * Initializes the panel with the player's color and their list of cards.
   * The panel will display each card as a rectangle and allow for selecting/unselecting cards.
   *
   * @param playerColor The color representing the player (e.g., red or blue).
   * @param cards The list of cards in the player's hand to be displayed.
   */
  public HandPanel(Color playerColor, List<Card> cards) {
    this.playerColor = playerColor;
    this.cards = cards;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    // Calculate dynamic card height based on how many cards are left
    int cardHeight = getHeight() / Math.max(cards.size(), 1);  // Ensure no division by zero
    int cardWidth = getWidth();  // Full width of the panel

    g2d.setColor(playerColor);

    // Draw cards as rectangles without spacing between them
    for (int i = 0; i < cards.size(); i++) {
      Card card = cards.get(i);
      int x = 0;  // No padding on X-axis
      int y = i * cardHeight;  // No padding on Y-axis

      // Draw the card rectangle
      g2d.fillRect(x, y, cardWidth, cardHeight);

      // If this card is selected, draw a yellow border around it
      if (i == selectedCardIndex) {
        g2d.setColor(Color.YELLOW);  // Yellow border for selected card
        g2d.setStroke(new BasicStroke(3));  // Thicker stroke for selection
        g2d.drawRect(x, y, cardWidth, cardHeight);
      }

      g2d.setColor(Color.BLACK);  // Draw black border around each card
      g2d.setStroke(new BasicStroke(1));  // Reset stroke size
      g2d.drawRect(x, y, cardWidth, cardHeight);

      g2d.setColor(Color.WHITE);

      String north = String.valueOf(card.getValue(CardinalDirection.NORTH));
      String east = String.valueOf(card.getValue(CardinalDirection.EAST));
      String south = String.valueOf(card.getValue(CardinalDirection.SOUTH));
      String west = String.valueOf(card.getValue(CardinalDirection.WEST));

      g2d.drawString(north, x + cardWidth / 2 - 10, y + 20);
      g2d.drawString(east, x + cardWidth - 15, y + (cardHeight / 2));
      g2d.drawString(south, x + cardWidth / 2 - 10, y + cardHeight - 15);
      g2d.drawString(west, x + 5, y + (cardHeight / 2));

      g2d.setColor(playerColor);  // Reset color after drawing text
    }
  }

  @Override
  public Dimension getPreferredSize() {
    // Adjust width dynamically based on parent height
    return new Dimension(100, getParent().getHeight());
  }

  /**
   * Method to update the list of cards in this panel.
   * This method will be called after a card is played.
   */
  public void updateCards(List<Card> newCards) {
    this.cards.clear();
    this.cards.addAll(newCards);
    repaint();  // Repaint after updating cards
  }

  /**
   * Method to set the selected card index and repaint the panel.
   * If the same card is clicked again, it will be unselected.
   *
   * @param index The index of the selected/unselected card.
   */
  public void setSelectedCardIndex(int index) {
    if (this.selectedCardIndex == index) {
      // If the same card is clicked again, unselect it
      this.selectedCardIndex = -1;
    } else {
      // Otherwise select the new one
      this.selectedCardIndex = index;
    }
    repaint();  // Repaint after selecting/unselecting a new card
  }

  /**
   * Method to reset the selected card index and remove any highlights.
   */
  public void resetSelection() {
    this.selectedCardIndex = -1;  // Reset selection (no cards highlighted)
    repaint();  // Repaint to remove any highlights
  }
}