package cs3500.threetrios.provider.model;

/**
 * Represents the possible powers for one of the cards directions.
 */
public enum Powers {
  ONE("1"),
  TWO("2"),
  THREE("3"),
  FOUR("4"),
  FIVE("5"),
  SIX("6"),
  SEVEN("7"),
  EIGHT("8"),
  NINE("9"),
  TEN("A");

  private final String value;

  /**
   * Constructs a power object that represents the strength of a specific direction of a card.
   *
   * @param value the string representation for the value of a card
   */
  Powers(String value) {
    this.value = value;
  }

  /**
   * Returns the Powers enum constant for the given integer value.
   *
   * @param value the integer value to look up
   * @return the corresponding Powers enum constant
   * @throws IllegalArgumentException if not value is found
   */
  public static Powers fromValue(String value) {
    for (Powers power : Powers.values()) {
      if (power.value.equals(value)) {
        return power;
      }
    }
    throw new IllegalArgumentException("No valid attack value found for " + value);
  }

  /**
   * Returns the string representation of the attack power.
   *
   * @return the value of the attack power as a string
   */
  public int getValueInt() {
    if (value.equals("A")) {
      return 10;
    } else {
      return Integer.parseInt(value);
    }
  }

  /**
   * Returns the value of the power as a string.
   *
   * @return the value of the power as a String
   */
  public String getvalueString() {
    return value;
  }


}


