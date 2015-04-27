package com.algo;


public class FaultAlgorithmFactory
{
    public AbstractFaultAlgorithm fetch(FaultAlgo algo)
    {
        switch (algo)
        {
        case FIFO :
            return new FIFO();
        case ROUND_ROBIN :
            return new RoundRobin();
        case RANDOM :
            return new Random();
        case LRU :
            return new LRU();
        case NRU :
            return new NRU();
        default:
            return null;
        }
    }
}
