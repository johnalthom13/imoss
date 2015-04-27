package com;

import com.data.ConfigData;

public class Virtual2Physical
{
    public static int getPageNumberFromAddress(long memaddr)
    {
        int i = 0;
        long high = 0;
        long low = 0;
        for (i = 0; i <= ConfigData.VIRTUAL_PAGE_COUNT; ++i)
        {
            low = ConfigData.BLOCK_SIZE*i;
            high = ConfigData.BLOCK_SIZE*(i + 1);
            if (low <= memaddr && memaddr < high)
            {
                return i;
            }
        }
        return -1;
    }
}
