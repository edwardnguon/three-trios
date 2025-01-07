package cs3500.threetrios.view;

/**
 * Interface for the ThreeTriosGameView class to represent gui behavior.
 */
public interface ThreeTriosView {
  void render(); // to render the game

  void addClickListener(Features listener);
}