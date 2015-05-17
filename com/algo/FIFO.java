package com.algo;

import com.type.Page;
import com.type.PageList;

public class FIFO extends AbstractFaultAlgorithm
{

    @Override
    protected int getPageToReplace(PageList pages)
    {
        int oldestPage = -1;
        int oldestTime = -1;
        for (Page page : pages)
        {
            if (page.getPhysicalPage() != -1 && page.getInMemoryTime() > oldestTime)
            {
                oldestPage = page.getId();
                oldestTime = page.getInMemoryTime();
            }
        }

        return oldestPage;
    }

	@Override
	public String toString()
	{
		return FaultAlgo.FIFO.toString();
	}

}
