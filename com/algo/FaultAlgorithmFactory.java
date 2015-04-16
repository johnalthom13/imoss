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
			case FIFO : return new FIFO(controlPanel_);
			default: return null;
		}
	}
	
	private ControlPanel controlPanel_;
}
