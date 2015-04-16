package com;

public class Page
{
    public Page( int id, int physical, byte R, byte M, int inMemTime, int lastTouchTime, long high, long low )
    {
        id_ = id;
        set(physical, R, M, inMemTime, lastTouchTime);
        setBounds(high, low);
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
