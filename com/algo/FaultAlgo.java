package com.algo;

public enum FaultAlgo
{
    FIFO("FIFO"),
    RANDOM("Random"),
    LRU("LRU"),
    NRU("NRU"),
    ROUND_ROBIN("Round Robin");

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
