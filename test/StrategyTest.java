import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import cs3500.threetrios.model.ThreeTriosGameModel;
import cs3500.threetrios.strategy.CornerStrategy;
import cs3500.threetrios.strategy.FlipMaxStrategy;
import cs3500.threetrios.strategy.Move;
import cs3500.threetrios.strategy.Strategy;

/**
 * Test class for the strategy interface and its implemented classes.
 */
public class StrategyTest {
  ThreeTriosGameModel model;
  Strategy strategy;

  @Before
  public void setup() throws IOException {
    model = new ThreeTriosGameModel();
    model.startGame(new File("Configs/NoHolesGrid:9C0H"),
            new File("Configs/CardConfig:10"), false);
  }

  @Test
  public void flipMaxStrategy() {
    strategy = new FlipMaxStrategy(model);
    Move foundMove = strategy.findMove();

    Assert.assertEquals(new Move(0, 0, 0), foundMove);

    model.playToGrid(0, 0, 0);
    strategy = new FlipMaxStrategy(model);
    Move foundMove2 = strategy.findMove();
    Assert.assertEquals(new Move(0, 0, 1), foundMove2);
  }

  @Test
  public void cornerStrategy() {
    strategy = new CornerStrategy(model);
    Move foundMove = strategy.findMove();

    Assert.assertEquals(new Move(4, 0, 0), foundMove);
  }
}
