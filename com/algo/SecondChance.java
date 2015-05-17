package com.algo;

import com.type.Page;
import com.type.PageList;

public class SecondChance extends AbstractFaultAlgorithm
{

	@Override
	public String toString()
	{
		return FaultAlgo.SECOND_CHANCE.toString();
	}

	@Override
	protected int getPageToReplace(PageList pages)
	{
        int oldestPage = -1;
        int oldestTime = -1;
        for (Page page : pages)
        {
            if (page.getPhysicalPage() != -1 && page.getInMemoryTime() > oldestTime)
            {
            	if (!page.isReferenced())
            	{
                    oldestPage = page.getId();
                    oldestTime = page.getInMemoryTime();
            	}
            	else
            	{
            		page.clearReferencedBit();
            		page.resetTimers();
            	}
            }
        }

        return oldestPage;
	}

}
