package com.ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PageButton extends JPanel
{
    private static final long serialVersionUID = 1L;

    public PageButton(int id)
    {
        pageId_ = id;
        pageButton_ = new JButton(" VP " + pageId_);
        pageButton_.setName("" + pageId_);
        pageButton_.setForeground( Color.magenta );
        pageButton_.setBackground( Color.lightGray );
        pageLabel_ = new JLabel();

        setLayout(new GridLayout(1, 0, 0, 0));
        add(pageButton_);
        add(pageLabel_);
    }

    public int getId()
    {
        return pageId_;
    }

    public void addActionListener(ActionListener actionListener)
    {
        pageButton_.addActionListener(actionListener);
    }

    public void setText(String label)
    {
        pageLabel_.setText(label);
    }


    private int pageId_;
    private JButton pageButton_;
    private JLabel pageLabel_;


}
