package h01;

import fopbot.Direction;
import fopbot.Robot;
import fopbot.RobotFamily;
import h01.template.Contaminant;
import h01.template.TickBased;
import h01.template.Utils;
import org.tudalgo.algoutils.student.Student;

import java.util.ArrayList;

/**
 * A {@link Contaminant}-{@link Robot} that moves in a predefined way and contaminates the floor.
 */
public class Contaminant2 extends Robot implements Contaminant, TickBased {

    /**
     * Creates a new {@link Contaminant2}.
     *
     * @param x             the initial x coordinate of the robot
     * @param y             the initial y coordinate of the robot
     * @param direction     the initial direction of the robot
     * @param numberOfCoins the initial number of coins of the robot
     */
    public Contaminant2(final int x, final int y, final Direction direction, final int numberOfCoins) {
        super(x, y, direction, numberOfCoins, RobotFamily.SQUARE_AQUA);
    }

    @Override
    public int getUpdateDelay() {
        return 8;
    }

    @Override
    public void doMove() {
        if (!hasAnyCoins()) {
            turnOff();
        }
        // if the robot is off or has no coin, the method will end
        if (!isTurnedOff() && hasAnyCoins()) {
            // check if 2 coins at the field
            int coinsHere = Utils.getCoinAmount(getX(), getY());
            if (coinsHere < 2) {
                // put the coins
                int putCoinsCounter = Math.min(2 - coinsHere, getNumberOfCoins());
                for (int i = 0; i < putCoinsCounter; i++) {
                    putCoin();
                }
            }
            // record how many left turns are required in all possible directions
            ArrayList<Integer> possibleTurns = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                if (isFrontClear()) {
                    possibleTurns.add(i);
                }
                turnLeft();
            }
            /*
            turningIndex = -1, when no possible direction;
            when there are possible directions, filter in the order of 1,0,3,2
             */
            int turningIndex = -1;
            if (possibleTurns.contains(2)) {
                turningIndex = 2;
            }
            if (possibleTurns.contains(3)) {
                turningIndex = 3;
            }
            if (possibleTurns.contains(0)) {
                turningIndex = 0;
            }
            if (possibleTurns.contains(1)) {
                turningIndex = 1;
            }
            // turn to the desired direction
            if (turningIndex != -1) {
                for (int i = 0; i < turningIndex; i++) {
                    turnLeft();
                }
            }
            // check once again
            if(isFrontClear()){
                move();
            }
        }
    }
}
