//
//	File:			Brain.java
//	Author:		Krzysztof Langner
//	Date:			1997/04/28
//
//    Modified by:	Paul Marlow

//    Modified by:      Edgar Acosta
//    Date:             March 4, 2008

import objects.ObjectInfo;

import java.awt.geom.Point2D;
import java.lang.Math;
import java.util.regex.*;

class Brain extends Thread {
    private Krislet krislet; // robot which is controlled by this brain
    private Memory memory; // place where all information is stored
    private char side;
    volatile private boolean timeOver; // todo kan slettes??
    private String playMode;

    private int playerNumber;
    private Point2D.Double startingCoordinate;
    public double headAngle = 0;
    double[] currentPosition;

    ObjectInfo ball; //todo can maybe be deleted
    ObjectInfo goal_opponent; //todo can maybe be deleted

    String nextCommand; // todo maybe an enum?

    String currentAction;
    boolean isCurrentActionComplete;


    private MathTest mathTest = new MathTest();


    public Brain(Krislet krislet, char side, int number, String playMode, Point2D.Double startingCoordinate) {
        this.timeOver = false;
        this.krislet = krislet;
        memory = new Memory();

        this.side = side; // better naming or description
        this.playerNumber = number;
        this.playMode = playMode;

        this.startingCoordinate = startingCoordinate;

        start();
    }
    /* Possible values for playMode.
                 "before_kick_off", "kick_off_l", "kick_off_r", "play_on",
                "kick_in_l", "kick_in_r", "corner_kick_l", "corner_kick_r"
                "goal_kick_l", "goal_kick_r", "foul_charge_l"
                "foul_charge_r", "back_pass_l", "back_pass_r"
                "indirect_free_kick_l", "indirect_free_kick_r", "half_time"
                "time_over"
     */

    public void run() {
        while (true){
            // TODO for Testing. Be kind, Delete.
            System.out.println(playMode);

            // reset command to make sure not to resend last command even if action is complete.
            nextCommand = null;

            //UpdatePosition();
            currentPosition = mathTest.getPlayerPosition(memory.getFlagInfoList());
            
            //UpdateDirection();

            UpdateObjective();

            UpdateCurrentAction();

            switch (playMode){
                case "before_kick_off":
                    setupFormation();
                    //beforeKickOff();
                    break;
                case "kick_off_l":
                    //kickOff_l("l")
                    break;
                case "kick_off_r":
                    //kickOff("r");
                    break;
                case "play_on":
                    //playOn();
                    break;
                case "kick_in_l":
                    //kickIn("l");
                    break;
                case "kick_in_r":
                    //kickIn("r");
                    break;
                case "corner_kick_l":
                    //cornerKick("l");
                    break;
                case "corner_kick_r":
                    //cornerKick("r");
                    break;
                case "goal_kick_l":
                    //goalKick("l");
                    break;
                case "goal_kick_r":
                    //goalKick("r");
                    break;
                case "foul_charge_l":
                    //foulCharge("l");
                    break;
                case "foul_charge_r":
                    //foulcharge("r")
                    break;
                case "back_pass_l":
                    //backPass("l");
                    break;
                case "back_pass_r":
                    //backPass("l");
                    break;
                case "indirect_free_kick_l":
                    //indirecFreeKick("l)");
                    break;
                case "indirect_free_kick_r":
                    //indirecFreeKick("r)");
                    break;
                case "half_time":
                    break;

                case "time_over":
                    //timeOver();
                    krislet.bye();
                    break;
                default:
                    // todo something?
                    break;
            }

            if (nextCommand != null) {
                krislet.send("(" + nextCommand + ")");
                //do the thing
            }

            // sleep one step to ensure that we will not send
            // two commands in one cycle.
            waitForNextCycle();
        }
    }

    private void UpdateObjective() {
        // offense
        // defense
        // can be extended later
    }

    private void UpdateCurrentAction() {
        // findObject(Object)
        // toTowards(Object
        // moveTowards(Object
        // moveBetween(object, object)
		// skip()
    }

    //private void selectCommandForNextCycle() {
    //}

    private void setupFormation() {
        if (playMode == "before_kick_off"){
            krislet.send("(move " + startingCoordinate.getX() + " " + startingCoordinate.getY() + ")");
        }
    }

    private void waitForNextCycle() {
        try {
            Thread.sleep(2 * Memory.SIMULATOR_STEP);
        } catch (Exception e) {

        }
    }

    /*
        private void findObject(ObjectInfo obj) {
        krislet.turn(40);
        memory.waitForNewInfo(); // todo maybe remove. should only be called in memory
    }

    private void boerneFodbold(){
        ball = memory.getBallInfo(); // måske rykkes ud som en fast variabel
        if (ball == null) {
            // If you don't know where is ball then find it
            findObject(ball);
        } else if (ball.m_distance > 1.0) {
            // If ball is too far then turn to ball or
            // if we have correct direction then go to ball
            if (ball.m_direction != 0) {
                movement.turnTowards(ball);
            } else {
                nextCommand = "dash " + 10 * ball.m_distance;
                //krislet.dash(10 * ball.m_distance);
            }
        } else {
            // We know where the ball is and we can kick it
            // so look for goal
            if (side == 'l') {
                goal_opponent = memory.getGoalObj('r');
            } else {
                goal_opponent = memory.getGoalObj('l');
            }

            if (goal_opponent == null) {
                findObject(goal_opponent); // giver ingen mening med null
            } else {
                nextCommand = "kick 100 " + goal_opponent.m_direction;
                //krislet.kick(100, goal_opponent.m_direction);
            }
        }
    }
    */


    public void printCurrentPosition(){
        if (currentPosition != null){
            System.out.println(currentPosition[0] + " " + currentPosition[1]);
        } else {
            System.out.println("Player pos not found!");
        }
    }

    // This function sends see information
    public void see(VisualInfo info) {
        memory.store(info);
    }

    // This function receives hear information from player
    public void hear(int time, int direction, String message) {
    }

    // This function receives hear information from referee
    public void hear(int time, String message) {
        playMode = message;

        /*
        if (message.compareTo("time_over") == 0) {
            timeOver = true;
        }*/
    }
}
