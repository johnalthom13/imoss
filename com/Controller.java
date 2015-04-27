package com;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.algo.AbstractFaultAlgorithm;
import com.data.ConfigData;
import com.data.Constants;
import com.type.Instruction;
import com.type.Page;
import com.type.PageList;
import com.ui.ControlPanel;

public class Controller extends Thread implements PropertyChangeListener
{		
	public Controller(ControlPanel controlPanel, AbstractFaultAlgorithm algo)
	{
		algorithm_ = algo;
		setup(controlPanel);
	}

	public Controller(ControlPanel ctrlPanel)
	{
		setup(ctrlPanel);
	}

	@SuppressWarnings("unchecked")
	private void setup(ControlPanel controlPanel)
	{
		controlPanelRef_ = controlPanel;
        runs_ = 0;
        pageMemList_ = new PageList();
        instructionList_ = (ArrayList<Instruction>) ConfigData.INSTRUCTIONS_LIST.clone();
        populatePageList();
        init();
	}
	
    private void populatePageList()
    {
        for (int i = 0; i <= ConfigData.VIRTUAL_PAGE_COUNT; i++)
        {
            long high = (ConfigData.BLOCK_SIZE * (i + 1))-1;
            long low = ConfigData.BLOCK_SIZE * i;
            pageMemList_.add(new Page(i, -1, 0, 0, high, low));
        }
	}

	public void getPage(int pageNum)
    {
        Page page = pageMemList_.get(pageNum);
        controlPanelRef_.reset(page);
    }
        
    private void init()
    {
        int map_count = 0;
        int virtualPageCount = ConfigData.VIRTUAL_PAGE_COUNT;
        for (int i = 0; i < virtualPageCount; i++)
        {
            Page page = pageMemList_.get(i);
            if (page.isValidPhysicalAddress())
            {
                map_count++;
            }
        }
        if (map_count < (virtualPageCount+1)/2)
        {
            for (int i = 0; i < pageMemList_.size(); i++)
            {
                Page page = pageMemList_.get(i);
                if (!page.isValidPhysicalAddress() && map_count < (virtualPageCount + 1)/2)
                {
                	System.err.println("x " + page);
                    page.setPhysicalAddress(i);
                    pageMemList_.set(i, page);
                    map_count++;
                }
            }
        }
        initPhysicalPages(virtualPageCount);
    }

	private void initPhysicalPages(int virtualPageCount)
	{
		for (int i = 0; i < virtualPageCount; i++)
        {
            Page page = pageMemList_.get(i);
            if (!page.isValidPhysicalAddress())
            {
                controlPanelRef_.removePageAt(i);
            }
            else
            {
                controlPanelRef_.addPage(i, page);
            }
        }
	}

    public boolean isRunCycleDone()
    {
        return (runs_ == instructionList_.size());
    }

    private void reset()
    {
        controlPanelRef_.reset();
        initPhysicalPages(ConfigData.VIRTUAL_PAGE_COUNT);
    }

    public void run()
    {
    	System.err.println("Running  " + algorithm_);
        step();
        while (!isRunCycleDone())
        {
            try
            {
                Thread.sleep(Constants.ANIMATION_DELAY);
            }
            catch(InterruptedException e)
            {
                /* Do nothing */
            }
            step();
        }
    }

    public void step()
    {
    	if (runs_ >= instructionList_.size())
    	{
    		JOptionPane.showMessageDialog(null, "ERROR");
    		return;
    	}
        Instruction instruct = instructionList_.get(runs_);
        long addr = instruct.getAddress();
        controlPanelRef_.setInstruction(instruct);
        getPage(Virtual2Physical.getPageNumberFromAddress(instruct.getAddress()));
        controlPanelRef_.setPageFaultPresent(false);
        if (instruct.isRead())
        {
            int pageId = Virtual2Physical.getPageNumberFromAddress(instruct.getAddress());
            Page page = pageMemList_.get(pageId);
            if (!page.isValidPhysicalAddress())
            {
                System.out.println(instruct + " ... page fault");
                algorithm_.replacePage(controlPanelRef_, pageMemList_, Virtual2Physical.getPageNumberFromAddress(addr));
                controlPanelRef_.setPageFaultPresent(true);
            }
            else
            {
                page.setAsReferenced();
                System.out.println(instruct + " ... okay");
            }
            pageMemList_.set(pageId, page);
        }
        if (instruct.isWrite())
        {
            int pageId = Virtual2Physical.getPageNumberFromAddress(addr);
            Page page = pageMemList_.get(pageId);
            if (!page.isValidPhysicalAddress())
            {
                System.out.println(instruct + " ... page fault" );
                algorithm_.replacePage(controlPanelRef_, pageMemList_, Virtual2Physical.getPageNumberFromAddress(addr));
                controlPanelRef_.setPageFaultPresent(true);
            }
            else
            {
                page.setAsModified();
                System.out.println(instruct + " ... okay");
            }
            pageMemList_.set(pageId, page);
        }
        
        pageMemList_.refreshTimers();
        runs_++;
        // TODO Fix magic number
        controlPanelRef_.setTime(runs_*10);
    }

	private void setAlgorithm(AbstractFaultAlgorithm algo)
	{
		algorithm_ = algo;
	}

	@Override
	public void propertyChange(PropertyChangeEvent event)
	{
		switch (event.getPropertyName())
		{
		case Constants.ALGORITHM_PROPERTY:
			setAlgorithm((AbstractFaultAlgorithm) event.getNewValue());
			break;
		case Constants.RESET_PROPERTY:
			reset();
			break;
		case Constants.START_PROPERTY:
			controlPanelRef_.setController(new Controller(controlPanelRef_));
			start();
			break;
		}
	}
	
	private ControlPanel controlPanelRef_;
    private AbstractFaultAlgorithm algorithm_;
    private ArrayList<Instruction> instructionList_;
    private PageList pageMemList_;

    private int runs_;

}
