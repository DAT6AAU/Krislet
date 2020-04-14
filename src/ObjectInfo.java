//
//	File:			ObjectInfo.java
//	Author:		Krzysztof Langner
//	Date:			1997/04/28

//  Modified by:  Paul Marlow, Amir Ghavam, Yoga Selvaraj
//  Course:       Software Agents
//  Date Due:     November 30, 2000

//  Modified by:  Edgar Acosta
//  Date:         March 4, 2008

//***************************************************************************
//
//	This is base class for different classese with visual information
//	about objects
//
//***************************************************************************
class ObjectInfo
{
	public ObjectType m_type;
	public float m_distance;
	public float m_direction;
	public float m_distChange;
	public float m_dirChange;

	//===========================================================================
	// Initialization member functions
	public ObjectInfo(ObjectType type)
	{
		 m_type = type;
	}

	public float getDistance()
	{
		return m_distance;
	}

	public float getDirection()
	{
		return m_direction;
	}

	public float getDistChange()
	{
		return m_distChange;
	}

	public float getDirChange()
	{
		return m_dirChange;
	}

	public ObjectType getType()
	{
		return m_type;
	}
}

enum ObjectType{
	BALL("ball"), FLAG("flag"), GOAL("goal"), LINE("line"), PLAYER("player");

	private String identifier;

	ObjectType(String identifier) {
		this.identifier = identifier;
	}

	public static ObjectType getTypeFromIdentifier(String identifier){
		for(ObjectType type : ObjectType.values()){
			if(type.identifier == identifier)
				return type;
		}

		throw new IllegalArgumentException("Did not receive a valid identifier!");
	}
}

//***************************************************************************
//
//	This class holds visual information about player
//
//***************************************************************************
class PlayerInfo extends ObjectInfo
{
	String  m_teamName = "";
	int m_uniformName = 0;        // recognise 0 as not being able to see number
	float m_bodyDir;
	float m_headDir;
	boolean m_goalie = false;

	//===========================================================================
	// Initialization member functions
	public PlayerInfo()
	{
		super(ObjectType.PLAYER);
	}

	public PlayerInfo(String team, int number, boolean is_goalie)
	{
		super(ObjectType.PLAYER);
		m_teamName = team;
		m_uniformName = number;
		m_goalie = is_goalie;
		m_bodyDir = 0;
		m_headDir = 0;
	}

	public PlayerInfo(String team, int number, float bodyDir, float headDir)
	{
		super(ObjectType.PLAYER);
		m_teamName = team;
		m_uniformName = number;
		m_bodyDir = bodyDir;
		m_headDir = headDir;
	}

	public String getTeamName()
	{
		return m_teamName;
	}

	public void setGoalie(boolean goalie)
	{
		m_goalie = goalie;
	}

	public boolean isGoalie()
	{
		return m_goalie;
	}

	public int getTeamNumber()
	{
		return m_uniformName;
	}
}

//***************************************************************************
//
//	This class holds visual information about goal
//
//***************************************************************************
class GoalInfo extends ObjectInfo
{
	private char m_side;
	//===========================================================================
	// Initialization member functions
	public GoalInfo()
	{
		super(ObjectType.GOAL);
		m_side = ' ';
	}

	public GoalInfo(char side)
	{
		//super("goal " + side); //TODO MAKE SURE WORKS
		super( ObjectType.FLAG);
		m_side = side;
	}

	public char getSide()
    {
	    return m_side;
    }
}

//***************************************************************************
//
//	This class holds visual information about ball
//
//***************************************************************************
class BallInfo extends ObjectInfo
{
	//===========================================================================
	// Initialization member functions
	public BallInfo()
  {
	super(ObjectType.BALL);
  }
}

//***************************************************************************
//
//	This class holds visual information about flag
//
//***************************************************************************
class FlagInfo extends ObjectInfo
{
	char m_type;  // p|g
	char m_pos1;  // t|b|l|c|r
	char m_pos2;  // l|r|t|c|b
	int m_num;    // 0|10|20|30|40|50
	boolean m_out;

	//===========================================================================
	// Initialization member functions
	public FlagInfo()
	{
		super(ObjectType.FLAG);
		m_type = ' ';
		m_pos1 = ' ';
		m_pos2 = ' ';
		m_num = 0;
		m_out = false;
	}

	public FlagInfo(char type, char pos1, char pos2, int num, boolean out)
	{
		super(ObjectType.FLAG);
		m_type = type;
		m_pos1 = pos1;
		m_pos2 = pos2;
		m_num = num;
		m_out = out;
	}
}

//***************************************************************************
//
//	This class holds visual information about line
//
//***************************************************************************
class LineInfo extends ObjectInfo
{
	char m_kind;  // l|r|t|b

	//===========================================================================
	// Initialization member functions
	public LineInfo()
	{
		super(ObjectType.LINE);
	}

	public LineInfo(char kind)
	{
		super(ObjectType.LINE);
		m_kind = kind;
	}
}