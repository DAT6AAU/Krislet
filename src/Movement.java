import java.awt.geom.Point2D;

public class Movement {

    private Brain brain;
    private PlayerPhysicalEstimator playerPhysicalEstimator;

    private final double positionDelta = 0.3;
    private final double lookingDirectionDelta = 10; //TODO
    private final double defaultMovingAmount = 40; //TODO Change or something!

    public ActionType currentAction = ActionType.NOTHING;

    public Movement(Brain brain, PlayerPhysicalEstimator playerPhysicalEstimator) {
        this.brain = brain;
        this.playerPhysicalEstimator = playerPhysicalEstimator;
    }

    //Makes the player move to the coordinate
    //returns true if at coordinate
    //public boolean moveTo(Object)
    public boolean moveTo(double destX, double destY){
        if (isAtPosition(destX, destY)){
            System.out.println("Is at position!");
            currentAction = ActionType.NOTHING;
            currentAction.amount = 0;
            return true;
        }

        if (isLookingAt(destX, destY)){ //TODO
            //move foward //TODO
            System.out.println("Is not at pos, but looking at pos!");
            currentAction = ActionType.MOVE_FORWARD;
            currentAction.amount = defaultMovingAmount;
            return false;
        }

        System.out.println("Not at pos, not looking at it!");
        return pointTowards(destX, destY); //TODO return value does not make sense here
    }


    //Points the player towards given coordinate, returns true if this is the case
    private boolean pointTowards(double x, double y){
        if(isLookingAt(x, y)){
            return true;
        }

        //Hvor meget skal jeg roterer for at kigge den ønskede retning??

        Point2D.Double VecMeToDestOrigined = playerPhysicalEstimator.getVectorMovedToZeroZero(new Point2D.Double(brain.currentPosition[0], brain.currentPosition[1]), new Point2D.Double(x, y));
        double angle = playerPhysicalEstimator.getAngleBetweenVector(brain.lookingDirectionVector, VecMeToDestOrigined);

        currentAction = ActionType.TURN_LEFT; //TODO should maybe not be left or right.. the angle could contain that info: -/+
        //currentAction.amount = angle; //TODO
        currentAction.amount = 3;

        return false;
    }

    private boolean isLookingAt(double x, double y){
        Point2D.Double VecMeToDestOrigined = playerPhysicalEstimator.getVectorMovedToZeroZero(new Point2D.Double(brain.currentPosition[0], brain.currentPosition[1]), new Point2D.Double(x, y));
        double angle = playerPhysicalEstimator.getAngleBetweenVector(brain.lookingDirectionVector, VecMeToDestOrigined);
        System.out.println("Movement: isLookingAt: angleDiff: " + angle);
        return angle < lookingDirectionDelta;
    }

    /** Checks if the players current position matches the given coordinates. Returns true if player is at pos. */
    private boolean isAtPosition(double destX, double destY){
        double xDifAbs = Math.abs(destX - brain.currentPosition[0]);
        double yDifAbs = Math.abs(destY - brain.currentPosition[1]);

        return xDifAbs <= positionDelta && yDifAbs <= positionDelta;
    }
}
