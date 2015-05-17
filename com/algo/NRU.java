package com.algo;

import com.data.Constants;
import com.type.Page;
import com.type.PageClass;
import com.type.PageList;

public class NRU extends AbstractFaultAlgorithm
{	
    @Override
    protected int getPageToReplace(PageList pages)
    {
    	if (totalRuns_%Constants.PERIODIC_CLEAR_COUNT == 0)
    	{
    		pages.clearRefereceBits();
    	}
    	if (totalRuns_ == pages.size())
    	{
    		totalRuns_ = 0;
    	}
    	
    	PageList selectedPages = new PageList();
    	for (PageClass pgClass : PageClass.values())
    	{
    		selectedPages = pages.getAllFromClass(pgClass);
    		if (!selectedPages.isEmpty())
    		{
    			System.err.println(pgClass);
    			break;
    		}
    	}
    	
    	int index = (int) (Math.random()*selectedPages.size());
    	Page toBeReplaced = selectedPages.get(index);
    	
    	totalRuns_++;
    	return toBeReplaced.getId();
    }
    

	@Override
	public String toString()
	{
		return FaultAlgo.NRU.toString();
	}
	
	private static int totalRuns_ = 1;
	
}
