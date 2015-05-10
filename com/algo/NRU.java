package com.algo;

import com.type.Page;
import com.type.PageClass;
import com.type.PageList;

public class NRU extends AbstractFaultAlgorithm
{
    @Override
    protected int getPageToReplace(PageList pages)
    {
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
    	int pageId = (int) (Math.random()*selectedPages.size());
    	Page page = pages.get(pageId);
    	while (page.getPhysicalPage() == -1)
    	{
        	pageId = (int) (Math.random()*selectedPages.size());
        	page = pages.get(pageId);
    	}
    	return pageId;
    }

	@Override
	public String toString()
	{
		return "NRU";
	}
}
