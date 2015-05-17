package com.algo;

import java.util.ArrayList;

import com.Virtual2Physical;
import com.type.Instruction;
import com.type.Page;
import com.type.PageList;

public class Optimal extends AbstractFaultAlgorithm
{
    
    @Override
    protected int getPageToReplace(PageList pages)
    {
        int longestNextRun = -1;
        Page longestNextRunPage = null;
        boolean hasPageAgain = false;
    	if (runCount_ == pages.size())
    	{
    		runCount_ = 0;
    	}
    	++runCount_;
        for (Page page : pages)
        {
            if (!page.isValidPhysicalAddress()) continue;

            hasPageAgain = false;
            for (int i = runCount_ + 1; i < instructionList_.size(); i++)
            {
                Instruction inst = instructionList_.get(i);
                if (Virtual2Physical.getPageNumberFromAddress(inst.getAddress()) == page.getId())
                {
                    hasPageAgain = true;
                    if (i > longestNextRun)
                    {
                        longestNextRun = i;
                        longestNextRunPage = page;
                    }
                    break;
                }
            }
            if(!hasPageAgain)
            {
                longestNextRunPage = page;
                break;
            }
        }
        return longestNextRunPage.getId();
    }
    
    public void setInstructionList(ArrayList<Instruction> instList)
    {
    	instructionList_ = instList;
    }
    
	@Override
	public String toString()
	{
		return FaultAlgo.OPT.toString();
	}

	private static int runCount_ = 0;
	private static ArrayList<Instruction> instructionList_ = null;
}
