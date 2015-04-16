package com.algo;

public enum FaultAlgo
{
	FIFO("FIFO");
	
	private FaultAlgo(String name)
	{
		name_ = name;
	}
	
	public String toString()
	{
		return name_;
	}
	
	private String name_;
}
