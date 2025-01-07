package cs3500.threetrios.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.ReadonlyThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosGameModel;

/**
 * Class that puts together the grid and hand panels for the final GUI.
 * This class is responsible for rendering the game state, handling user interactions,
 * and updating visual components such as the grid and player hands.
 */
public class ThreeTriosGameView extends JFrame implements ThreeTriosView {

  private final ReadonlyThreeTriosModel model;
  private final HandPanel leftPanel;
  private final HandPanel rightPanel;
  private final JPanel centerPanel;

  // Track selected card and player
  private int selectedCardIndex = -1;  // -1 means no card selected
  private String selectedPlayer = null;  // Track which player selected a card

  /**
   * Constructor for initializing GUI components.
   *
   * @param model The read-only model of Three Trios game providing
   *              game state displayed in this view.
   */
  public ThreeTriosGameView(ReadonlyThreeTriosModel model) {
    this.model = model;

    setTitle("Three Trios Game");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout(0, 0));

    leftPanel = new HandPanel(ColorConverter.convertToAwtColor(cs3500.threetrios.model.Color.RED),
            model.getHand(cs3500.threetrios.model.Color.RED));
    rightPanel = new HandPanel(ColorConverter.convertToAwtColor(cs3500.threetrios.model.Color.BLUE),
            model.getHand(cs3500.threetrios.model.Color.BLUE));
    centerPanel = new GridPanel(model);

    add(leftPanel, BorderLayout.WEST);
    add(centerPanel, BorderLayout.CENTER);
    add(rightPanel, BorderLayout.EAST);

    pack();
    setVisible(true);
  }

  /**
   * Default constructor for the view for adapter purposes.
   */
  public ThreeTriosGameView() {
    model = new ThreeTriosGameModel();
    leftPanel = new HandPanel(java.awt.Color.RED, new ArrayList<>());
    rightPanel = new HandPanel(Color.BLUE, new ArrayList<>());
    centerPanel = new GridPanel(model);
  }

  @Override
  public void render() {
    repaint();
  }

  /**
   * Adds listeners to handle user interactions (card selection and playing cards).
   *
   * @param listener The controller that implements {@link Features}, which handles player actions.
   */
  @Override
  public void addClickListener(Features listener) {

    // Handle clicks on grid cells
    centerPanel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        int row = e.getY() / (centerPanel.getHeight() / model.getGridRows());
        int col = e.getX() / (centerPanel.getWidth() / model.getGridCols());

        if (selectedCardIndex != -1 && selectedPlayer != null) {
          if (model.isEmpty(row, col) && !model.isHole(row, col)) {
            listener.playCardToGrid(selectedCardIndex, row, col);
            resetSelection();
            render();
            updateHandPanels(model.getHand(cs3500.threetrios.model.Color.RED),
                    model.getHand(cs3500.threetrios.model.Color.BLUE));
          } else {
            System.out.println("Cannot play here! Cell is either occupied or a hole.");
          }
        } else {
          System.out.println("No card selected!");
        }
      }
    });

    // Handle clicks on Player Red's hand (left panel)
    leftPanel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        int cardIndex = e.getY() / (leftPanel.getHeight()
                / model.getHand(cs3500.threetrios.model.Color.RED).size());

        if (model.currentPlayer().getColor() == cs3500.threetrios.model.Color.RED) {
          leftPanel.setSelectedCardIndex(cardIndex);
          listener.onCardClick(cardIndex, "Red");
          rightPanel.resetSelection();
          selectedPlayer = "Red";
          selectedCardIndex = cardIndex;
        } else {
          System.out.println("It's not Red's turn!");
          showMessage("It's not Red's turn!");
        }
      }
    });

    // Handle clicks on Player Blue's hand (right panel)
    rightPanel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        int cardIndex = e.getY() / (rightPanel.getHeight()
                / model.getHand(cs3500.threetrios.model.Color.BLUE).size());

        if (model.currentPlayer().getColor() == cs3500.threetrios.model.Color.BLUE) {
          rightPanel.setSelectedCardIndex(cardIndex);
          listener.onCardClick(cardIndex, "Blue");
          leftPanel.resetSelection();
          selectedPlayer = "Blue";
          selectedCardIndex = cardIndex;
        } else {
          System.out.println("It's not Blue's turn!");
          showMessage("It's not Blue's turn!");
        }
      }
    });
  }

  /**
   * Updates both players' hands after playing a card.
   * This method updates the cards displayed in each player's hand panel.
   *
   * @param redHand The updated list of cards in Red player's hand.
   * @param blueHand The updated list of cards in Blue player's hand.
   */
  public void updateHandPanels(List<Card> redHand, List<Card> blueHand) {
    leftPanel.updateCards(redHand);
    rightPanel.updateCards(blueHand);
  }

  /**
   * Resets selection after playing or unselecting cards.
   * This method resets the selection state for both players' hand panels.
   */
  private void resetSelection() {
    leftPanel.resetSelection();
    rightPanel.resetSelection();
    selectedPlayer = null;
    selectedCardIndex = -1;
  }

  /**
   * To display game messages based on listeners.
   * @param message message to be displayed to player.
   */
  public void showMessage(String message) {
    SwingUtilities.invokeLater(() -> {
      JOptionPane optionPane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
      JDialog dialog = optionPane.createDialog(this, "Message");
      dialog.setModal(false); // Non-blocking
      dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
      dialog.setVisible(true);
    });
  }

  /**
   * To display error messages on GUI to indicate game errors.
   * @param message error message to be displayed to player.
   */
  public void showErrorMessage(String message) {
    JOptionPane.showMessageDialog(this,
            message, "Error", JOptionPane.ERROR_MESSAGE);
  }
}