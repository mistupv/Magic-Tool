package magicTool.presentation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import magicTool.logic.Logic;
import magicTool.logic.Slicer;

public class ConfigPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	private final Logic logic = Logic.getLogic();

	public ConfigPanel()
	{
		this.initComponents();
		this.addComponents();
	}
	private void initComponents()
	{
		this.setLayout(new GridBagLayout());
	}
	private void addComponents()
	{
		final GridBagConstraints constraints = new GridBagConstraints();

		// Slicer label
		final JLabel slicersLabel = new JLabel("Program Slicing tools");
		constraints.gridy = 0;
		constraints.gridx = 0;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.weighty = 0.05;
		constraints.weightx = 1.0;
		constraints.anchor = GridBagConstraints.NORTH;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(10, 10, 0, 10);
		slicersLabel.setHorizontalAlignment(SwingConstants.CENTER);
		slicersLabel.setFont(new Font("Monaco", 0, 20));
		this.add(slicersLabel, constraints);

		// Slicers
		final JPanel slicersPanel = new JPanel();
		final JScrollPane slicersScrollPane = new JScrollPane(slicersPanel);
		constraints.gridy = 1;
		constraints.gridx = 0;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.weighty = 0.4;
		constraints.weightx = 1.0;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(0, 25, 10, 25);
		slicersScrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		slicersScrollPane.setPreferredSize(new Dimension(1000, 300));
		this.add(slicersScrollPane, constraints);

		// Slicers
		final List<Slicer> slicers = this.logic.getSlicers();
		final double slicerPercentage = 1 / (slicers.size() + 2);
		int slicerIndex = 0;

		slicersPanel.setLayout(new GridBagLayout());
		for (Slicer slicer : slicers)
		{
			// Slicer path
			final String slicerDescription = slicer.toString();
			final JButton slicerButton = new JButton(slicerDescription);
			constraints.gridy = slicerIndex;
			constraints.gridx = 0;
			constraints.gridheight = 1;
			constraints.gridwidth = 1;
			constraints.weighty = slicerPercentage;
			constraints.weightx = 1.0;
			constraints.anchor = GridBagConstraints.CENTER;
			constraints.fill = GridBagConstraints.BOTH;
			constraints.insets = new Insets(0, 10, 0, 10);
			slicersPanel.add(slicerButton, constraints);
			slicerButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					ConfigPanel.this.editSlicer(slicer);
				}
			});
			slicerIndex++;
		}

		// New slicer
		final JButton newSlicer = new JButton("Add Program Slicer");
		constraints.gridy = slicerIndex;
		constraints.gridx = 0;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.weighty = slicerPercentage;
		constraints.weightx = 1.0;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(0, 10, 0, 10);
		slicersPanel.add(newSlicer, constraints);
		newSlicer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				ConfigPanel.this.loadSlicer();
			}
		});

		// Slicer remaining panel
		final JPanel slicerRemainingPanel = new JPanel();
		constraints.gridy = slicerIndex + 1;
		constraints.gridx = 0;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.weighty = 1.0;
		constraints.weightx = 1.0;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(0, 0, 0, 0);
		slicersPanel.add(slicerRemainingPanel, constraints);

		// Remaining space
		final JPanel remainingSpace = new JPanel();
		constraints.gridy = 2;
		constraints.gridx = 0;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.weighty = 0.55;
		constraints.weightx = 1.0;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(0, 0, 0, 0);
		this.add(remainingSpace, constraints);
	}

	private void reload()
	{
		this.clear();
		this.addComponents();
		this.refresh();
	}
	private void clear()
	{
		this.removeAll();
	}
	private void refresh()
	{
		this.revalidate();
		this.repaint();
	}

	private void loadSlicer()
	{
		this.editSlicer(null);
	}
	private void editSlicer(Slicer slicer)
	{
		final LoadSlicer ls = new LoadSlicer(slicer);
		final LoadSlicer.Option option = ls.start();

		switch (option)
		{
			case Accept:
				final Slicer outputSlicer = ls.getOutput();
				final List<Slicer> slicers = this.logic.getSlicers();
				final int slicerIndex = slicer == null ? slicers.size() : slicers.indexOf(slicer);
				if (slicer != null)
					this.logic.removeSlicer(slicer);
				this.logic.addSlicer(slicerIndex, outputSlicer);
				this.logic.saveSlicers();
				this.reload();
				break;
			case Cancel:
				break;
			case Delete:
				this.logic.removeSlicer(slicer);
				this.logic.saveSlicers();
				this.reload();
				break;
			default:
				break;
		}
	}
}