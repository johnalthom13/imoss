package com.algo;

import com.type.Page;
import com.type.PageList;
import com.ui.ControlPanel;

public class FIFO extends AbstractFaultAlgorithm
{

    FIFO(ControlPanel controlPanel)
    {
        super(controlPanel);
    }

    /**
     * The page replacement algorithm for the memory management sumulator.
     * This method gets called whenever a page needs to be replaced.
     * <p>
     * The page replacement algorithm included with the simulator is
     * FIFO (first-in first-out).  A while or for loop should be used
     * to search through the current memory contents for a canidate
     * replacement page.  In the case of FIFO the while loop is used
     * to find the proper page while making sure that virtPageNum is
     * not exceeded.
     * <pre>
     *   Page page = ( Page ) pages.elementAt( oldestPage )
     * </pre>
     * This line brings the contents of the Page at oldestPage (a
     * specified integer) from the pages vector into the page object.
     * Next recall the contents of the target page, replacePageNum.
     * Set the physical memory address of the page to be added equal
     * to the page to be removed.
     * <pre>
     *   controlPanel.removePhysicalPage( oldestPage )
     * </pre>
     * Once a page is removed from memory it must also be reflected
     * graphically.  This line does so by removing the physical page
     * at the oldestPage value.  The page which will be added into
     * memory must also be displayed through the addPhysicalPage
     * function call.  One must also remember to reset the values of
     * the page which has just been removed from memory.
     *
     * @param pages is the vector which contains the contents of the pages
     *   in memory being simulated.  pages should be searched to find the
     *   proper page to remove, and modified to reflect any changes.
     * @param virtPageNum is the number of virtual pages in the
     *   simulator (set in Kernel.java).
     * @param replacePageNum is the requested page which caused the
     *   page fault.
     */
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

}
