package com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.algo.AbstractFaultAlgorithm;


public class Controller extends Thread
{
    public void getPage(int pageNum)
    {
        Page page = pageMemList_.get( pageNum );
        controlPanel_.reset(page);
    }

    public void init(AbstractFaultAlgorithm algorithm, String commands, String config)
    {
        File f = new File( commands );
        algorithm_ = algorithm;
        commandFilename_ = commands;
        configFilename_ = config;
        pageMemList_ = new ArrayList<Page>();
        instructionList_ = new ArrayList<Instruction>();
        
        String line;
        String tmp = null;
        String command = "";
        byte R = 0;
        byte M = 0;
        int i = 0;
        int j = 0;
        int id = 0;
        int physical = 0;
        int physical_count = 0;
        int inMemTime = 0;
        int lastTouchTime_ = 0;
        int map_count = 0;
        double power = 14;
        long high = 0;
        long low = 0;
        long addr = 0;
        long address_limit = (block_ * virtPageNum_+1)-1;

        if ( config != null )
        {
            f = new File ( config );
            try
            {
                BufferedReader in = new BufferedReader(new FileReader(f));
                while ((line = in.readLine()) != null)
                {
                    if (line.startsWith("numpages"))
                    {
                        StringTokenizer st = new StringTokenizer(line);
                        while (st.hasMoreTokens())
                        {
                            tmp = st.nextToken();
                            virtPageNum_ = Common.stringToInt(st.nextToken()) - 1;
                            if ( virtPageNum_ < Constants.MIN_PAGE_COUNT || virtPageNum_ >= Constants.MAX_PAGE_COUNT)
                            {
                                System.out.println("MemoryManagement: numpages out of bounds.");
                                System.exit(-1);
                            }
                            address_limit = (block_ * virtPageNum_+1)-1;
                        }
                    }
                }
                in.close();
            }
            catch (IOException e)
            {
                /* Handle exceptions */
            }
            for (i = 0; i <= virtPageNum_; i++)
            {
                high = (block_ * (i + 1))-1;
                low = block_ * i;
                pageMemList_.add(new Page(i, -1, R, M, 0, 0, high, low));
            }
            try
            {
                BufferedReader in = new BufferedReader(new FileReader(f));
                while ((line = in.readLine()) != null)

                {
                    if (line.startsWith("memset"))
                    {
                        StringTokenizer st = new StringTokenizer(line);
                        st.nextToken();
                        while (st.hasMoreTokens())
                        {
                            id = Common.stringToInt(st.nextToken());
                            tmp = st.nextToken();
                            if (tmp.startsWith("x"))
                            {
                                physical = -1;
                            }
                            else
                            {
                                physical = Common.stringToInt(tmp);
                            }
                            if ((0 > id || id > virtPageNum_) || (-1 > physical || physical > ((virtPageNum_ - 1) / 2)))
                            {
                                System.out.println("MemoryManagement: Invalid page value in " + config);
                                System.exit(-1);
                            }
                            R = Common.stringToByte(st.nextToken());
                            if (R < 0 || R > 1)
                            {
                                System.out.println("MemoryManagement: Invalid R value in " + config);
                                System.exit(-1);
                            }
                            M = Common.stringToByte(st.nextToken());
                            if (M < 0 || M > 1)
                            {
                                System.out.println("MemoryManagement: Invalid M value in " + config);
                                System.exit(-1);
                            }
                            inMemTime = Common.stringToInt(st.nextToken());
                            if (inMemTime < 0)
                            {
                                System.out.println("MemoryManagement: Invalid inMemTime in " + config);
                                System.exit(-1);
                            }
                            lastTouchTime_ = Common.stringToInt(st.nextToken());
                            if (lastTouchTime_ < 0)
                            {
                                System.out.println("MemoryManagement: Invalid lastTouchTime_ in " + config);
                                System.exit(-1);
                            }
                            Page page = pageMemList_.get(id);
                            page.set(physical, R, M, inMemTime, lastTouchTime_);
                            pageMemList_.set(id, page);
                        }
                    }
                    if (line.startsWith("pagesize"))
                    {
                        StringTokenizer st = new StringTokenizer(line);
                        while (st.hasMoreTokens())
                        {
                            tmp = st.nextToken();
                            tmp = st.nextToken();
                            if ( tmp.startsWith( "power" ) )
                            {
                                power = (double) Integer.parseInt(st.nextToken());
                                block_ = (int) Math.pow(2,power);
                            }
                            else
                            {
                                block_ = Long.parseLong(tmp,10);
                            }
                            address_limit = (block_ * virtPageNum_+1)-1;
                        }
                        if ( block_ < 64 || block_ > Math.pow(2,26))
                        {
                            System.out.println("MemoryManagement: pagesize is out of bounds");
                            System.exit(-1);
                        }
                        for (i = 0; i <= virtPageNum_; i++)
                        {
                            Page page = pageMemList_.get(i);
                            page.setBounds((block_*(i + 1))-1, block_*i);
                            pageMemList_.set(i, page);
                        }
                    }
                }
                in.close();
            }
            catch (IOException e)
            {
                /* Handle exceptions */
            }
        }
        f = new File ( commands );
        try
        {
            BufferedReader in = new BufferedReader(new FileReader(f));
            while ((line = in.readLine()) != null)
            {
                if (line.startsWith(Constants.READ_COMMAND) || line.startsWith(Constants.WRITE_COMMAND))
                {
                    if (line.startsWith(Constants.READ_COMMAND))
                    {
                        command = Constants.READ_COMMAND;
                    }
                    if (line.startsWith(Constants.WRITE_COMMAND))
                    {
                        command = Constants.WRITE_COMMAND;
                    }
                    StringTokenizer st = new StringTokenizer(line);
                    tmp = st.nextToken();
                    tmp = st.nextToken();
                    if (tmp.startsWith("random"))
                    {
                        instructionList_.add(new Instruction(command,Common.generateRandomLong( address_limit )));
                    }
                    else
                    {
                        if ( tmp.startsWith( "bin" ) )
                        {
                            addr = Long.parseLong(st.nextToken(),2);
                        }
                        else if ( tmp.startsWith( "oct" ) )
                        {
                            addr = Long.parseLong(st.nextToken(),8);
                        }
                        else if ( tmp.startsWith( "hex" ) )
                        {
                            addr = Long.parseLong(st.nextToken(),16);
                        }
                        else
                        {
                            addr = Long.parseLong(tmp);
                        }
                        if (0 > addr || addr > address_limit)
                        {
                            System.out.println("MemoryManagement: " + addr + ", Address out of range in " + commands);
                            System.exit(-1);
                        }
                        instructionList_.add(new Instruction(command,addr));
                    }
                }
            }
            in.close();
        }
        catch (IOException e)
        {
            /* Handle exceptions */
        }
        if (instructionList_.isEmpty())
        {
            System.err.println("MemoryManagement: no instructions present for execution.");
            System.exit(-1);
        }
        runs_ = 0;
        for (i = 0; i < virtPageNum_; i++)
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
            for (i = 0; i < virtPageNum_; i++)
            {
                Page page = pageMemList_.get(i);
                if (!page.isValidPhysicalAddress() && map_count < ( virtPageNum_ + 1 ) / 2 )
                {
                    page.setPhysicalAddress(i);
                    pageMemList_.set(i, page);
                    map_count++;
                }
            }
        }
        for (i = 0; i < virtPageNum_; i++)
        {
            Page page = pageMemList_.get(i);
            if (!page.isValidPhysicalAddress())
            {
                controlPanel_.removePageAt(i);
            }
            else
            {
                controlPanel_.addPage(i, page);
            }
        }
        for (i = 0; i < instructionList_.size(); i++)
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
    private ArrayList<Page> pageMemList_;
    
    private int runs_;
    // The number of virtual pages must be fixed at 63 due to
    // dependencies in the GUI
    private int virtPageNum_ = 0;
}
