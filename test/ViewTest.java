import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Color;
import cs3500.threetrios.model.Grid;
import cs3500.threetrios.model.HumanPlayer;
import cs3500.threetrios.model.ThreeTriosGameModel;
import cs3500.threetrios.view.TextualView;

import static cs3500.threetrios.model.ConfigReader.readGrid;

/**
 * Test class for the game's view.
 */
public class ViewTest {
  public Grid grid;
  public HumanPlayer redPlayer;
  public HumanPlayer bluePlayer;
  public ThreeTriosGameModel model;
  public TextualView view;

  @Before
  public void setup() throws IOException {
    grid = readGrid("Configs/NoHolesGrid:9C0H");
    redPlayer = new HumanPlayer(Color.RED);
    bluePlayer = new HumanPlayer(Color.BLUE);
    Card card1 = new Card("CARD1", 1, 1, 1, 1);
    Card card2 = new Card("CARD2", 2, 2, 2, 2);
    Card card3 = new Card("CARD3", 3, 3, 3, 3);
    Card card4 = new Card("CARD4", 4, 4, 4, 4);
    Card card5 = new Card("CARD5", 5, 5, 5, 5);
    Card card6 = new Card("CARD6", 6, 6, 6, 6);
    Card card7 = new Card("CARD7", 7, 7, 7, 7);
    Card card8 = new Card("CARD8", 8, 8, 8, 8);
    Card card9 = new Card("CARD9", 9, 9, 9, 9);
    Card cardA = new Card("CARDA", 10, 10, 10, 10);
    redPlayer.setHand(new ArrayList<>(Arrays.asList(card1, card3, card5, card7, card9)));
    bluePlayer.setHand(new ArrayList<>(Arrays.asList(card2, card4, card6, card8, cardA)));
    model = new ThreeTriosGameModel();
    File gridConfig = new File("Configs/NoHolesGrid:9C0H");
    File cardConfig = new File("Configs/CardConfig:10");
    model.startGame(gridConfig, cardConfig, false);
    view = new TextualView(model);
  }

  @Test
  public void testView() {
    Assert.assertEquals(
            "Player: RED\n" +
                    "_ _ _ \n" +
                    "_ _ _ \n" +
                    "_ _ _ \n" +
                    "Hand:\n" +
                    "CARD1 1 1 1 1\n" +
                    "CARD3 3 3 3 3\n" +
                    "CARD5 5 5 5 5\n" +
                    "CARD7 7 7 7 7\n" +
                    "CARD9 9 9 9 9\n"
            , view.toString());
    model.playToGrid(0, 0, 0);
    Assert.assertEquals(
            "Player: BLUE\n" +
                    "R _ _ \n" +
                    "_ _ _ \n" +
                    "_ _ _ \n" +
                    "Hand:\n" +
                    "CARD2 2 2 2 2\n" +
                    "CARD4 4 4 4 4\n" +
                    "CARD6 6 6 6 6\n" +
                    "CARD8 8 8 8 8\n" +
                    "CARDA 10 10 10 10\n"
            , view.toString());
  }
}


