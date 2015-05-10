package com.ui;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class InfoLabel extends JPanel
{
    private final static int A_PANEL_HEIGHT = 15;
    private final static Dimension LABEL_SIZE = new Dimension(150, A_PANEL_HEIGHT);
    private final static Dimension VALUE_SIZE = new Dimension(200, A_PANEL_HEIGHT);

    /// Value is assumed zero
    public InfoLabel(String label)
    {
        label_ = new JLabel(label + ":");
        label_.setPreferredSize(LABEL_SIZE);
        value_ = new JLabel("0");
        value_.setPreferredSize(VALUE_SIZE);

        add(label_);
        add(value_);
    }

    public InfoLabel(String label, String value)
    {
        label_ = new JLabel(label + ":");
        label_.setPreferredSize(LABEL_SIZE);
        value_ = new JLabel(value);
        value_.setPreferredSize(VALUE_SIZE);

        add(label_);
        add(value_);
    }

    public InfoLabel()
    {
        label_ = new JLabel("");
        label_.setPreferredSize(LABEL_SIZE);
        value_ = new JLabel("");
        value_.setPreferredSize(VALUE_SIZE);

        add(label_);
        add(value_);
	}

	public void setValue(int value)
    {
		String text = "" + value;
		if (isAddress_)
		{
	        text = "0x" + text;
		}
        value_.setText(text);
    }

    public void setValue(boolean value)
    {
        value_.setText(value ? "TRUE" : "FALSE");
    }

    public void setValue(String value)
    {
		if (isAddress_ && value.indexOf("0x") < 0)
		{
			value = "0x" + value;
		}
        value_.setText(value);
    }
    
    public String getValue()
    {
    	return label_.getText();
    }
    
    public void setAsAddress()
    {
    	isAddress_ = true;
    }

    private JLabel label_;
    private JLabel value_;
    private boolean isAddress_;
}


