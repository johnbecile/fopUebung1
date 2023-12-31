package h01;

import fopbot.Direction;
import fopbot.Robot;
import h01.template.Cleaner;
import h01.template.GameConstants;
import h01.template.TickBased;
import h01.template.Utils;
import org.tudalgo.algoutils.student.Student;

/**
 * A robot that can clean the floor.
 */
public class CleaningRobot extends Robot implements Cleaner, TickBased {

    /**
     * Creates a new {@link CleaningRobot}.
     *
     * @param x             the initial x coordinate of the robot
     * @param y             the initial y coordinate of the robot
     * @param direction     the initial direction of the robot
     * @param numberOfCoins the initial number of coins of the robot
     */
    public CleaningRobot(final int x, final int y, final Direction direction, final int numberOfCoins) {
        super(x, y, direction, numberOfCoins);
    }

    @Override
    public void handleKeyInput(final int direction, final boolean shouldPutCoins, final boolean shouldPickCoins) {
        // put a coin if the robot can
        if (shouldPutCoins && hasAnyCoins()) {
            putCoin();
        }
        // pick a coin if the robot can
        if (shouldPickCoins && isOnACoin() && getNumberOfCoins() < GameConstants.CLEANER_CAPACITY) {
            pickCoin();
        }
        // if direction is not [0,3], Cleaner will not move
        if (direction >= 0 && direction <= 3) {
            int nowDirection = switch (getDirection()) {
                case UP -> 0;
                case RIGHT -> 1;
                case DOWN -> 2;
                // case LEFT:
                default -> 3;
            };
            // Perform a modulo operation to find out how many left turns are needed.
            int turnLeftTime = Math.floorMod(nowDirection - direction, 4);
            for (int i = 0; i < turnLeftTime; i++) {
                turnLeft();
            }
            // move if the front is clear
            if (isFrontClear()) {
                move();
            }
        }
    }
}
