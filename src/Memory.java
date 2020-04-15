//
//	File:			Memory.java
//	Author:		Krzysztof Langner
//	Date:			1997/04/28
//

class Memory 
{
	//===========================================================================
	// Private members
	volatile private VisualInfo	m_info;	// place where all information is stored
	final static int SIMULATOR_STEP = 100;

    public Memory()
    {
    }

    //---------------------------------------------------------------------------
    // This function puts see information into our memory
    public void store(VisualInfo info)
    {
		m_info = info;
    }

    /** @param side: 'r' or 'l' */
    public GoalInfo getGoalObj(char side){

		if (m_info == null)
		{
			waitForNewInfo();
		}

    	for (GoalInfo goalObj : m_info.getGoalList()){
    		if (goalObj.getSide() == side){
    			return goalObj;}
		}

    	return null;
	}

	public BallInfo getBallInfo(){

		if (m_info == null)
		{
			waitForNewInfo();
		}

    	return m_info.getBallInfo();
	}

    //---------------------------------------------------------------------------
    // This function waits for new visual information
    public void waitForNewInfo() 
    {
		// first remove old info
		m_info = null;
		// now wait until we get new copy
		while (m_info == null)
	    {
			// We can get information faster then 75 milliseconds
			try
			{
				Thread.sleep(SIMULATOR_STEP);
			}
			catch (Exception e)
			{
			}
	    }
    }
}
