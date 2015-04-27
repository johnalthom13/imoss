package com.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.Controller;
import com.algo.FaultAlgo;
import com.algo.FaultAlgorithmFactory;
import com.data.Constants;

public class CommandPanel extends JPanel
{
    private static final long serialVersionUID = 1L;

    public CommandPanel()
    {
    	algoFactory_ = new FaultAlgorithmFactory();
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
        faultAlgoSelect_.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            	listener_.propertyChange(new PropertyChangeEvent(this, Constants.ALGORITHM_PROPERTY, null, 
            			algoFactory_.fetch((FaultAlgo) faultAlgoSelect_.getSelectedItem()) ));
            }
        });
        add(faultAlgoSelect_);
    }

    private void initExitButton()
    {
        exitButton_ = new CommandButton("exit");
        exitButton_.addActionListener(new ActionListener()
        {

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
        resetButton_.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
            	listener_.propertyChange(new PropertyChangeEvent(this, Constants.RESET_PROPERTY, null, null));
                runButton_.setEnabled(true);
                stepButton_.setEnabled(true);
                faultAlgoSelect_.setEnabled(true);
            }
        });
        add(resetButton_);
    }

    private void initRunButton()
    {
        runButton_ = new CommandButton("run");
        runButton_.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                runButton_.setEnabled(false);
                stepButton_.setEnabled(false);
                resetButton_.setEnabled(false);
                faultAlgoSelect_.setEnabled(false);
            	listener_.propertyChange(new PropertyChangeEvent(this, Constants.START_PROPERTY, null, null));
                resetButton_.setEnabled(true);
            }
        });
        add(runButton_);
    }

    private void initStepButton()
    {
        stepButton_ = new CommandButton("step");
        stepButton_.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
            	listener_.step();
                if (listener_.isRunCycleDone())
                {
                    stepButton_.setEnabled(false);
                    runButton_.setEnabled(false);
                }
            }
        });
        add(stepButton_);
    }

    public void setController(Controller controller)
    {
    	listener_ = controller;
    }
    
    private FaultAlgorithmFactory algoFactory_;
    private JComboBox<FaultAlgo> faultAlgoSelect_;
    private Controller listener_;
    private CommandButton exitButton_;
    private CommandButton resetButton_;
    private CommandButton runButton_;
    private CommandButton stepButton_;
}
