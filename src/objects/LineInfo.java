package objects;

public class LineInfo extends ObjectInfo {
    private char m_kind;  // l|r|t|b

    public LineInfo() {
        super(ObjectType.LINE);
    }

    public LineInfo(char kind) {
        super (ObjectType.LINE);
        m_kind = kind;
    }
}
