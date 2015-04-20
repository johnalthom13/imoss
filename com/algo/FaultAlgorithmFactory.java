package com.algo;

import com.ui.ControlPanel;

public class FaultAlgorithmFactory
{

    public FaultAlgorithmFactory(ControlPanel controlPanel)
    {
        controlPanel_ = controlPanel;
    }

    public AbstractFaultAlgorithm fetch(FaultAlgo algo)
    {
        switch (algo)
        {
        case FIFO :
            return new FIFO(controlPanel_);
        case ROUND_ROBIN :
            return new RoundRobin(controlPanel_);
        case RANDOM :
            return new Random(controlPanel_);
        case LRU :
            return new LRU(controlPanel_);
        case NRU :
            return new NRU(controlPanel_);
        default:
            return null;
        }
    }

    private ControlPanel controlPanel_;
}
