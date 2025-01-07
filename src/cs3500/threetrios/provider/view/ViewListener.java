package cs3500.threetrios.provider.view;

import cs3500.threetrios.provider.model.Position;

/**
 * Represents a listener that reacts to gui view interaction.
 * Receives messages when cards or cells are selected.
 */
public interface ViewListener {

  /**
   * Runs when a user clicks a card in the view.
   * Action depends on implementation.
   *
   * @param cardIndex the index of the selected card
   */
  public void onCardSelected(int cardIndex);


  public void onCellSelected(Position position);



}
