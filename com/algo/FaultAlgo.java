package com.algo;

public enum FaultAlgo
{
    FIFO("First In, First Out"),
    LRU("Least Recently Used"),
    MRU("Most Recently Used"),
    NRU("Not Recently Used"),
    OPT("Optimal"),
    RANDOM("Random"),
    ROUND_ROBIN("Round Robin"),
    SECOND_CHANCE("Second Chance"),
    SECTIONAL("Sectional Selection"),
    UNNAMED_ALGO1("X Algo 1");

    private FaultAlgo(String name)
    {
        name_ = name;
    }

    @Override
	public String toString()
    {
        return name_;
    }

    private String name_;
}
