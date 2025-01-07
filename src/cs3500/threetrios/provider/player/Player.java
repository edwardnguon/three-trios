package cs3500.threetrios.provider.player;


import cs3500.threetrios.provider.model.Color;

/**
 * An interface for computer or human three trios players.
 */
public interface Player {

  /**
   * Notifies the player that it is their turn.
   */
  public void yourTurn();

  /**
   * Adds a listener that will be notified when this player makes a move.
   *
   * @param listener the listener to be notified when moves are made
   * @throws IllegalArgumentException if the listener is null
   */
  public void addPlayerListener(PlayerListener listener);

  /**
   * Returns the color of the player.
   *
   * @return the color of the player (RED or BLUE)
   */
  public Color getColor();

}

