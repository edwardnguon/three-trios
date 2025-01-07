package cs3500.threetrios.adapter;

import cs3500.threetrios.provider.view.ThreeTriosGUIView;
import cs3500.threetrios.view.ThreeTriosGameView;

/**
 * Adapts the client's view type to our view's type.
 */
public class ViewAdapter extends ThreeTriosGameView {
  private ThreeTriosGUIView clientView;

  /**
   * Constructor for the view adapter.
   * @param clientView client's view type
   */
  public ViewAdapter(ThreeTriosGUIView clientView) {
    super();
    this.clientView = clientView;
  }


  @Override
  public void render() {
    clientView.playerChanged();
  }

}
