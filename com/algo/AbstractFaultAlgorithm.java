package com.algo;

import java.util.ArrayList;

import com.Page;
import com.ui.ControlPanel;

public abstract class AbstractFaultAlgorithm
{
	protected AbstractFaultAlgorithm(ControlPanel controlPanel)
	{
		controlPanel_ = controlPanel;
	}
	
	public abstract void replacePage(ArrayList<Page> mem, int virtPageNum , int replacePageNum);
	
	protected ControlPanel controlPanel_;
}
