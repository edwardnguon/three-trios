package cs3500.threetrios.provider.player;

import cs3500.threetrios.provider.strategy.Move;


/**
 * An interface for any object that reacts to player moves.
 */
public interface PlayerListener {

  /**
   * Triggers after a player makes a move.
   * Should run any logic that is meant to run after a player move.
   * @param move the player's move
   */
  public void onPlayerMove(Move move);

}
