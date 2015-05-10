package com;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.data.CommandReader;
import com.data.ConfigData;
import com.data.Constants;
import com.ui.ControlPanel;

@SuppressWarnings("serial")
public class InitSettingsDialog extends JDialog
{
	public static void display()
	{
		new InitSettingsDialog();
	}
	
	private InitSettingsDialog()
	{
		setLayout(new GridLayout(0, 1));
		add(createPageSizeSelectionPanel());
		add(createPageCountSelectionPanel());
		add(createReadOnlyBoundsPanel());
		prepareSubmitButton();
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
	}

	private JPanel createPageSizeSelectionPanel()
	{
		JPanel panel = new JPanel(new BorderLayout());
		Vector<Integer> sizeValues = new Vector<>();
		// TODO Fix magic number
		for (int i = Constants.MIN_PAGESIZE_POWER; i < Constants.MAX_PAGESIZE_POWER; ++i)
		{
			sizeValues.add((int) Math.pow(2, i));
		}
		DefaultComboBoxModel<Integer> sizeModel = new DefaultComboBoxModel<>(sizeValues);
		pageSizeSelector_ = new JComboBox<>(sizeModel);
		JLabel label = new JLabel("Select page size: ");
		
		panel.add(pageSizeSelector_, BorderLayout.EAST);
		panel.add(label, BorderLayout.WEST);
		return panel;
	}
	
	private JPanel createPageCountSelectionPanel()
	{
		JPanel panel = new JPanel(new BorderLayout());
		Vector<Integer> countValues = new Vector<>();
		// TODO Fix magic number
		for (int i = Constants.MAX_PAGECOUNT_POWER; i > Constants.MIN_PAGECOUNT_POWER; i--)
		{
			countValues.add((int) Math.pow(2, i));
		}
		DefaultComboBoxModel<Integer> countModel = new DefaultComboBoxModel<>(countValues);
		pageCountSelector_ = new JComboBox<>(countModel);
		JLabel label = new JLabel("Select page count: ");
		
		panel.add(pageCountSelector_, BorderLayout.EAST);
		panel.add(label, BorderLayout.WEST);
		return panel;
	}
	
	private JPanel createReadOnlyBoundsPanel()
	{
		JPanel panel = new JPanel(new BorderLayout());

		lowerReadOnlyPage_ = new JTextField("0");
		upperReadOnlyPage_ = new JTextField("10");
		JPanel textboxPanel = new JPanel(new GridLayout(1, 0));
		textboxPanel.add(lowerReadOnlyPage_);
		textboxPanel.add(new JLabel("to"));
		textboxPanel.add(upperReadOnlyPage_);
		
		JLabel label = new JLabel("Read-only page(s): ");
		panel.add(textboxPanel, BorderLayout.EAST);
		panel.add(label, BorderLayout.WEST);
		return panel;
	}
	
	private void prepareSubmitButton()
	{
		submitButton_ = new JButton("Submit");
		submitButton_.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				ConfigData.BLOCK_SIZE = (int) pageSizeSelector_.getSelectedItem();
				ConfigData.VIRTUAL_PAGE_COUNT = (int) pageCountSelector_.getSelectedItem();
				
				int lower = Integer.parseInt(lowerReadOnlyPage_.getText());				
				int upper = Integer.parseInt(upperReadOnlyPage_.getText());

				ConfigData.UPPER_RO_PAGE = Math.max(upper, lower);
				ConfigData.LOWER_RO_PAGE = Math.min(upper, lower);
				
				dispose();
				int addressLimit = (ConfigData.BLOCK_SIZE * ConfigData.VIRTUAL_PAGE_COUNT+1)-1;
				ConfigData.INSTRUCTIONS_LIST = CommandReader.readCommands(addressLimit);
		        if (ConfigData.INSTRUCTIONS_LIST.isEmpty())
		        {
		            System.err.println("MemoryManagement: no instructions present for execution.");
		            System.exit(-1);
		        }
		        JFrame x = new JFrame();
		        ControlPanel ctrlPanel = new ControlPanel();
		        ctrlPanel.setController(new Controller(ctrlPanel));
		        x.add(ctrlPanel);
		        x.pack();
		        x.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        x.setVisible(true);
			}
		});
		add(submitButton_);
	}

	private JTextField lowerReadOnlyPage_;
	private JTextField upperReadOnlyPage_;
	private JComboBox<Integer> pageSizeSelector_;
	private JComboBox<Integer> pageCountSelector_;
	private JButton submitButton_;
}
