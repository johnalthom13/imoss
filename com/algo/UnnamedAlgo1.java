package com.algo;

import com.data.Constants;
import com.type.Page;
import com.type.PageClass;
import com.type.PageList;

///< @brief Same concept as NRU but instead, the order of classes is Class2, Class3, Class0, Class1
public class UnnamedAlgo1 extends AbstractFaultAlgorithm

{

	@Override
	public String toString()
	{
		return FaultAlgo.UNNAMED_ALGO1.toString();
	}

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
		PageClass[] modifiedOrder = {PageClass.CLASS_2, PageClass.CLASS_3, PageClass.CLASS_0, PageClass.CLASS_1};
    	for (PageClass pgClass : modifiedOrder)
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

	private static int totalRuns_ = 1;
}
