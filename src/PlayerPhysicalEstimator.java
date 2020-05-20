import objects.FlagInfo;
import utilities.PairGeneric;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class PlayerPhysicalEstimator {

    private final double maxFlagDistanceAllowed = 40; //Max dist to a flag that can be chosen
    private final boolean printGetPosDebugMsgs = false;

    /** Data-object used for the hardcoded flags which are placed around the field. */
    static class HardcodedFlag extends FlagInfo{
        public final double[] position;

        public HardcodedFlag(char type, char pos1, char pos2, int num, boolean out, double[] position) {
            super(type, pos1, pos2, num, out);
            this.position = position;
        }

        /** Used to identify and  compare two flags.*/
        public String getIdentifier(){
            return getM_type() + " " + getM_pos1() + " " + getM_pos2() + " " + getM_num() + " " + isM_out();
        }
    }

    private ArrayList<HardcodedFlag> hardcodedFlags = new ArrayList<>();

    public PlayerPhysicalEstimator() {
        initializeHardcodedFlags();
    }

    private void initializeHardcodedFlags(){
        //The four corners, inside
        hardcodedFlags.add(new HardcodedFlag(' ', 'l', 't', 0, false, new double[]{-52, -34}));
        hardcodedFlags.add(new HardcodedFlag(' ', 'l', 'b', 0, false, new double[]{-52, 34}));
        hardcodedFlags.add(new HardcodedFlag(' ', 'r', 't', 0, false, new double[]{52, -34}));
        hardcodedFlags.add(new HardcodedFlag(' ', 'r', 'b', 0, false, new double[]{52, 34}));

        //Goals
        hardcodedFlags.add(new HardcodedFlag('g', 'l', 't', 0, false, new double[]{-52, 7}));
        hardcodedFlags.add(new HardcodedFlag('g', 'l', 'b', 0, false, new double[]{-52, -7}));
        hardcodedFlags.add(new HardcodedFlag('g', 'r', 't', 0, false, new double[]{52, 7}));
        hardcodedFlags.add(new HardcodedFlag('g', 'r', 'b', 0, false, new double[]{52, -7}));

        //Goal boxes
        hardcodedFlags.add(new HardcodedFlag('p', 'l', 't', 0, false, new double[]{-36, -20}));
        hardcodedFlags.add(new HardcodedFlag('p', 'l', 'c', 0, false, new double[]{-36, 0}));
        hardcodedFlags.add(new HardcodedFlag('p', 'l', 'b', 0, false, new double[]{-36, 20}));
        hardcodedFlags.add(new HardcodedFlag('p', 'r', 't', 0, false, new double[]{36, -20}));
        hardcodedFlags.add(new HardcodedFlag('p', 'r', 'c', 0, false, new double[]{36, 0}));
        hardcodedFlags.add(new HardcodedFlag('p', 'r', 'b', 0, false, new double[]{36, 20}));

        //Center flag
        hardcodedFlags.add(new HardcodedFlag(' ', 'c', 't', 0, false, new double[]{0, -34}));
        hardcodedFlags.add(new HardcodedFlag(' ', 'c', 'b', 0, false, new double[]{0, 34}));
        hardcodedFlags.add(new HardcodedFlag(' ', 'c', ' ', 0, false, new double[]{0, 0}));


        //Boundry flags
        //Left
        hardcodedFlags.add(new HardcodedFlag(' ', 'l', 't', 30, false, new double[]{-58, 30}));
        hardcodedFlags.add(new HardcodedFlag(' ', 'l', 't', 20, false, new double[]{-58, 20}));
        hardcodedFlags.add(new HardcodedFlag(' ', 'l', 't', 10, false, new double[]{-58, 10}));
        hardcodedFlags.add(new HardcodedFlag(' ', 'l', ' ', 0, false, new double[]{-58, 0}));
        hardcodedFlags.add(new HardcodedFlag(' ', 'l', 'b', 10, false, new double[]{-58, 10}));
        hardcodedFlags.add(new HardcodedFlag(' ', 'l', 'b', 20, false, new double[]{-58, 20}));
        hardcodedFlags.add(new HardcodedFlag(' ', 'l', 'b', 30, false, new double[]{-58, 30}));

        //Top
        hardcodedFlags.add(new HardcodedFlag(' ', 't', 'l', 50, false, new double[]{-39, 50}));
        hardcodedFlags.add(new HardcodedFlag(' ', 't', 'l', 40, false, new double[]{-39, 40}));
        hardcodedFlags.add(new HardcodedFlag(' ', 't', 'l', 30, false, new double[]{-39, 30}));
        hardcodedFlags.add(new HardcodedFlag(' ', 't', 'l', 20, false, new double[]{-39, 20}));
        hardcodedFlags.add(new HardcodedFlag(' ', 't', 'l', 10, false, new double[]{-39, 10}));
        hardcodedFlags.add(new HardcodedFlag(' ', 't', ' ', 0, false, new double[]{0, 0}));
        hardcodedFlags.add(new HardcodedFlag(' ', 't', 'r', 10, false, new double[]{-39, 10}));
        hardcodedFlags.add(new HardcodedFlag(' ', 't', 'r', 20, false, new double[]{-39, 20}));
        hardcodedFlags.add(new HardcodedFlag(' ', 't', 'r', 30, false, new double[]{-39, 30}));
        hardcodedFlags.add(new HardcodedFlag(' ', 't', 'r', 40, false, new double[]{-39, 40}));
        hardcodedFlags.add(new HardcodedFlag(' ', 't', 'r', 50, false, new double[]{-39, 50}));

        //Bottom
        hardcodedFlags.add(new HardcodedFlag(' ', 'b', 'l', 50, false, new double[]{39, 50}));
        hardcodedFlags.add(new HardcodedFlag(' ', 'b', 'l', 30, false, new double[]{39, 30}));
        hardcodedFlags.add(new HardcodedFlag(' ', 'b', 'l', 40, false, new double[]{39, 40}));
        hardcodedFlags.add(new HardcodedFlag(' ', 'b', 'l', 20, false, new double[]{39, 20}));
        hardcodedFlags.add(new HardcodedFlag(' ', 'b', 'l', 10, false, new double[]{39, 10}));
        hardcodedFlags.add(new HardcodedFlag(' ', 'b', ' ', 0, false, new double[]{0, 0}));
        hardcodedFlags.add(new HardcodedFlag(' ', 'b', 'r', 10, false, new double[]{39, 10}));
        hardcodedFlags.add(new HardcodedFlag(' ', 'b', 'r', 20, false, new double[]{39, 20}));
        hardcodedFlags.add(new HardcodedFlag(' ', 'b', 'r', 30, false, new double[]{39, 30}));
        hardcodedFlags.add(new HardcodedFlag(' ', 'b', 'r', 40, false, new double[]{39, 40}));
        hardcodedFlags.add(new HardcodedFlag(' ', 'b', 'r', 50, false, new double[]{39, 50}));

        //Right
        hardcodedFlags.add(new HardcodedFlag(' ', 'r', 't', 30, false, new double[]{58, 30}));
        hardcodedFlags.add(new HardcodedFlag(' ', 'r', 't', 20, false, new double[]{58, 20}));
        hardcodedFlags.add(new HardcodedFlag(' ', 'r', 't', 10, false, new double[]{58, 10}));
        hardcodedFlags.add(new HardcodedFlag(' ', 'r', ' ', 0, false, new double[]{0, 0}));
        hardcodedFlags.add(new HardcodedFlag(' ', 'r', 'b', 10, false, new double[]{58, 10}));
        hardcodedFlags.add(new HardcodedFlag(' ', 'r', 'b', 20, false, new double[]{58, 20}));
        hardcodedFlags.add(new HardcodedFlag(' ', 'r', 'b', 30, false, new double[]{58, 30}));
    }

    /** Returns the players position calculated on the given flags. */
    //https://stackoverflow.com/questions/19723641/find-intersecting-point-of-three-circles-programmatically
    public double[] getPlayerPosition(ArrayList<FlagInfo> flagSeeObjects){
        // identify two hardcoded flags that the player can see
        ArrayList<PairGeneric<FlagInfo, HardcodedFlag>> flagsPlayerSees = getHardcodedFlagsThatPlayerSees(flagSeeObjects);

        //TODO optimization 1: only use flags below a max distance.
        //ArrayList<PairGeneric<FlagInfo, HardcodedFlag>> acceptedFlagsPlayerSees = removeFlagsOverDist(maxFlagDistanceAllowed, flagsPlayerSees);

        ArrayList<PairGeneric<FlagInfo, HardcodedFlag>> chosenFlags;
        if(flagsPlayerSees.size() < 3){
            if(printGetPosDebugMsgs){
                System.out.println("Not enough flags are seen: " + flagsPlayerSees.size());
            }
            return null; //does not see enough hardcoded flags
        }else{
            chosenFlags = pickThreeFlags(flagsPlayerSees);
            //TODO optimization 1: only use flags below a max distance.
            //chosenFlags = pickTwoFlags(acceptedFlagsPlayerSees);
        }

        //Get hardcoded pos of the three chosen flags
        double[] chosenFlagOnePosition = chosenFlags.get(0).getSecond().position;
        double[] chosenFlagTwoPosition = chosenFlags.get(1).getSecond().position;
        double[] chosenFlagThreePosition = chosenFlags.get(2).getSecond().position;

        //TODO remove, debugging
        //System.out.println("Chosen flag positons: " + chosenFlagOnePosition[0] + " " + chosenFlagOnePosition[1] + " Flag 2: " + chosenFlagTwoPosition[0] + " " + chosenFlagTwoPosition[1]);

        // Get player position // Results in two possibilities or none
        double[] playerPosition = getIntersections(
                chosenFlagOnePosition[0], chosenFlagOnePosition[1], chosenFlags.get(0).getFirst().m_distance,
                chosenFlagTwoPosition[0], chosenFlagTwoPosition[1], chosenFlags.get(1).getFirst().m_distance);
        if(playerPosition == null){
            if(printGetPosDebugMsgs){
                System.out.println("Could not find position. No intersections");
            }
            return null;
        }

        /* //TODO for testing
        if(playerPosition != null){
            System.out.println("Pos 1: " + playerPosition[0] + " " + playerPosition[1]);
            System.out.println("pos 2: " + playerPosition[2] + " " + playerPosition[3]);
        }*/

        //Out of bounds selection/elimination of coords
        double[] positionPossibilityOne = new double[]{ playerPosition[0], playerPosition[1]};
        double[] positionPossibilityTwo = new double[]{ playerPosition[2], playerPosition[3]};
        boolean posOneOutOfBounds = isCoordinateOutOfBounds(positionPossibilityOne);
        boolean posTwoOutOfBounds = isCoordinateOutOfBounds(positionPossibilityTwo);

        //Both coords out of bounds?
        if(posOneOutOfBounds && posTwoOutOfBounds){
            if(printGetPosDebugMsgs){
                System.out.println("Pos 1: " + positionPossibilityOne[0] + " " + positionPossibilityOne[1]);
                System.out.println("pos 2: " + positionPossibilityTwo[0] + " " + positionPossibilityTwo[1]);
                System.out.println("getPos: both out of bounds.");
            }

            return null; //Both coordinates are out of bounds
        }

        //Is both coords inside bounds? Then use triangulation //Intersection with third point
        if(!posOneOutOfBounds && !posTwoOutOfBounds){
            double distFromPlayerToThirdFlag = chosenFlags.get(2).getFirst().m_distance;
            double distFromPossibilityOneToThirdFlag = distBetweenCoords(positionPossibilityOne[0], positionPossibilityOne[1],
                    chosenFlagThreePosition[0], chosenFlagThreePosition[1]);
            double distFromPossibilityTwoToThirdFlag = distBetweenCoords(positionPossibilityTwo[0], positionPossibilityTwo[1],
                    chosenFlagThreePosition[0], chosenFlagThreePosition[1]);

            if(distFromPlayerToThirdFlag == distFromPossibilityOneToThirdFlag){
                if(printGetPosDebugMsgs){
                    System.out.println("GetPos: possibility one chosen");
                }
                return positionPossibilityOne;
            }else if(distFromPlayerToThirdFlag == distFromPossibilityTwoToThirdFlag){
                if(printGetPosDebugMsgs){
                    System.out.println("GetPos: possibility one chosen");
                }
                return positionPossibilityTwo;
            }else{
                if(printGetPosDebugMsgs){
                    System.out.println("Could not find player pos! Triangulation failed!");
                }
                return null; //Both coordinates are inside of bounds but no intersection with third point could be found
            }
        }

        //Which coordinate is inside of bounds?
        if(posOneOutOfBounds){
            if(printGetPosDebugMsgs){
                System.out.println("getPos: pos one is OOB, returning pos two.");
            }
            return positionPossibilityTwo;
        }else{
            if(printGetPosDebugMsgs){
                System.out.println("getPos: pos two is OOB, returning pos one.");
            }
            return positionPossibilityOne;
        }
    }

    /** Returns a list with all flags from given list which are below or at the distance given. */
    private ArrayList<PairGeneric<FlagInfo, HardcodedFlag>> removeFlagsOverDist(double maxDistAllowed, ArrayList<PairGeneric<FlagInfo, HardcodedFlag>> flags){
        ArrayList<PairGeneric<FlagInfo, HardcodedFlag>> acceptedFlags = new ArrayList<>();
        for(PairGeneric<FlagInfo, HardcodedFlag> flag : flags){
            if(flag.getFirst().m_distance <= maxDistAllowed){
                acceptedFlags.add(flag);
            }
        }

        return acceptedFlags;
    }

    /** Returns a pair for each given flag consisting of that flag and the matching hardcoded one. */
    private ArrayList<PairGeneric<FlagInfo, HardcodedFlag>> getHardcodedFlagsThatPlayerSees(ArrayList<FlagInfo> flagSeeObjects){
        ArrayList<PairGeneric<FlagInfo, HardcodedFlag>> flagsPlayerSees = new ArrayList<>();
        for(FlagInfo flagInfoSee : flagSeeObjects){
            for(HardcodedFlag hardcodedFlag : hardcodedFlags){
                if(flagInfoSee.isEqualTo(hardcodedFlag)){
                    flagsPlayerSees.add(new PairGeneric<>(flagInfoSee, hardcodedFlag));
                }
                /*
                if(flagsPlayerSees.size() == desiredNumberOfFlags){
                    return flagsPlayerSees;
                }
                 */
            }
        }

        return flagsPlayerSees;
    }

    /** Returns true if the given coordinate is out of bounds. */
    private boolean isCoordinateOutOfBounds(double[] coordinate){
        final int maxAbsX = 53;
        final int maxAbsY = 35;

        if(Math.abs(coordinate[0]) > maxAbsX){
            return true;
        }

        if(Math.abs(coordinate[1]) > maxAbsY) {
            return true;
        }

        return false;
    }

    /** Takes a list of flag identifiers, finds the hardcoded flag with the same identifiers and returns its position.
     //TODO is this needed? */
    private double[] getPosOfFlagFromIdentifiers(HardcodedFlag givenFlag){
        for(HardcodedFlag hardcodedFlag : hardcodedFlags){
            String hardcodedFlagIdentifier = hardcodedFlag.getIdentifier();
            if(hardcodedFlagIdentifier.length() != givenFlag.getIdentifier().length())
                continue;

            boolean isMatching = true;
            for(int i = 0; i < givenFlag.getIdentifier().length(); i++){

                if(hardcodedFlagIdentifier.equals(givenFlag.getIdentifier())){
                    isMatching = false;
                    break;
                }
            }

            if(isMatching){
                return hardcodedFlag.position;
            }
        }

        //no matching flag found
        throw new IllegalArgumentException("Could not find position of hardcoded flag! Identifier of flag: " + givenFlag.getIdentifier());
    }

    /** Takes a list of flags and returns three of them.
    * These should be picked with a specific policy
    * TODO Improve to select closest flags? */
    private ArrayList<PairGeneric<FlagInfo, HardcodedFlag>> pickThreeFlags(ArrayList<PairGeneric<FlagInfo, HardcodedFlag>> givenFlags){
        if(givenFlags.size() < 3){
            return null;
            //throw new IllegalArgumentException("Error choosing three flags. Given list only contains amount of flags: " + givenFlags.size());
        }

        PairGeneric<FlagInfo, HardcodedFlag> chosenFlagOne = null;
        PairGeneric<FlagInfo, HardcodedFlag> chosenFlagTwo = null;
        PairGeneric<FlagInfo, HardcodedFlag> chosenFlagThree = null;

        for(PairGeneric<FlagInfo, HardcodedFlag> flag : givenFlags){
            if(chosenFlagOne == null){
                chosenFlagOne = flag;
            }else if(chosenFlagTwo == null){
                chosenFlagTwo = flag;
            }else if(chosenFlagThree == null){
                chosenFlagThree = flag;
            }else if(chosenFlagOne.getFirst().m_distance > flag.getFirst().m_distance){
                chosenFlagOne = flag;
            }else if(chosenFlagTwo.getFirst().m_distance > flag.getFirst().m_distance){
                chosenFlagTwo = flag;
            }else if(chosenFlagThree.getFirst().m_distance > flag.getFirst().m_distance){
                chosenFlagThree = flag;
            }
        }

        ArrayList<PairGeneric<FlagInfo, HardcodedFlag>> chosenFlags = new ArrayList<>();
        chosenFlags.add(chosenFlagOne);
        chosenFlags.add(chosenFlagTwo);
        chosenFlags.add(chosenFlagThree);

        return chosenFlags;
    }

    /** Returns the coordinates of the third point in a triangle.
     * Params: x0, y0 = point A and r0 is its distance to the point C (unknown location)
     * Params: x1, y1 = point B and r1 is its distance to the point C (unknown location)
     * https://stackoverflow.com/questions/55816902/finding-the-intersection-of-two-circles */
    private double[] getIntersections(double x0, double y0, double r0, double x1, double y1, double r1){
        double d = Math.sqrt(Math.pow(x1 - x0, 2) + Math.pow(y1 - y0, 2));

        // non intersecting
        if(d > r0 + r1){
            return null;
        }

        // One circle within other
        if(d < Math.abs(r0 - r1)){
            return null;
        }

        // coincident circles
        if(d == 0 && r0 == r1){
            return null;
        }

        double a = (Math.pow(r0, 2) - Math.pow(r1, 2) + Math.pow(d, 2)) / (2 * d);
        double h = Math.sqrt(Math.pow(r0, 2) - Math.pow(a, 2));
        double x2 = x0 + a * (x1 - x0) / d;
        double y2 = y0 + a * (y1 - y0) / d;
        double x3 = x2 + h * (y1 - y0) / d;
        double y3 = y2 - h * (x1 - x0) / d;

        double x4 = x2 - h * (y1 - y0) / d;
        double y4 = y2 + h * (x1 - x0) / d;

        return new double[] {x3, y3, x4, y4};
    }

    /** Returns a vector pointing the direction the player looks. */
    public Point2D.Double getPlayerLookingDirection(ArrayList<FlagInfo> flagSeeObjects,double[] myPosition){
        //Find hardcoded object that i can see
        ArrayList<PairGeneric<FlagInfo, HardcodedFlag>> flagsPlayerSees = getHardcodedFlagsThatPlayerSees(flagSeeObjects);
        if(flagsPlayerSees.size() < 3){
            return null;
        }

        //todo Method should be changed to give requested amount
        PairGeneric<FlagInfo, HardcodedFlag> flag = pickThreeFlags(flagsPlayerSees).get(0);

        //Calculate looking direction vector
        double flagDirection = flag.getFirst().m_direction;
        double[] flagPosition = flag.getSecond().position;

        //System.out.println("My position: " + myPosition[0] + " " + myPosition[1]);
        //System.out.println("Flag direction: " + flagDirection);
        //System.out.println("Flag position: " + flagPosition[0] + " " + flagPosition[1]);

        Point2D.Double tempResult = getRotatedVector(flagDirection, new Point2D.Double(myPosition[0], myPosition[1]), new Point2D.Double(flagPosition[0], flagPosition[1]));
        return tempResult;
        //Point2D.Double tempResult = getRotatedVector(flagDirection, new Point2D.Double(myPosition[0], myPosition[1]), new Point2D.Double(flagPosition[0], flagPosition[1]));
        //return getRotatedVector(tempResult, 180);
    }

    /** Given vector starts at vectorStart and ends at vectorDest. */
    public Point2D.Double getRotatedVector(double degreesToRotate, Point2D.Double vectorStart, Point2D.Double vectorDest){
        //Move vector to start at 0,0
        Point2D.Double vectorStartZero = getVectorMovedToZeroZero(vectorStart, vectorDest);
        //Rotate
        return getRotatedVector(vectorStartZero, degreesToRotate);
    }

    /** Moves the given vector to 0,0 */
    public Point2D.Double getVectorMovedToZeroZero(Point2D.Double vecStart, Point2D.Double vecDest){
        return new Point2D.Double(vecDest.x - vecStart.x, vecDest.y - vecStart.y);
    }

    /** Given vector must start at 0,0 */
    public Point2D.Double getRotatedVector(Point2D.Double vec, double degreesToRotate){
        double degreesToRotateRad = (-degreesToRotate) * (Math.PI / 180);
        double rotatedVectorX = vec.x * Math.cos(degreesToRotateRad) - vec.y * Math.sin(degreesToRotateRad);
        double rotatedVectorY = vec.x * Math.sin(degreesToRotateRad) + vec.y * Math.cos(degreesToRotateRad);

        return new Point2D.Double(rotatedVectorX, rotatedVectorY);
    }

    /** Returns the angle between the two given vectors */
    public double getAngleBetweenVector(Point2D.Double vecOne, Point2D.Double vecTwo){
        double result = Math.acos(getDotProductVectors(vecOne, vecTwo) / (getMagnitudeOfVector(vecOne) + getMagnitudeOfVector(vecTwo)));
        return Math.toDegrees(result);
    }

    /* //TODO Might replace the method with the same name
    public double getAngleBetweenVector(Point2D.Double v1, Point2D.Double v2){
        double cos = scalarProduct(v1, v2) / (getMagnitudeOfVector(v1) * getMagnitudeOfVector(v2));
        double acos = Math.acos(cos);
        return Math.toDegrees(acos);
    }*/

    /** Returns the scalar product of the two given vectors. */
    private double scalarProduct(Point2D.Double v1, Point2D.Double v2) {
        double x1 = v1.x - v1.x;
        double x2 = v2.x - v2.x;
        double y1 = v1.y - v1.y;
        double y2 = v2.y - v2.y;
        return x1*x2 + y1*y2;
    }

    private double getDotProductVectors(Point2D.Double vecOne, Point2D.Double vecTwo){
        return ((vecOne.x * vecTwo.x) + (vecOne.y * vecTwo.y));
    }

    private double getMagnitudeOfVector(Point2D.Double vec){
        return Math.sqrt(Math.pow(vec.x, 2) + Math.pow(vec.y, 2));
    }

    /** Returns the distance between the two given points. (Pythagoras) */
    private double distBetweenCoords(double x1, double y1, double x2, double y2){
        return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
    }
}
