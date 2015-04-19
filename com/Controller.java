package com;

import java.io.IOException;
import java.util.ArrayList;

import com.algo.AbstractFaultAlgorithm;
import com.data.CommandReader;
import com.data.ConfReader;
import com.data.Constants;
import com.type.Instruction;
import com.type.Page;
import com.type.PageList;


public class Controller extends Thread
{
    public void getPage(int pageNum)
    {
        Page page = pageMemList_.get(pageNum);
        controlPanel_.reset(page);
    }

    public void init(AbstractFaultAlgorithm algorithm, String commands, String config)
    {
        algorithm_ = algorithm;
        commandFilename_ = commands;
        configFilename_ = config;
        pageMemList_ = new PageList();
        instructionList_ = new ArrayList<Instruction>();

        int j = 0;
        int physical_count = 0;
        int map_count = 0;
        long high = 0;
        long addr = 0;

        this.readConfigFile(config);
        this.readCommandFile(commands);
        runs_ = 0;
        for (int i = 0; i < virtPageNum_; i++)
        {
            Page page = pageMemList_.get(i);
            if (page.isValidPhysicalAddress())
            {
                map_count++;
            }
            for (j = 0; j < virtPageNum_; j++)
            {
                Page tmp_page = pageMemList_.get(j);
                if (tmp_page.isSamePhysicalAddressWith(page) && page.isValidPhysicalAddress())
                {
                    physical_count++;
                }
            }
            if (physical_count > 1)
            {
                System.err.println("MemoryManagement: Duplicate physical page's in " + config);
                System.exit(-1);
            }
            physical_count = 0;
        }
        if ( map_count < ( virtPageNum_ +1 ) / 2 )
        {
            for (int i = 0; i <= virtPageNum_; i++)
            {
                Page page = pageMemList_.get(i);
                System.err.println(virtPageNum_);
                if (!page.isValidPhysicalAddress() && map_count < ( virtPageNum_ + 1 ) / 2 )
                {
                	System.err.println("x " + page);
                    page.setPhysicalAddress(i);
                    pageMemList_.set(i, page);
                    map_count++;
                }
            }
        }
        for (int i = 0; i < virtPageNum_; i++)
        {
            Page page = pageMemList_.get(i);
            if (!page.isValidPhysicalAddress())
            {
                controlPanel_.removePageAt(i);
            }
            else
            {
                controlPanel_.addPage(i, page);
                System.err.println(page);
            }
        }
        for (int i = 0; i < instructionList_.size(); i++)
        {
            high = block_ * virtPageNum_;
            Instruction instruct = instructionList_.get(i);
            if ( addr < 0 || addr > high )
            {
                System.err.println("MemoryManagement: Instruction (" + instruct.getInstructionCode() + " " + instruct.getAddressStr() + ") out of bounds.");
                System.exit(-1);
            }
        }
    }

    private void readCommandFile(String commands)
    {
        try
        {
            instructionList_ = CommandReader.readCommands(commands, computeAddressLimit());
        }
        catch (NumberFormatException | IOException e)
        {
            e.printStackTrace();
        }
        if (instructionList_.isEmpty())
        {
            System.err.println("MemoryManagement: no instructions present for execution.");
            System.exit(-1);
        }
    }

    private void readConfigFile(String config)
    {
        ConfReader reader = ConfReader.create(config);

        try
        {
            byte R = 0, M = 0;
            long vPageNum = reader.fetchVirtualPageNumber();
            virtPageNum_ = vPageNum;
            for (int i = 0; i <= virtPageNum_; i++)
            {
                long high = (block_ * (i + 1))-1;
                long low = block_ * i;
                pageMemList_.add(new Page(i, -1, R, M, 0, 0, high, low));
            }
        }
        catch(Exception e)
        {
            System.err.println("virtPageNum_ not found");
        }
        try
        {
            PageList temp = reader.fetchMemorySetting(pageMemList_, virtPageNum_);
            pageMemList_ = temp;
        }
        catch (Exception e)
        {
            System.err.println("Memory setting not found");
        }
        try
        {
            long temp = reader.fetchPageSize();
            System.err.println("Block size changed to " + temp);
            block_ = temp;
            pageMemList_.updateBounds(block_);
        }
        catch (Exception e)
        {
            System.err.println("Block size not found. Retained to " + block_);
        }
    }

    private long computeAddressLimit()
    {
        return (block_ * virtPageNum_+1)-1;
    }

    public boolean isRunCycleDone()
    {
        return (runs_ == instructionList_.size());
    }

    public void reset()
    {
        controlPanel_.reset();

        init(algorithm_, commandFilename_ , configFilename_);
    }

    public void run()
    {
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

    public void setControlPanel(com.ui.ControlPanel newControlPanel)
    {
        controlPanel_ = newControlPanel;
    }

    public void step()
    {
        int i = 0;

        Instruction instruct = instructionList_.get(runs_);
        long addr = instruct.getAddress();
        controlPanel_.setInstruction(instruct);
        getPage(Virtual2Physical.pageNum(instruct.getAddress(), virtPageNum_, block_));
        controlPanel_.setPageFaultPresent(false);
        if (instruct.isRead())
        {
            int pageId = Virtual2Physical.pageNum(instruct.getAddress(), virtPageNum_, block_);
            Page page = pageMemList_.get(pageId);
            if (!page.isValidPhysicalAddress())
            {
                System.out.println(instruct + " ... page fault");
                algorithm_.replacePage( pageMemList_ , virtPageNum_ , Virtual2Physical.pageNum(addr, virtPageNum_ , block_ ));
                controlPanel_.setPageFaultPresent(true);
            }
            else
            {
                page.R_ = 1;
                page.lastTouchTime_ = 0;
                System.out.println(instruct + " ... okay");
            }
            pageMemList_.set(pageId, page);
        }
        if (instruct.isWrite())
        {
            int pageId = Virtual2Physical.pageNum(addr, virtPageNum_ , block_ );
            Page page = pageMemList_.get(pageId);
            if (!page.isValidPhysicalAddress())
            {
                System.out.println(instruct + " ... page fault" );
                algorithm_.replacePage( pageMemList_ , virtPageNum_ , Virtual2Physical.pageNum(addr, virtPageNum_ , block_ ));
                controlPanel_.setPageFaultPresent(true);
            }
            else
            {
                page.M_ = 1;
                page.lastTouchTime_ = 0;
                System.out.println(instruct + " ... okay");
            }
            pageMemList_.set(pageId, page);
        }
        for ( i = 0; i < virtPageNum_; i++ )
        {
            Page page = pageMemList_.get(i);
            if ( page.R_ == 1 && page.lastTouchTime_ == 10 )
            {
                page.R_ = 0;
            }
            if (page.isValidPhysicalAddress())
            {
                page.addInMemoryTime(10);
                page.lastTouchTime_ = page.lastTouchTime_ + 10;
            }
            pageMemList_.set(i, page);
        }
        runs_++;
        controlPanel_.setTime(runs_*10);
    }
    // TODO Clean these
    private long block_ = (int) Math.pow(2,12);
    private String commandFilename_;

    private AbstractFaultAlgorithm algorithm_;
    private String configFilename_;
    private com.ui.ControlPanel controlPanel_;
    private ArrayList<Instruction> instructionList_;
    private PageList pageMemList_;

    private int runs_;
    // The number of virtual pages must be fixed at 63 due to
    // dependencies in the GUI
    private long virtPageNum_ = 0;
}
