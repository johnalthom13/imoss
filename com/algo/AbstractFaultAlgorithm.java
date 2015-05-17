package com.algo;

import com.type.Page;
import com.type.PageList;
import com.ui.ControlPanel;

public abstract class AbstractFaultAlgorithm
{
    public void replacePage(ControlPanel controlPanel, PageList mem, int replacePageNum)
    {
        int pageToReplace = getPageToReplace(mem);
        Page page = mem.get(pageToReplace);
        Page nextpage = mem.get(replacePageNum);
        controlPanel.removePageAt(pageToReplace);
        nextpage.setPhysicalAddress(page.getPhysicalPage());
        controlPanel.addPageAt(nextpage.getPhysicalPage(), replacePageNum);
        page.reset();
    }
    
    public abstract String toString();

    protected abstract int getPageToReplace(PageList pages);
}
