package com.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.Common;
import com.type.Page;
import com.type.PageList;

@Deprecated
public class ConfReader
{
	public static String FILENAME = "";
    private ConfReader(String configFile)
    {
        configFile_ = new File(configFile);
    }

    public static ConfReader create()
    {
        instance_ = new ConfReader(FILENAME);
        return instance_;
    }

    public static ConfReader getInstance()
    {
        if (instance_ == null)
        {
            instance_ = new ConfReader("memory.conf"); // By default
        }
        return instance_;
    }

    public ConfigData read()
    {
        return null;

    }

    private String fetchPropertyLine(String propertyName)
    {
        try
        {
            String line = null;
            BufferedReader in = new BufferedReader(new FileReader(configFile_));
            while ((line = in.readLine()) != null)
            {
                if (line.startsWith(propertyName))
                {
                    in.close();
                    return line;
                }
            }
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public long fetchVirtualPageNumber()
    {
        String[] tokens = fetchPropertyLine("numpages").split(" ");
        long virtualPageNum = Common.stringToInt(tokens[1])-1;
        if (virtualPageNum < Constants.MIN_PAGE_COUNT)
        {
            System.err.println("MemoryManagement: numpages below bounds");
            System.exit(-1);
        }
        else if (virtualPageNum >= Constants.MAX_PAGE_COUNT)
        {
            System.err.println("MemoryManagement: numpages above bounds.");
            System.exit(-1);
        }
        return virtualPageNum;
    }

    // key - page id, value - page object
    public PageList fetchMemorySetting(PageList pageList, long virtPageNum)
    {
        try
        {
            String line = null;
            BufferedReader in = new BufferedReader(new FileReader(configFile_));
            while ((line = in.readLine()) != null)
            {
                if (line.startsWith("memset"))
                {
                    String[] tokens = line.split(" ");
                    int id = Common.stringToInt(tokens[1]);
                    int physicalPageNum = -1;
                    if (!tokens[2].startsWith("x"))
                    {
                        physicalPageNum = Common.stringToInt(tokens[2]);
                    }

                    boolean readFrom = Common.stringToBoolean(tokens[3]);
                    boolean modified = Common.stringToBoolean(tokens[4]);
                    int inMemTime = Common.stringToInt(tokens[5]);
                    int lastTouchTime = Common.stringToInt(tokens[5]);

                    Page page = pageList.get(id);
                    page.set(physicalPageNum, readFrom, modified, inMemTime, lastTouchTime);
                    page.validate(virtPageNum);
                    pageList.set(id, page);
                }
            }
            in.close();
            return pageList;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return pageList;
    }

    public long fetchPageSize()
    {
        String[] tokens = fetchPropertyLine("pagesize").split(" ");
        long pageSize = 0;
        if (tokens.length == 3 && tokens[1].equals("power"))
        {
            double power = Double.parseDouble(tokens[2]);
            pageSize = (long) Math.pow(2, power);
        }
        else
        {
            pageSize = Long.parseLong(tokens[1]);
        }
        if (pageSize < Constants.MIN_PAGESIZE)
        {
            System.err.println("MemoryManagement: pageSize < Constants.MIN_PAGESIZE");
            System.exit(-1);
        }
        if (pageSize > Constants.MAX_PAGESIZE)
        {
            System.err.println("MemoryManagement: pageSize > Constants.MAX_PAGESIZE");
            System.exit(-1);
        }

        return pageSize;
    }

    private File configFile_;
    private static ConfReader instance_;
}
