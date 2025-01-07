package cs3500.threetrios.provider.model;

/**
 * The rule logic for when cards battle.
 */
public interface BattleRule {

  /**
   * Executes the battle between two cards and returns result.
   *
   * @return the result of the battle as a BattleResult (WIN, LOSS, or TIE)
   */
  public BattleResult doBattle();

}
