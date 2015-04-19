package com.algo;

import java.util.ArrayList;

import com.type.Page;
import com.ui.ControlPanel;

public abstract class AbstractFaultAlgorithm
{
    protected AbstractFaultAlgorithm(ControlPanel controlPanel)
    {
        controlPanel_ = controlPanel;
    }

    public void replacePage(ArrayList<Page> mem, long virtPageNum , int replacePageNum)
    {
        int pageToReplace = getPageToReplace(mem, virtPageNum);
        Page page = mem.get(pageToReplace);
        Page nextpage = mem.get(replacePageNum);
        controlPanel_.removePageAt(pageToReplace);
        nextpage.setPhysicalAddress(page.getPhysicalPage());
        controlPanel_.addPageAt(nextpage.getPhysicalPage(), replacePageNum);
        page.set(-1, (byte)0, (byte)0, 0, 0);
    }

    protected abstract int getPageToReplace(ArrayList<Page> pages, long virtPageNum);
    protected ControlPanel controlPanel_;
}
