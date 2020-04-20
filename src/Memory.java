//
//	File:			Memory.java
//	Author:		Krzysztof Langner
//	Date:			1997/04/28
//

import objects.BallInfo;
import objects.GoalInfo;

class Memory {
    volatile private VisualInfo info;    // place where all information is stored
    final static int SIMULATOR_STEP = 100;

    public Memory() {
    }

    //---------------------------------------------------------------------------
    // This function puts see information into our memory
    public void store(VisualInfo info) {
        this.info = info;
    }

    /**
     * @param side: 'r' or 'l'
     */
    public GoalInfo getGoalObj(char side) {

        if (info == null) {
            waitForNewInfo();
        }

        for (GoalInfo goalObj : info.getGoalList()) {
            if (goalObj.getSide() == side) {
                return goalObj;
            }
        }

        return null;
    }

    public BallInfo getBallInfo() {

        if (info == null) {
            waitForNewInfo();
        }

        return info.getBallInfo();
    }

    //---------------------------------------------------------------------------
    // This function waits for new visual information
    public void waitForNewInfo() {
        // first remove old info
        info = null;
        // now wait until we get new copy
        while (info == null) {
            // We can get information faster then 75 milliseconds
            try {
                Thread.sleep(SIMULATOR_STEP);
            } catch (Exception e) {
            }
        }
    }
}
