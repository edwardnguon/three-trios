package cs3500.threetrios.view;

import java.io.IOException;

import cs3500.threetrios.model.ReadonlyThreeTriosModel;

/**
 * Provides a textual rendering of the game state.
 */
public class TextualView {
  private Appendable out;
  private final ReadonlyThreeTriosModel model;

  public TextualView(ReadonlyThreeTriosModel model) {
    this.model = model;
    this.out = System.out;
  }

  /**
   * Constructor for the view of the game based on a given grid and current player.
   *
   * @param appendable appendable
   * @param model      model for the game
   */
  public TextualView(ReadonlyThreeTriosModel model, Appendable appendable) {
    if (appendable == null || model == null) {
      throw new IllegalArgumentException("Arguments can not be null");
    }

    this.out = appendable;
    this.model = model;
  }

  /**
   * Textual representation of the grid and respective player's hand.
   *
   * @return string representation of the view.
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();

    // Display current player's turn
    builder.append("Player: ").append(model.currentPlayer().getColor().toString()).append("\n");

    // Render the grid
    for (int row = 0; row < model.getGridRows(); row++) {
      for (int col = 0; col < model.getGridCols(); col++) {
        builder.append(model.getGrid().getCell(row, col)).append(" ");
      }
      builder.append("\n");
    }

    // Display player's hand
    builder.append("Hand:");
    model.currentPlayer().getHand().forEach(card ->
            builder.append("\n").append(card)
    );

    return builder + "\n";
  }

  /**
   * Appends toString value to appendable.
   *
   * @throws IOException if appendable is null
   */
  public void render() throws IOException {
    if (out == null) {
      throw new IllegalArgumentException("Appendable is null");
    }
    out.append(this.toString());
  }
}