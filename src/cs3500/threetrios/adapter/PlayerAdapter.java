package cs3500.threetrios.adapter;

import cs3500.threetrios.provider.model.Color;
import cs3500.threetrios.provider.player.Player;
import cs3500.threetrios.provider.player.PlayerListener;

/**
 * Adapter class that implements the client's Player type so that we can
 * use our implementation for the client's type.
 */
public class PlayerAdapter implements Player {

  private cs3500.threetrios.model.Player player;

  public PlayerAdapter(cs3500.threetrios.model.Player player) {
    this.player = player;
  }

  @Override
  public void yourTurn() {
    // Functionality handled in the controller
  }

  @Override
  public void addPlayerListener(PlayerListener listener) {
    // Functionality handled in the controller
  }

  @Override
  public Color getColor() {
    return ColorAdapter.toClientColor(player.getColor());
  }
}
