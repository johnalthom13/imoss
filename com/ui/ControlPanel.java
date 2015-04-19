package com.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import com.Controller;
import com.algo.FaultAlgo;
import com.algo.FaultAlgorithmFactory;
import com.data.Constants;
import com.type.Instruction;
import com.type.Page;

public class ControlPanel extends JPanel
{
    private static final long serialVersionUID = 1L;

    public ControlPanel(String commands, String config)
    {
        kernel_ = new Controller();
        kernel_.setControlPanel(this);

        commandPanel_ = new CommandPanel(kernel_);
        infoPanel_ = new InfoPanel();
        setPagePanel();
        setLayout();
        kernel_.init(new FaultAlgorithmFactory(this).fetch(FaultAlgo.LRU), commands, config);
    }

    public void addPage(int pageNum, Page physicalPage)
    {
        pageButtons_[physicalPage.getPhysicalPage()].setText("page " + pageNum);
    }

    public void addPageAt(int pageNum, int physicalPage)
    {
        pageButtons_[physicalPage].setText("page " + pageNum);
    }

    public void removePage(Page physicalPage)
    {
        pageButtons_[physicalPage.getPhysicalPage()].setText("");
    }

    public void removePageAt(int loc)
    {
        pageButtons_[loc].setText("");
    }

    public void reset()
    {
        infoPanel_ = new InfoPanel();
    }

    public void reset(Page page)
    {
        infoPanel_.reset(page);
    }

    public void setInstruction(Instruction instruct)
    {
        infoPanel_.setInstruction(instruct);
    }

    public void setPageFaultPresent(boolean isPresent)
    {
        infoPanel_.setPageFaultPresent(isPresent);
    }

    // in nano-seconds
    public void setTime(int time)
    {
        infoPanel_.setTime(time);
    }

    private void setLayout()
    {
        setLayout(new BorderLayout());
        add(commandPanel_, BorderLayout.NORTH);
        add(pagePanel_, BorderLayout.WEST);
        add(infoPanel_, BorderLayout.EAST);
    }

    private void setPagePanel()
    {
        GridLayout layout = new GridLayout(0, Constants.COLUMN_VIEW, 0, 0);
        pagePanel_ = new JPanel(layout);
        pageButtons_ = new PageButton[Constants.MAX_PAGE_COUNT];
        for (int i = 0; i < Constants.MAX_PAGE_COUNT; ++i)
        {
            final int id = i;
            pageButtons_[id] = new PageButton(id);
            pageButtons_[id].addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent arg0)
                {
                    kernel_.getPage(id);
                }
            });

            pagePanel_.add(pageButtons_[id]);
        }
    }

    private CommandPanel commandPanel_;
    private InfoPanel infoPanel_;
    private Controller kernel_;
    private PageButton[] pageButtons_;
    private JPanel pagePanel_;

}
