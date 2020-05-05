import objects.FlagInfo;
import utilities.PairGeneric;

import java.util.ArrayList;
import java.util.HashMap;

public class MathTest {

    /** Data-object used for the hardcoded flags which are placed around the field. */
    class HardcodedFlag extends FlagInfo{
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

    ArrayList<HardcodedFlag> hardcodedFlags = new ArrayList<>();

    public MathTest() {
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
    public double[] getPlayerPosition(ArrayList<FlagInfo> flagSeeObjects){
        // identify two hardcoded flags that the player can see
        // TODO: Optimize: stop at two flags found
        ArrayList<PairGeneric<FlagInfo, HardcodedFlag>> flagsPlayerSees = new ArrayList<>();
        for(FlagInfo flagInfoSee : flagSeeObjects){
            for(HardcodedFlag hardcodedFlag : hardcodedFlags){
                if(flagInfoSee.isEqualTo(hardcodedFlag)){
                    flagsPlayerSees.add(new PairGeneric<>(flagInfoSee, hardcodedFlag));
                }
            }
        }

        ArrayList<PairGeneric<FlagInfo, HardcodedFlag>> chosenFlags;
        if(flagsPlayerSees.size() < 2){
            return null; //does not see enough hardcoded flags
        }else{
            chosenFlags = pickTwoFlags(flagsPlayerSees);
        }

        //Get hardcoded pos of the two chosen flags
        double[] chosenFlagOnePosition = chosenFlags.get(0).getSecond().position;
        double[] chosenFlagTwoPosition = chosenFlags.get(1).getSecond().position;

        //System.out.println("Chosen flag positons: " + chosenFlags.get(0).getIdentifier() + ": " + chosenFlagOnePosition[0] + ";" + chosenFlagOnePosition[1] + " " + chosenFlags.get(1).getIdentifier() + ": " + chosenFlagTwoPosition[0] + ";" + chosenFlagTwoPosition[1]);

        // Get player position // Results in two possibilities
        double[] playerPosition = getIntersections(
                chosenFlagOnePosition[0], chosenFlagOnePosition[1], chosenFlags.get(0).getFirst().m_distance,
                chosenFlagTwoPosition[0], chosenFlagTwoPosition[1], chosenFlags.get(1).getFirst().m_distance);
        if(playerPosition == null){
            return null;
        }

        //TODO for testing
        /*
        if(playerPosition != null){
            System.out.println("Pos 1: " + playerPosition[0] + " " + playerPosition[1]);
            System.out.println("pos 2: " + playerPosition[2] + " " + playerPosition[3]);
        }*/

        //Out of bounds selection/elimination of coords
        double[] positionPossibilityOne = new double[]{ playerPosition[0], playerPosition[1]};
        double[] positionPossibilityTwo = new double[]{ playerPosition[2], playerPosition[3]};
        boolean posOneOutOfBounds = isCoordinateOutOfBounds(positionPossibilityOne);
        boolean posTwoOutOfBounds = isCoordinateOutOfBounds(positionPossibilityTwo);

        if(posOneOutOfBounds && posTwoOutOfBounds){
            return null; //Both coordinates are out of bounds
        }

        if(!posOneOutOfBounds && !posTwoOutOfBounds){
            return null; //Both coordinates are inside of bounds //Todo - how to decide?! Does it happen?
        }

        if(posOneOutOfBounds){
            return positionPossibilityTwo;
        }else{
            return positionPossibilityOne;
        }
    }

    /** Returns true if the given coordinate is out of bounds. */
    private boolean isCoordinateOutOfBounds(double[] coordinate){
        int maxAbsX = 58;
        int maxAbsY = 39;

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

    /** Takes a list of flags and returns two of them.
     * These should be picked with a specific policy
     * TODO Improve to select closest flags? */
    private ArrayList<PairGeneric<FlagInfo, HardcodedFlag>> pickTwoFlags(ArrayList<PairGeneric<FlagInfo, HardcodedFlag>> givenFlags){
        if(givenFlags.size() < 2)
            throw new IllegalArgumentException("Error choosing two flags. Given list only contains amount of flags: " + givenFlags.size());

        //TODO temp: selection should be optimized
        ArrayList<PairGeneric<FlagInfo, HardcodedFlag>> chosenFlags = new ArrayList<>();
        chosenFlags.add(givenFlags.get(0));
        chosenFlags.add(givenFlags.get(1));

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
}
