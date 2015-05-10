package com;

public class Instruction
{
	public Instruction( String inst, long addr )
    {
        this.instructionCode_ = inst;
        this.address_ = addr;
    }

    public long getAddress()
    {
    	return address_;
    }
    
    public String getAddressStr()
    {
    	return Long.toString(address_, Constants.ADDRESS_RADIX);
    }
    
    public String getInstructionCode()
    {
    	return instructionCode_;
    }
    
    public boolean isRead()
	{
		return instructionCode_.startsWith(Constants.READ_COMMAND);
	}

    public boolean isWrite()
	{
		return instructionCode_.startsWith(Constants.WRITE_COMMAND);
	}
    
    public String toString()
    {
    	return instructionCode_ + " " + getAddressStr();
    }

    private long address_;
    private String instructionCode_;
	

}
