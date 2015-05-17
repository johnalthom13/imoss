package com.algo;

import com.type.Page;
import com.type.PageList;

// TODO Fix this
public class MRU extends AbstractFaultAlgorithm
{
    
    @Override
    protected int getPageToReplace(PageList pages)
    {
        int pageToReplace = 0;
        int minTime = 0;
        for (Page page : pages)
        {
        	if (!page.isValidPhysicalAddress()) continue;
            if (page.getInMemoryTime() < minTime)
            {
                pageToReplace = page.getId();
                minTime = page.getInMemoryTime();
            }
        }
        return pageToReplace;
    }
    
	@Override
	public String toString()
	{
		return FaultAlgo.MRU.toString();
	}

}
