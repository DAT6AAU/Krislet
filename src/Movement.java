public class  Movement {

    private Brain brain;

    private final double positionDelta = 0.3;

    public Movement(Brain brain) {
        brain = this.brain;
    }

    //Makes the player move to the coordinate
    //returns true if at coordinate
    //public boolean moveTo(Object)
    public boolean moveTo(double destX, double destY){
        if(isAtPosition(destX, destY)){
            return true;
        }

        if(pointTowards(0,0)){ //TODO
            //move foward //TODO
            return false;
        }

        //todo???
        return false; //TODO
    }


    //Points the player towards given coordinate, returns true if this is the case
    private boolean pointTowards(double x, double y){
        //TODO
        return false;
    }

    /** Checks if the players current position matches the given coordinates. Returns true if player is at pos. */
    private boolean isAtPosition(double destX, double destY){

        double xDifAbs = Math.abs(destX - brain.myPosition[0]);
        double yDifAbs = Math.abs(destY - brain.myPosition[1]);

        return xDifAbs <= positionDelta && yDifAbs <= positionDelta;
    }
}
