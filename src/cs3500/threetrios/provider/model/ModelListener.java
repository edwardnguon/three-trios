package cs3500.threetrios.provider.model;

/**
 * An interface for any class that listens and reacts to model events.
 */
public interface ModelListener {

  /**
   * Triggers whenever the player turn changes.
   * Functionality depends on implementation.
   */
  void playerChanged();

  /**
   * Triggers when the game ends.
   * Functionality depends on implementation.
   */
  void gameOver();



}