package com.algo;


public class FaultAlgorithmFactory
{
    public static AbstractFaultAlgorithm fetch(FaultAlgo algo)
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
        case SECOND_CHANCE :
            return new SecondChance();
        case UNNAMED_ALGO1:
        	return new UnnamedAlgo1();
		case SECTIONAL:
			return new SectionalSelect();
		case OPT:
			return new Optimal();
		default:
			return null;
        }
    }
}
