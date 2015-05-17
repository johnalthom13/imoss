package com.algo;

import com.type.Page;
import com.type.PageList;

public class SectionalSelect extends AbstractFaultAlgorithm
{
    
    @Override
    protected int getPageToReplace(PageList pages)
    {
    	if (runCount_ == pages.size())
    	{
    		runCount_ = 0;
    	}
    	++runCount_;
    	switch (runCount_%3)
    	{
    	case 0:
    		return getFromLastSection(pages);
    	case 1:
    		return getFromBeginSection(pages);
    	case 2:
    		return getRandomly(pages);
    	}
    	
        return -1;
    }
    
	private int getFromBeginSection(PageList pages)
	{
		for (Page page : pages)
        {
        	if (page.isValidPhysicalAddress())
        	{
        		return page.getId();
        	}
        }
		return -1;
	}
    
	private int getFromLastSection(PageList pages)
	{
		for (int i = pages.size()-1; i >= 0; i--)
        {
			Page page = pages.get(i);
        	if (page.isValidPhysicalAddress())
        	{
        		return page.getId();
        	}
        }
		return -1;
	}

	private int getRandomly(PageList pages)
	{
		int pageId = (int) (Math.random()*pages.size());
    	Page page = pages.get(pageId);
    	while (page.getPhysicalPage() == -1)
    	{
        	pageId = (int) (Math.random()*pages.size());
        	page = pages.get(pageId);
    	}
    	return pageId;
	}

	@Override
	public String toString()
	{
		return FaultAlgo.SECTIONAL.toString();
	}

	private static int runCount_ = 0;
}
