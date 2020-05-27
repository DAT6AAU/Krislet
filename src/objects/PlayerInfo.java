package objects;

public class PlayerInfo extends ObjectInfo {
    private String m_teamName = "";
    private int m_uniformName = 0; // recognise 0 as not being able to see number
    public float m_bodyDir;
    public float m_headDir;
    private boolean m_goalie = false;

    public PlayerInfo() {
        super (ObjectType.PLAYER);
    }

    public PlayerInfo(String team, int number, boolean is_goalie) {
        super(ObjectType.PLAYER);
        m_teamName = team;
        m_uniformName = number;
        m_goalie = is_goalie;
        m_bodyDir = 0;
        m_headDir = 0;
    }

    public PlayerInfo(String team, int number, float bodyDir, float headDir) {
        super(ObjectType.PLAYER);
        m_teamName = team;
        m_uniformName = number;
        m_bodyDir = bodyDir;
        m_headDir = headDir;
    }

    public String getTeamName() {
        return m_teamName;
    }

    public void setGoalie(boolean goalie) {
        m_goalie = goalie;
    }

    public boolean isGoalie() {
        return m_goalie;
    }

    public int getTeamNumber() {
        return m_uniformName;
    }
}
