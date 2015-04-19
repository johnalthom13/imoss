package com.ui;

import java.awt.Color;

import javax.swing.JButton;

public class CommandButton extends JButton
{
    private static final long serialVersionUID = 1L;
    public CommandButton(String label)
    {
        super(label);
        setForeground( Color.BLUE );
        setBackground( Color.LIGHT_GRAY );
    }
}
