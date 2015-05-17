package com.algo;

import java.util.ArrayList;

import com.type.Instruction;
import com.type.Page;
import com.type.PageList;
import com.ui.ControlPanel;

public abstract class AbstractFaultAlgorithm
{
    public Page replacePage(ControlPanel controlPanel, PageList mem, Page newPage)
    {
        int oldPageId = getPageToReplace(mem);
        Page oldPage = mem.get(oldPageId);
        Page nextpage = mem.get(newPage.getId());
        controlPanel.removePageAt(oldPageId);
        nextpage.setPhysicalAddress(oldPage.getPhysicalPage());
        controlPanel.addPageAt(nextpage.getPhysicalPage(), newPage.getId());
        return oldPage;
    }
    
    public abstract String toString();
    
    public void setInstructionList(ArrayList<Instruction> instList)
    {
    	
    }

    protected abstract int getPageToReplace(PageList pages);
}
