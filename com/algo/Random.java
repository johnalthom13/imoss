package com.algo;

import com.type.Page;
import com.type.PageList;
import com.ui.ControlPanel;

public class Random extends AbstractFaultAlgorithm
{

    Random(ControlPanel controlPanel)
    {
        super(controlPanel);
    }
    
    @Override
    protected int getPageToReplace(PageList pages)
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
}
