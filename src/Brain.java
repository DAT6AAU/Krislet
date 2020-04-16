//
//	File:			Brain.java
//	Author:		Krzysztof Langner
//	Date:			1997/04/28
//
//    Modified by:	Paul Marlow

//    Modified by:      Edgar Acosta
//    Date:             March 4, 2008

import java.lang.Math;
import java.util.regex.*;

class Brain extends Thread {
    private Krislet krislet; // robot which is controlled by this brain
    private Memory memory; // place where all information is stored
    private char side;
    volatile private boolean timeOver;
    private String playMode;

    //---------------------------------------------------------------------------
    // - stores connection to krislet
    // - starts thread for this object
    public Brain(Krislet krislet, String team, char side, int number, String playMode) {
        this.timeOver = false;
        this.krislet = krislet;
        memory = new Memory();
        //team = team;
        this.side = side;
        // number = number;
        this.playMode = playMode;
        start();
    }

    //---------------------------------------------------------------------------
    // This is main brain function used to make decision
    // In each cycle we decide which command to issue based on
    // current situation. the rules are:
    //
    //	1. If you don't know where is ball then turn right and wait for new info
    //
    //	2. If ball is too far to kick it then
    //		2.1. If we are directed towards the ball then go to the ball
    //		2.2. else turn to the ball
    //
    //	3. If we don't know where is opponent goal then turn wait
    //				and wait for new info
    //
    //	4. Kick ball
    //
    //	To ensure that we don't send commands to often after each cycle
    //	we waits one simulator steps. (This of course should be done better)

    // ***************  Improvements ******************
    // Always know where the goal is.
    // Move to a place on my side on a kick_off
    // ************************************************

    public void run() {
        ObjectInfo ball;
        ObjectInfo goal_opponent;

        String nextCommand; // todo maybe an enum?

        // before kickoff
        setupFormation();

        // kickoff
        while (!timeOver) {
            nextCommand = null;

            ball = memory.getBallInfo();
            if (ball == null) {
                // If you don't know where is ball then find it
                findObject(ball);
            } else if (ball.m_distance > 1.0) {
                // If ball is too far then
                // turn to ball or
                // if we have correct direction then go to ball
                if (ball.m_direction != 0) {
                    turnTowards(ball);
                } else {
                    krislet.dash(10 * ball.m_distance);
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
                    findObject(goal_opponent);
                } else {
                    krislet.kick(100, goal_opponent.m_direction);
                }
            }

            if (nextCommand != null) {
                //do the thing
            }

            // sleep one step to ensure that we will not send
            // two commands in one cycle.
            waitForNextCycle();
        }


        // after kickoff
        krislet.bye();
    }

    private void setupFormation() {
        // Place player randomly on field TODO: change
        if (Pattern.matches("^before_kick_off.*", playMode)) {
            krislet.move(-Math.random() * 52.5, 34 - Math.random() * 68.0);
        }
    }

    private void findObject(ObjectInfo obj) {
        krislet.turn(40);
        memory.waitForNewInfo(); // todo maybe remove. should only be called in memory
    }

    private void turnTowards(ObjectInfo obj) {
        krislet.turn(obj.m_direction);
    }

    private void waitForNextCycle() {
        try {
            Thread.sleep(2 * Memory.SIMULATOR_STEP);
        } catch (Exception e) {

        }
    }


    /// check if used

    //---------------------------------------------------------------------------
    // This function sends see information
    public void see(VisualInfo info) {
        memory.store(info);
    }

    //---------------------------------------------------------------------------
    // This function receives hear information from player
    public void hear(int time, int direction, String message) {
    }

    //---------------------------------------------------------------------------
    // This function receives hear information from referee
    public void hear(int time, String message) {
        if (message.compareTo("time_over") == 0) {
            timeOver = true;
        }
    }

}
