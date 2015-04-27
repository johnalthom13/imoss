package com.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.Controller;
import com.data.Constants;
import com.type.Instruction;
import com.type.Page;

public class ControlPanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    
    public ControlPanel()
    {
        commandPanel_ = new CommandPanel();
        infoPanel_ = new InfoPanel();
        setPagePanel();
        setLayout();
    }

    public void setController(final Controller controller)
    {
    	commandPanel_.setController(controller);
    	addListenerToButtons(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event)
			{
				JButton evtSrc = (JButton) event.getSource();
				int id = Integer.parseInt(evtSrc.getName());
				if (id < Constants.MAX_PAGE_COUNT)
					controller.getPage(id);
			}
		});
    }
    
    public void addPage(int pageNum, Page physicalPage)
    {
    	addPageAt(pageNum, physicalPage.getPhysicalPage());
    }

    public void addPageAt(int pageNum, int physicalPage)
    {
    	initializePageButtons();
        pageButtons_[physicalPage].setText("page " + pageNum);
    }

    public void removePage(Page physicalPage)
    {
    	removePageAt(physicalPage.getPhysicalPage());
    }

    public void removePageAt(int loc)
    {
        pageButtons_[loc].setText("");
    }

    public void reset()
    {
    	removeAll();
    	infoPanel_ = new InfoPanel();
        setPagePanel();
        setLayout();
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

        repaint();
        revalidate();
    }
    
    private void initializePageButtons()
    {
        if (pageButtons_ == null)
        {
            pageButtons_ = new PageButton[(int) Constants.MAX_PAGE_COUNT];
        }
    }
    
    private void setPagePanel()
    {
        GridLayout layout = new GridLayout(0, Constants.COLUMN_VIEW, 0, 0);
        pagePanel_ = new JPanel(layout);
        initializePageButtons();
        for (int i = 0; i < Constants.MAX_PAGE_COUNT; ++i)
        {
            final int id = i;
            pageButtons_[id] = new PageButton(id);
            pageButtons_[id].repaint();
            pageButtons_[id].revalidate();
            pagePanel_.add(pageButtons_[id]);
            
        }
    }
    
    private void addListenerToButtons(ActionListener listener)
    {
        for (int i = 0; i < Constants.MAX_PAGE_COUNT; ++i)
        {
            final int id = i;
            pageButtons_[id].addActionListener(listener);
        }
    }

    private CommandPanel commandPanel_;
    private InfoPanel infoPanel_;
    private PageButton[] pageButtons_;
    private JPanel pagePanel_;

}
