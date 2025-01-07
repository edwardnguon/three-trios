package cs3500.threetrios.provider.controller;

import cs3500.threetrios.provider.model.Position;

/**
 * Controls game flow and player interactions in the Three Trios game.
 * Each player (human or machine) has their own controller instance that
 * manages their interactions with the game.
 */
public interface TriosController  {
  /**
   * Called when a card is selected by a player.
   *
   * @param cardIndex the index of the selected card in hand
   */
  public void onCardSelected(int cardIndex);

  /**
   * Called when a cell on the board is selected.
   *
   * @param position the position of the selected cell
   * @throws IllegalArgumentException if position is null
   */
  public void onCellSelected(Position position);
}
