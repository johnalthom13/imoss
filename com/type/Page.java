package com.type;

import com.data.Constants;

public class Page
{
    public Page( int id, int physical, byte R, byte M, int inMemTime, int lastTouchTime, long high, long low )
    {
        id_ = id;
        set(physical, R, M, inMemTime, lastTouchTime);
        setBounds(high, low);
    }

    public Page(int virtualPageNum, int physicalPageNum, byte readFrom, byte modified, int inMemTime, int lastTouchTime)
    {
        id_ = virtualPageNum;
        set(physicalPageNum, readFrom, modified, inMemTime, lastTouchTime);
        setBounds(-1, -1);
    }

    public void addInMemoryTime(int timeAddition)
    {
        inMemTime_ += timeAddition;
    }

    public String getHighAddress()
    {
        return Long.toString(addressHigh_ , Constants.ADDRESS_RADIX);
    }

    public int getId()
    {
        return id_;
    }

    public int getInMemoryTime()
    {
        return inMemTime_;
    }

    public String getLowAddress()
    {
        return Long.toString(addressLow_ , Constants.ADDRESS_RADIX);
    }

    public int getPhysicalPage()
    {
        return physical_;
    }

    public boolean isSamePhysicalAddressWith(Page page)
    {
        return (physical_ == page.physical_);
    }

    public boolean isValidPhysicalAddress()
    {
        return (physical_ != -1);
    }

    public void set(int physical, byte r, byte m, int inMemTime, int lastTouchTime)
    {
        physical_ = physical;
        R_ = r;
        M_ = m;
        inMemTime_ = inMemTime;
        lastTouchTime_ = lastTouchTime;
    }

    public void setBounds(long high, long low)
    {
        this.addressHigh_ = high;
        this.addressLow_ = low;
    }

    public void setPhysicalAddress(int phy)
    {
        physical_ = phy;
    }

    public void validate(long virtualPage)
    {
        String reason = "";
        if (id_ < 0)
        {
            reason += "id < 0\n";
            reason += id_ + " < 0\n";
        }
        if (id_ > virtualPage)
        {
            reason += "id > virtPageNum_\n";
            reason += id_ + " > " + virtualPage + "\n";
        }
        if (-1 > physical_)
        {
            reason += "-1 > physical\n";
            reason += "-1 > " + physical_ + "\n";
        }
        if (physical_ > ((virtualPage - 1) / 2))
        {
            reason += "physical > ((virtPageNum_ - 1) / 2)\n";
            reason += physical_ + " > " + ((virtualPage - 1) / 2) + "\n";
        }
        if (!reason.isEmpty())
        {
            System.err.println("Configuration Problem: Invalid page value " + "\nReason(s): " + reason);
            System.exit(-1);
        }
        if (R_ < 0 || R_ > 1)
        {
            System.err.println("MemoryManagement: Invalid R value");
            System.exit(-1);
        }
        if (M_ < 0 || M_ > 1)
        {
            System.err.println("MemoryManagement: Invalid M value");
            System.exit(-1);
        }
        if (inMemTime_ < 0)
        {
            System.err.println("MemoryManagement: Invalid inMemTime");
            System.exit(-1);
        }
        if (lastTouchTime_ < 0)
        {
            System.err.println("MemoryManagement: Invalid lastTouchTime");
            System.exit(-1);
        }
    }
    
    public String toString()
    {
    	return "Page " + id_;
    }

    // TODO FIX ME
    public int lastTouchTime_;
    public byte M_;
    public byte R_;

    private long addressHigh_;
    private long addressLow_;
    private int id_;
    private int inMemTime_;
    private int physical_;
}
