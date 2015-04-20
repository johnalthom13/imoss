package com.algo;

import com.type.Page;
import com.type.PageList;
import com.ui.ControlPanel;

public abstract class AbstractFaultAlgorithm
{
    protected AbstractFaultAlgorithm(ControlPanel controlPanel)
    {
        controlPanel_ = controlPanel;
    }

    public void replacePage(PageList mem, int replacePageNum)
    {
        int pageToReplace = getPageToReplace(mem);
        Page page = mem.get(pageToReplace);
        Page nextpage = mem.get(replacePageNum);
        controlPanel_.removePageAt(pageToReplace);
        nextpage.setPhysicalAddress(page.getPhysicalPage());
        controlPanel_.addPageAt(nextpage.getPhysicalPage(), replacePageNum);
        page.set(-1, (byte)0, (byte)0, 0, 0);
    }

    protected abstract int getPageToReplace(PageList pages);
    protected ControlPanel controlPanel_;
}
