package cs3500.threetrios.adapter;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.model.CardinalDirection;
import cs3500.threetrios.provider.model.Card;
import cs3500.threetrios.provider.model.Color;
import cs3500.threetrios.provider.model.Direction;
import cs3500.threetrios.provider.model.Powers;

/**
 * Class to adapt our card class to the client's.
 */
public class CardAdapter implements Card {

  private cs3500.threetrios.model.Card card;

  public CardAdapter(cs3500.threetrios.model.Card card) {
    this.card = card;
  }

  @Override
  public Powers getAttackPower(Direction direction) {
    CardinalDirection cardinalDirection = DirectionAdapter.toOurDirection(direction);
    int cardValue = card.getValue(cardinalDirection);
    return PowerAdapter.intToPowers(cardValue);
  }

  @Override
  public String getName() {
    return card.getName();
  }

  @Override
  public Color getOwner() {
    return ColorAdapter.toClientColor(card.getOwner());
  }

  @Override
  public void setOwner(Color color) {
    card.setOwner(ColorAdapter.toOurColor(color));
  }

  @Override
  public Card getCopy() {
    return this;
  }

  /**
   * Method to convert an entire hand(list of cards) from our type to
   * the client's type.
   * @param hand list of cards of our type
   * @return list of cards in the client's type
   */
  public static List<Card> convertHand(List<cs3500.threetrios.model.Card> hand) {
    List<Card> cards = new ArrayList<>();
    for (cs3500.threetrios.model.Card card : hand) {
      cards.add(new CardAdapter(card));
    }
    return cards;
  }

  /**
   * Converts a card of the client's type to a card of our type.
   * @param card existing card of client type
   * @return new card of our type
   */
  public static cs3500.threetrios.model.Card convertCard(Card card) {
    return new cs3500.threetrios.model.Card(card.getName(),
            PowerAdapter.powersToInt(card.getAttackPower(Direction.NORTH)),
            PowerAdapter.powersToInt(card.getAttackPower(Direction.SOUTH)),
            PowerAdapter.powersToInt(card.getAttackPower(Direction.EAST)),
            PowerAdapter.powersToInt(card.getAttackPower(Direction.WEST)));
  }
}
