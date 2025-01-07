package cs3500.threetrios.provider.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import cs3500.threetrios.adapter.ControllerAdapter;
import cs3500.threetrios.adapter.ModelAdapter;
import cs3500.threetrios.adapter.PlayerAdapter;
import cs3500.threetrios.adapter.ViewAdapter;
import cs3500.threetrios.model.MachinePlayer;
import cs3500.threetrios.provider.controller.ControllerListener;
import cs3500.threetrios.provider.controller.TriosController;
import cs3500.threetrios.provider.model.Color;
import cs3500.threetrios.provider.model.ModelListener;
import cs3500.threetrios.provider.model.ReadOnlyTriosModel;
import cs3500.threetrios.provider.player.Player;
import cs3500.threetrios.strategy.CornerStrategy;
import cs3500.threetrios.strategy.FlipMaxStrategy;


/**
 * An implementation of the three trios gui view.
 * Uses 2 hand panels and a board panel, with hands sitting on sides.
 */
public class ThreeTriosGUIViewImpl extends JFrame implements
    ThreeTriosGUIView, ControllerListener {
  private final ReadOnlyTriosModel model;

  private final BoardPanel boardPanel;
  private final HandPanel redHandPanel;
  private final HandPanel blueHandPanel;
  private boolean endGameDisplayed;


  /**
   * Creates a GUI view given a read-only model to assess game state.
   * @param model the read-only model
   */
  public ThreeTriosGUIViewImpl(ReadOnlyTriosModel model) {
    super("Current turn: " + model.getCurrentPlayer());

    this.model = model;
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setPreferredSize(new Dimension(600, 600));
    setLocationRelativeTo(null);


    boardPanel = new BoardPanel(model);
    redHandPanel = new HandPanel(model, Color.RED);
    blueHandPanel = new HandPanel(model, Color.BLUE);
    endGameDisplayed = false;


    layoutComponents();
  }

  @Override
  public void display() {
    pack();
    setVisible(true);
  }

  /**
   * Adds a listener for the view.
   * @param listener listener for the view
   * @param color color for the view
   */
  public void addInteractionListener(ViewListener listener, Color color) {
    if (color == Color.RED) {
      redHandPanel.addInteractionListener(listener);
    } else {
      blueHandPanel.addInteractionListener(listener);
    }
    boardPanel.addInteractionListener(listener);
  }

  private void layoutComponents() {
    getContentPane().removeAll();
    setLayout(new BorderLayout());

    add(boardPanel, BorderLayout.CENTER);
    add(redHandPanel, BorderLayout.WEST);
    add(blueHandPanel, BorderLayout.EAST);
  }


  protected void refresh() {
    if (!isVisible()) {
      throw new IllegalStateException("View is not currently displayed");
    }
    boardPanel.repaint();
    redHandPanel.revalidate();
    redHandPanel.repaint();
    blueHandPanel.revalidate();
    blueHandPanel.repaint();
    if (model.isGameOver()) {
      if (!endGameDisplayed) {
        Color winner = model.getWinner();
        String message;
        if (winner == null) {
          message = "Game Over: It's a tie!";

        } else {
          message = "Game Over: Winner is " + winner + " with "
              + model.getScore(winner) + " points";
        }
        this.setTitle(message);
        SwingUtilities.invokeLater(() -> {
          JOptionPane.showMessageDialog(this, message,
              "Game Over", JOptionPane.INFORMATION_MESSAGE);
        });
        endGameDisplayed = true;

      } else {
        this.setTitle("Current turn: " + model.getCurrentPlayer());
      }
    }
  }

  @Override
  public void playerChanged() {
    redHandPanel.selectedCardIndex = -1;
    blueHandPanel.selectedCardIndex = -1;
    boardPanel.selectedPosition = null;
    refresh();
  }

  @Override
  public void invalidMove(String errMsg) {
    SwingUtilities.invokeLater(() -> {
      JOptionPane.showMessageDialog(this,
          errMsg, "Failed to Make Move",
          JOptionPane.INFORMATION_MESSAGE);
    });
    playerChanged();
  }


  /**
   * A main method to run the game with players or humans.
   * See README.md for more usage details.
   *
   * @param args the command line arguments. Write "human" for human and name of strategy for robot.
   * @throws IllegalArgumentException if args are poorly formatted or invalid
   */
  public static void main(String[] args) {
    ModelAdapter model = new ModelAdapter();

    if (args.length > 1) {
      if (args[1].equalsIgnoreCase("flipmaxstrategy")) {
        model.setPlayer(cs3500.threetrios.model.Color.BLUE,
                new MachinePlayer(cs3500.threetrios.model.Color.BLUE, new FlipMaxStrategy(model)));
      } else if (args[1].equalsIgnoreCase("cornerstrategy")) {
        model.setPlayer(cs3500.threetrios.model.Color.BLUE,
                new MachinePlayer(cs3500.threetrios.model.Color.BLUE, new CornerStrategy(model)));
      }
    }

    model.startGame(false, "Configs/CardConfig:10",
        "Configs/NoHolesGrid:9C0H");
    ThreeTriosGUIView redView = new ThreeTriosGUIViewImpl(model);
    ThreeTriosGUIView blueView = new ThreeTriosGUIViewImpl(model);
    Player red = new PlayerAdapter(model.currentPlayer());
    Player blue = new PlayerAdapter(model.otherPlayer());
    TriosController redController = new ControllerAdapter(model,
            (PlayerAdapter) red, new ViewAdapter(redView));
    TriosController blueController = new ControllerAdapter(model,
            (PlayerAdapter) blue, new ViewAdapter(blueView));
    redView.display();
    blueView.display();
    model.registerListener((ModelListener) redController);
    model.registerListener((ModelListener) blueController);
    blueView.addInteractionListener((ViewListener) blueController,Color.BLUE);
    redView.addInteractionListener((ViewListener) redController,Color.RED);
  }
}

