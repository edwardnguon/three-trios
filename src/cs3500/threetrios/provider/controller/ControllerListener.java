package cs3500.threetrios.provider.controller;

/**
 * An interface for any object that listens for controller updates.
 */
public interface ControllerListener {

  /**
   * Triggers when the current player changes.
   */
  public void playerChanged();

  /**
   * Triggers when a move made by the player is invalid.
   *
   * @param errorMsg the error message from the model for the move
   */
  public void invalidMove(String errorMsg);

}
