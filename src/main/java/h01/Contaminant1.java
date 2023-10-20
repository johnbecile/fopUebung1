package h01;

import fopbot.Direction;
import fopbot.Robot;
import fopbot.RobotFamily;
import h01.template.Contaminant;
import h01.template.GameConstants;
import h01.template.TickBased;
import h01.template.Utils;
import org.tudalgo.algoutils.student.Student;

import java.util.ArrayList;

/**
 * A {@link Contaminant}-{@link Robot} that moves randomly and contaminates the floor.
 */
public class Contaminant1 extends Robot implements Contaminant, TickBased {

    /**
     * Creates a new {@link Contaminant1}.
     *
     * @param x             the initial x coordinate of the robot
     * @param y             the initial y coordinate of the robot
     * @param direction     the initial direction of the robot
     * @param numberOfCoins the initial number of coins of the robot
     */
    public Contaminant1(final int x, final int y, final Direction direction, final int numberOfCoins) {
        super(x, y, direction, numberOfCoins, RobotFamily.SQUARE_ORANGE);
    }

    @Override
    public int getUpdateDelay() {
        return 10;
    }

    @Override
    public void doMove() {
        if (!hasAnyCoins()) {
            turnOff();
        }
        // if the robot is off or has no coin, the method will end
        if (!isTurnedOff() && hasAnyCoins()) {
            // random number of coins to be put
            int putCoinsCounter = Utils.getRandomInteger(GameConstants.CONTAMINANT_ONE_MIN_PUT_COINS, GameConstants.CONTAMINANT_ONE_MAX_PUT_COINS);
            // no more than 20 coins at a single field
            putCoinsCounter = Math.min(putCoinsCounter, 20 - Utils.getCoinAmount(getX(), getY()));
            // if the robot has fewer coins than it should place, it puts down all the coins it has.
            putCoinsCounter = Math.min(putCoinsCounter, getNumberOfCoins());
            for (int i = 0; i < putCoinsCounter; i++) {
                putCoin();
            }
            // record the possible moving direction
            ArrayList<Integer> possibleTurns = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                if (isFrontClear()) {
                    possibleTurns.add(i);
                }
                turnLeft();
            }
            /*
            turningIndex = -1, when no possible direction;
            when there are possible directions, randomly choose one from them
             */
            int turningIndex = switch (possibleTurns.size()) {
                case 0 -> -1;
                default -> Utils.getRandomInteger(0, possibleTurns.size() - 1);
            };
            // if there is a possible direction, turn and move
            if (turningIndex != -1) {
                for (int i = 0; i < possibleTurns.get(turningIndex); i++) {
                    turnLeft();
                }
                // check once again
                if (isFrontClear()) {
                    move();
                }
            }
        }
    }
}
