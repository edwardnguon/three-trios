package cs3500.threetrios.view;

/**
 * Interface to listen for clicks.
 */
public interface Features {

  void onCardClick(int cardIndex, String player);

  // New method for playing a card to the grid
  void playCardToGrid(int cardIndex, int row, int col);

  void turnSwitched();

  void gameOver();
}