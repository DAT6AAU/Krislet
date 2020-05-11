package objects;

public class FlagInfo extends ObjectInfo {
    private char m_type;  // p|g
    private char m_pos1;  // t|b|l|c|r
    private char m_pos2;  // l|r|t|c|b
    private int m_num;    // 0|10|20|30|40|50
    private boolean m_out;

    public FlagInfo() {
        super(ObjectType.FLAG);
        m_type = ' ';
        m_pos1 = ' ';
        m_pos2 = ' ';
        m_num = 0;
        m_out = false;
    }

    public FlagInfo(char type, char pos1, char pos2, int num, boolean out) {
        super(ObjectType.FLAG);
        m_type = type;
        m_pos1 = pos1;
        m_pos2 = pos2;
        m_num = num;
        m_out = out;
    }

    public boolean isEqualTo(FlagInfo other){
        if(m_type != other.getM_type()){
            return false;
        }

        if(m_pos1 != other.getM_pos1()){
            return false;
        }

        if(m_pos2 != other.getM_pos2()){
            return false;
        }

        if(m_num != other.getM_num()){
            return false;
        }

        if(m_out != other.isM_out()){
            return false;
        }

        return true;
    }

    public char getM_type() {
        return m_type;
    }

    public char getM_pos1() {
        return m_pos1;
    }

    public char getM_pos2() {
        return m_pos2;
    }

    public int getM_num() {
        return m_num;
    }

    public boolean isM_out() {
        return m_out;
    }
}