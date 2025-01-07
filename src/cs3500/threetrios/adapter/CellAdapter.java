package cs3500.threetrios.adapter;

import cs3500.threetrios.model.Hole;
import cs3500.threetrios.provider.model.Card;
import cs3500.threetrios.provider.model.Cell;

/**
 * Class to adapt our cell class to our client's.
 */
public class CellAdapter implements Cell {
  private cs3500.threetrios.model.Cell cell;

  public CellAdapter(cs3500.threetrios.model.Cell cell) {
    this.cell = cell;
  }

  @Override
  public Boolean isEmpty() {
    return cell.isEmpty();
  }

  @Override
  public Boolean isBlocked() {
    return cell instanceof Hole;
  }

  @Override
  public void placeCard(Card card) {
    cell.placeCard(CardAdapter.convertCard(card));
  }

  @Override
  public Card getCard() {
    return new CardAdapter(cell.getCard());
  }

  @Override
  public Boolean hasCard() {
    return !cell.isEmpty();
  }

  @Override
  public void block() {
    cell = new Hole();
  }
}
