package com.type;

import com.data.Constants;

public class Page
{
    public Page(int id, int physical, int inMemTime, int lastTouchTime, long high, long low)
    {
        id_ = id;
        set(physical, false, false, inMemTime, lastTouchTime);
        setBounds(high, low);
    }

    public Page(int virtualPageNum, int physicalPageNum, boolean isReferenced, boolean isModified, int inMemTime, int lastTouchTime)
    {
        id_ = virtualPageNum;
        set(physicalPageNum, isReferenced, isModified, inMemTime, lastTouchTime);
        setBounds(-1, -1);
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

    public void set(int physical, boolean isReferenced, boolean isModified, int inMemTime, int lastTouchTime)
    {
        physical_ = physical;
        isReferenced_ = isReferenced;
        isModified_ = isModified;
        inMemTime_ = inMemTime;
        lastTouchTime_ = lastTouchTime;
    }
    
    public void reset()
    {
    	set(-1, false, false, 0, 0);
    }
    
    public void resetTimers()
    {
    	inMemTime_ = 0;
    	lastTouchTime_ = 0;
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
    	return "Page " + id_ + " RO = " + isReadOnly_;
    }

    public PageClass getPageClass()
    {
    	return PageClass.compute(isReferenced(), isModified());
    }
    
    private int lastTouchTime_;
    private boolean isModified_;
    private boolean isReferenced_;

    private long addressHigh_;
    private long addressLow_;
    private int id_;
    private int inMemTime_;
    private int physical_;
    
    private boolean isReadOnly_ = false;
    
	public void setAsReferenced()
	{
		lastTouchTime_ = 0;
		isReferenced_ = true;
	}
	
	public void clearReferencedBit()
	{
		isReferenced_ = false;
	}

	public void setAsModified()
	{
		lastTouchTime_ = 0;
		isModified_ = true;
	}

	public void refreshTimers()
	{
		final int TIME_UNIT = 10; // 10 nanoseconds
        if (isReferenced_ && lastTouchTime_ == TIME_UNIT) 
        {
        	isReferenced_ = false;
        }
        if (isValidPhysicalAddress())
        {
            inMemTime_ += TIME_UNIT;
            lastTouchTime_ = lastTouchTime_ + TIME_UNIT;
        }
	}

	public int getLastTouchTime()
	{
		return lastTouchTime_;
	}

	public boolean isReferenced()
	{
		return isReferenced_;
	}

	public boolean isModified()
	{
		return isModified_;
	}
	
	public void setAsReadOnly()
	{
		isReadOnly_ = true;
	}
	
	public boolean isReadOnly()
	{
		return isReadOnly_;
	}
}
