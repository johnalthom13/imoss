package com.algo;

import com.type.Page;
import com.type.PageList;
import com.ui.ControlPanel;

public class RoundRobin extends AbstractFaultAlgorithm
{

    RoundRobin(ControlPanel controlPanel)
    {
        super(controlPanel);
    }
    
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

    private int getNumberOfPhysicalPages(PageList mem )
    {
        int count = 0;
        for (Page page : mem)
        {
            if (page.getPhysicalPage() != -1)
            {
                count++;
            }
        }
        return count;
    }
    private static int rrNextPage_ = 0;
}
