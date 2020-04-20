package objects;

public class GoalInfo extends ObjectInfo{
    private char m_side;

    public GoalInfo() {
        super(ObjectType.GOAL);
        m_side = ' ';
    }

    public GoalInfo(char side) {
        //super("goal " + side); //TODO MAKE SURE WORKS
        super(ObjectType.FLAG);
        m_side = side;
    }

    public char getSide() {
        return m_side;
    }
}
