package com.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.Controller;
import com.algo.FaultAlgo;

public class CommandPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	public CommandPanel(Controller kernel)
	{
		kernel_ = kernel;
		setLayout(new GridLayout(1, 0));
    	initRunButton();
    	initStepButton();
    	initResetButton();
    	initExitButton();
    	initFaultAlgoSelection();
	}
	
	private void initFaultAlgoSelection()
	{
    	faultAlgoSelect_ = new JComboBox<>();
    	faultAlgoSelect_.setModel(new DefaultComboBoxModel<>(FaultAlgo.values()));
    	faultAlgoSelect_.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e)
			{
				// TODO
			}
		});
    	add(faultAlgoSelect_);
	}

	private void initExitButton()
    {
    	exitButton_ = new CommandButton("exit");
    	exitButton_.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
    	add(exitButton_);
    }

    private void initResetButton()
    {
    	resetButton_ = new CommandButton("reset");
    	resetButton_.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e)
			{
				kernel_.reset();
	            runButton_.setEnabled(true);
	            stepButton_.setEnabled(true);
			}
		});
    	add(resetButton_);
    }

    private void initRunButton()
    {
    	runButton_ = new CommandButton("run");
        runButton_.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e)
			{
				runButton_.setEnabled(false);
	            stepButton_.setEnabled(false);
	            resetButton_.setEnabled(false);
	            kernel_.start();
	            resetButton_.setEnabled(true);
			}
		});
        add(runButton_);
    }
    
    private void initStepButton()
    {
    	stepButton_ = new CommandButton("step");
    	stepButton_.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e)
			{
	            kernel_.step();
	            if (kernel_.isRunCycleDone())
	            {
	                stepButton_.setEnabled(false);
	                runButton_.setEnabled(false);
	            }
			}
		});
    	add(stepButton_);
    }
    
    private JComboBox<FaultAlgo> faultAlgoSelect_;
    private CommandButton exitButton_;
    private Controller kernel_;
    private CommandButton resetButton_;
    private CommandButton runButton_;
    private CommandButton stepButton_;
}
