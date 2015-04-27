package com.algo;

import com.type.Page;
import com.type.PageList;

public class LRU extends AbstractFaultAlgorithm
{
    
    @Override
    protected int getPageToReplace(PageList pages)
    {
        int pageToReplace = -1;
        int usedLongAgo = -1;
        for (Page page : pages)
        {
            if (page.getLastTouchTime() > usedLongAgo)
            {
                usedLongAgo = page.getLastTouchTime();
                pageToReplace = page.getId();
            }
        }
        return pageToReplace;
    }
    
	@Override
	public String toString()
	{
		return "LRU";
	}

}
