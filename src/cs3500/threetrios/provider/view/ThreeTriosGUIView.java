package cs3500.threetrios.provider.view;

import cs3500.threetrios.provider.controller.ControllerListener;
import cs3500.threetrios.provider.model.Color;

/**
 * A GUI view for the three trios game that can be displayed and refreshed.
 */
public interface ThreeTriosGUIView extends ControllerListener {

  /**
   * Displays the current state of the view.
   *
   * @throws IllegalStateException if game has not been started.
   */
  void display();

  /**
   * Refreshes the view, updating it to the current state of the model.
   * @throws IllegalStateException if view has not yet been displayed.
   */
  void playerChanged();


  public void addInteractionListener(ViewListener listener, Color color);


}
