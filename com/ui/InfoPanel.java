package com.ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import com.type.Instruction;
import com.type.Page;

public class InfoPanel extends JPanel
{
    private static final long serialVersionUID = 1L;

    public InfoPanel()
    {
        pageFaultCount_ = 0;
        pageFaultCost_ = 0;
        initComponents();
        addComponents();
    }

    public void reset(Page page)
    {
        virtualPageValueLabel_.setValue(page.getId());
        physicalPageValueLabel_.setValue(page.getPhysicalPage());

        rValueLabel_.setValue(page.isReferenced());
        mValueLabel_.setValue(page.isModified());
        inMemTimeLabel_.setValue(page.getInMemoryTime());
        lastTouchTimeValueLabel_.setValue(page.getLastTouchTime());
        lowValueLabel_.setValue(page.getHighAddress());
        highValueLabel_.setValue(page.getHighAddress());
    }

    public void setInstruction(Instruction instruct)
    {
        instructionLabel_.setValue(instruct.getInstructionCode());
        addressLabel_.setValue(instruct.getAddressStr());
    }

    public void setPageFaultPresent(boolean isPresent)
    {
        pageFaultPresentLabel_.setValue(isPresent ? "YES" : "NO");
        if (isPresent)
        {
            pageFaultCount_++;
            pageFaultCost_++; // TODO Fix for dirty page
            pageFaultCountLabel_.setValue(pageFaultCount_);
            pageFaultCostLabel_.setValue(pageFaultCost_);
        }
    }

    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(400, 100);
    }

    public void setTime(int time)
    {
        timeValueLabel_.setValue(time + " (ns)");
    }

    private void addComponents()
    {
        add(timeValueLabel_);
        add(new JSeparator());

        add(instructionLabel_);
        add(addressLabel_);
        add(new JSeparator());

        add(pageFaultPresentLabel_);
        add(pageFaultCountLabel_);
        add(pageFaultCostLabel_);
        add(new JSeparator());

        add(virtualPageValueLabel_);
        add(physicalPageValueLabel_);
        add(rValueLabel_);
        add(mValueLabel_);
        add(inMemTimeLabel_);
        add(lastTouchTimeValueLabel_);
        add(lowValueLabel_);
        add(highValueLabel_);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    private void initComponents()
    {
        timeValueLabel_ = new InfoLabel("Time");

        instructionLabel_ = new InfoLabel("Instruction", "NONE");
        addressLabel_ = new InfoLabel("Address", "NULL");

        pageFaultPresentLabel_ = new InfoLabel("Page Fault", "NO");
        pageFaultCountLabel_ = new InfoLabel("Page Fault Count");
        pageFaultCostLabel_ = new InfoLabel("Page Fault Cost");

        virtualPageValueLabel_ = new InfoLabel("Virtual Page", "x");
        physicalPageValueLabel_ = new InfoLabel("Physical Page");
        rValueLabel_ = new InfoLabel("R");
        mValueLabel_ = new InfoLabel("M");
        inMemTimeLabel_ = new InfoLabel("In-Memory Time");
        lastTouchTimeValueLabel_ = new InfoLabel("Last Touch Time");
        lowValueLabel_ = new InfoLabel("Low Value");
        highValueLabel_ = new InfoLabel("High Value");
    }

    private int pageFaultCount_;
    private int pageFaultCost_;
    private InfoLabel addressLabel_;

    private InfoLabel highValueLabel_;
    private InfoLabel inMemTimeLabel_;

    private InfoLabel instructionLabel_;

    private InfoLabel lastTouchTimeValueLabel_;
    private InfoLabel lowValueLabel_;
    private InfoLabel mValueLabel_;
    
    private InfoLabel pageFaultPresentLabel_;
    private InfoLabel pageFaultCountLabel_;
    private InfoLabel pageFaultCostLabel_;
    
    private InfoLabel physicalPageValueLabel_;
    private InfoLabel rValueLabel_;
    private InfoLabel timeValueLabel_;
    private InfoLabel virtualPageValueLabel_;
}
