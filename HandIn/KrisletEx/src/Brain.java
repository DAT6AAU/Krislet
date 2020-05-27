//
//	File:			Brain.java
//	Author:		Krzysztof Langner
//	Date:			1997/04/28
//
//    Modified by:	Paul Marlow

//    Modified by:      Edgar Acosta
//    Date:             March 4, 2008
//    Modified by:      Mikkel Kuntz & Jon theis Nilsson
//    Date:             2020-05-27

import java.awt.geom.Point2D;
import java.lang.Math;

import objects.ObjectInfo;

class Brain extends Thread {
    private Krislet krislet; // robot which is controlled by this brain
    private Memory memory; // place where all information is stored
    private static Movement movement;
    private char side;
    volatile private boolean timeOver;
    private String playMode; // todo change to enum?

    private int playerNumber;
    private Point2D.Double startingCoordinate;
    public double headAngle = 0;
    double[] currentPosition;
    public Point2D.Double lookingDirectionVector;

    String nextCommand; // todo change to enum?

    String currentAction;
    boolean isCurrentActionComplete;

    private PlayerPhysicalEstimator playerPhysicalEstimator = new PlayerPhysicalEstimator();    

    ObjectInfo ball;
    ObjectInfo goal_opponent;


    public Brain(Krislet krislet, char side, int number, String playMode, Point2D.Double startingCoordinate) {
        this.timeOver = false;
        this.krislet = krislet;
        memory = new Memory();
        movement = new Movement(this, playerPhysicalEstimator);

        this.side = side; // better naming or description
        this.playerNumber = number;
        this.playMode = playMode;

        this.startingCoordinate = startingCoordinate;

        movement = new Movement(this, playerPhysicalEstimator);

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
            // for testing
            if (playerNumber == 1){
                System.out.println(playMode);
            }

            // reset command to make sure not to resend last command even if action is complete.
            nextCommand = null;

            UpdatePosition();

            UpdateDirection();

            UpdateObjective();

            UpdateCurrentAction();

            //TurnActionIntoCommand() //incorporated in play_on for now

            //SendNextCommand()
            if (nextCommand != null) {
                krislet.send("(" + nextCommand + ")");
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
                playOn();
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
                //indirectFreeKick("l)");
                break;
            case "indirect_free_kick_r":
                //indirectFreeKick("r)");
                break;
            case "half_time":
                break;

            case "time_over":
                //timeOver();
                krislet.bye();
                break;
            default:
                //
                break;
        }
        // findObject(Object)
        // toTowards(Object
        // moveTowards(Object
        // moveBetween(object, object)
        // distanceTo(object)
		// skip()
    }

    private void playOn(){

        ball = memory.getBallInfo();

        // If you don't know where is ball then find it
        if (ball == null) {
            nextCommand = "turn " + 90;
            //findObject(ball);
            return;
        }

        // Move towards ball
        if (ball.m_distance > 1.5){
            if( ball.m_direction != 0 ){
                nextCommand = "turn " + ball.m_direction;
            }
            else
                nextCommand = "dash " + 10 * ball.m_distance;
        }
        else {
            //findGoal
            if (goal_opponent == null) {
                if (side == 'l') {
                    goal_opponent = memory.getGoalObj('r');
                } else {
                    goal_opponent = memory.getGoalObj('l');
                }
                nextCommand = "turn " + 90;
            }
            else {
                nextCommand = "kick " + 100 + " " + 45; //goal_opponent.m_direction;
            }
        }
    }

    private void setupFormation() {
        if (playMode.equals("before_kick_off") ){
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
    }*/

    private void UpdatePosition() {
        double[] result = playerPhysicalEstimator.getPlayerPosition(memory.getFlagInfoList());
        if (result != null)
            System.out.println(Math.floor(result[0]) + " " + Math.floor(result[1]));
        if (result != null){ //TODO should we use last known? Or acknowledge that we dont know?
            currentPosition = result;
        }
    }

    private void UpdateDirection(){
        if (currentPosition == null){
            return;
        }

        Point2D.Double result = playerPhysicalEstimator.getPlayerLookingDirection(memory.getFlagInfoList(), currentPosition);
        //System.out.println(result);
        if (result != null){ //TODO should we use last known? Or acknowledge that we dont know?
            lookingDirectionVector = result;
        }
        //System.out.println("Looking direction: " + Math.floor(lookingDirectionVector.x) + " " + Math.floor(lookingDirectionVector.y));
    }

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
