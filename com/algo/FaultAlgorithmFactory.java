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
        case LRU :
            return new LRU(controlPanel_);
        default:
            return null;
        }
    }

    private ControlPanel controlPanel_;
}
