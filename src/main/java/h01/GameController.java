package h01;

import fopbot.Robot;
import fopbot.World;
import h01.template.GameControllerBase;
import h01.template.Utils;
import org.tudalgo.algoutils.student.Student;

/**
 * A {@link GameController} controls the game loop and the {@link Robot}s and checks the win condition.
 */
public class GameController extends GameControllerBase {

    /**
     * Creates a new {@link GameControllerBase}.
     */
    public GameController() {
        setup();
    }

    @Override
    public void checkWinCondition() {
        // cleaner wins if both contaminants turned off or at least 200 coins in the dump zone
        boolean isCleanerWin = (getContaminant1().isTurnedOff() && getContaminant2().isTurnedOff())
            || (Utils.getCoinAmount(0, World.getHeight() - 1) >= 200);
        // calculate the amount of total fields and fields with coins
        int totalFieldAmount = World.getHeight() * World.getWidth();
        int coinsFieldAmount = 0;
        for (int i = 0; i < World.getWidth(); i++) {
            for (int j = 0; j < World.getHeight(); j++) {
                if (Utils.getCoinAmount(i, j) > 0) {
                    coinsFieldAmount++;
                }
            }
        }
        // winning condition for contaminants
        boolean isContaminantsWin = 2 * coinsFieldAmount >= totalFieldAmount;
        if (isCleanerWin || isContaminantsWin) {
            cleaningRobot.turnOff();
            contaminant1.turnOff();
            contaminant2.turnOff();
            // announce the winner
            if (isCleanerWin) {
                System.out.println("CLeaning robot won!");
            } else {
                System.out.println("Contaminants won!");
            }
            stopGame();
        }
    }
}
