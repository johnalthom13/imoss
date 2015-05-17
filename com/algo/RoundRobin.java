package com.algo;

import com.type.Page;
import com.type.PageList;

public class RoundRobin extends AbstractFaultAlgorithm
{
    
    @Override
    protected int getPageToReplace(PageList pages)
    {
        int pageToReplace = -1;
        for (Page page : pages)
        {
            if (page.getPhysicalPage() == rrNextPage_)
            {
                pageToReplace = page.getId();
                break;
            }
        }
        rrNextPage_ = ++rrNextPage_ % getNumberOfPhysicalPages(pages);
        return pageToReplace;
    }

	@Override
	public String toString()
	{
		return FaultAlgo.ROUND_ROBIN.toString();
	}
	
    private int getNumberOfPhysicalPages(PageList mem)
    {
        int count = 0;
        for (Page page : mem)
        {
            if (page.isValidPhysicalAddress())
            {
                count++;
            }
        }
        return count;
    }
    private static int rrNextPage_ = 0;
}
