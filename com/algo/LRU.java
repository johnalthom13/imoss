package com.algo;

import java.util.ArrayList;

import com.type.Page;
import com.ui.ControlPanel;

public class LRU extends AbstractFaultAlgorithm
{

    LRU(ControlPanel controlPanel)
    {
        super(controlPanel);
    }
    
    @Override
    protected int getPageToReplace(ArrayList<Page> pages, long virtualPageNum)
    {
        int pageToReplace = -1;
        int usedLongAgo = -1;
        for (Page page : pages)
        {
            if (page.lastTouchTime_ > usedLongAgo)
            {
                usedLongAgo = page.lastTouchTime_;
                pageToReplace = page.getId();
            }
        }
        return pageToReplace;
    }

}
