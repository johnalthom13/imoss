package com.algo;

public enum FaultAlgo
{
    FIFO("FIFO"),
    ROUND_ROBIN("Round Robin"),
    LRU("LRU");

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
