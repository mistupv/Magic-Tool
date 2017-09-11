package magicTool.presentation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import magicTool.logic.Logic;
import magicTool.logic.Slicer;
import magicTool.logic.process.ProcessListener;
import magicTool.logic.progress.Phase1Progress;
import magicTool.logic.progress.Progress;
import misc.Misc;

public class Phase1ProgressPanel extends JPanel implements ProcessListener
{
	private static final long serialVersionUID = 1L;

	private final Logic logic = Logic.getLogic();
	private final JPanel contentPanel = new JPanel();
	private final JScrollPane contentScrollPane = new JScrollPane(this.contentPanel);

	public Phase1ProgressPanel()
	{
		this.logic.addProcessListener(this);

		this.initComponents();
		this.addComponents();
	}
	private void initComponents()
	{
		final GridBagConstraints constraints = new GridBagConstraints();

		this.setLayout(new GridBagLayout());
		constraints.gridy = 0;
		constraints.gridx = 0;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.weighty = 1.0;
		constraints.weightx = 1.0;
		constraints.anchor = GridBagConstraints.NORTH;
		constraints.fill = GridBagConstraints.VERTICAL;
		constraints.insets = new Insets(10, 10, 10, 10);
		this.add(this.contentScrollPane, constraints);

		this.contentScrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.contentPanel.setLayout(null);
	}
	private void addComponents()
	{
		final int textX = 0;
		final int textY = 0;
		final int textHeight = 20;
		final int slicerWidth = 150;
		final int iterationWidth = 100;
		final int timeWidth = 100;
		final int reductionWidth = 75;
		final List<Slicer> slicers = this.logic.getSlicers();
		final List<Phase1Progress> progress = this.logic.getPhase1Progress();
		final int slicersSize = slicers.size();
		final int progressSize = Math.max(3, this.getMaxProgress(slicers, progress));

		// Header
		final JLabel slicerLabel = new JLabel("Program Slicer", SwingConstants.CENTER);

		this.contentPanel.add(slicerLabel);
		slicerLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK));
		slicerLabel.setBounds(textX, textY + textHeight, slicerWidth, textHeight);
		for (int progressIndex = 0; progressIndex < progressSize; progressIndex++)
		{
			final int currentTimeX = textX + slicerWidth + (timeWidth + reductionWidth) * progressIndex;
			final int currentReductionX = currentTimeX + timeWidth;
			final JLabel iterationLabel = new JLabel("Iteration " + (progressIndex + 1), SwingConstants.CENTER);
			final JLabel timeLabel = new JLabel("Time spent  ", SwingConstants.RIGHT);
			final JLabel reductionLabel = new JLabel("  Reduction", SwingConstants.LEFT);

			this.contentPanel.add(iterationLabel);
			this.contentPanel.add(timeLabel);
			this.contentPanel.add(reductionLabel);
			timeLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
			reductionLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
			iterationLabel.setBounds(currentTimeX + timeWidth - iterationWidth / 2, textY, iterationWidth, textHeight);
			timeLabel.setBounds(currentTimeX, textY + textHeight, timeWidth, textHeight);
			reductionLabel.setBounds(currentReductionX, textY + textHeight, reductionWidth, textHeight);
		}

		// Body
		for (int slicerIndex = 0; slicerIndex < slicersSize; slicerIndex++)
		{
			final Slicer slicer = slicers.get(slicerIndex);
			final String slicerName = slicer.getName();
			final List<Phase1Progress> slicerProgress = this.getProgress(slicer, progress);
			final JLabel slicerNameLabel = new JLabel(slicerName, SwingConstants.CENTER);
			final int currentSlicerY = textY + textHeight + textHeight * (slicerIndex + 1);

			this.contentPanel.add(slicerNameLabel);
			slicerNameLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK));
			slicerNameLabel.setBounds(textX, currentSlicerY, slicerWidth, textHeight);
			for (int progressIndex = 0; progressIndex < slicerProgress.size(); progressIndex++)
			{
				final Progress singleProgress = slicerProgress.get(progressIndex);
				final long time = singleProgress.getTime();
				final double reduction = singleProgress.getReduction();
				final JLabel slicerTimeLabel = new JLabel(time + " ms  ", SwingConstants.RIGHT);
				final JLabel slicerReductionLabel = new JLabel("  " + Misc.round(reduction, 3) + " %", SwingConstants.LEFT);
				final int currentTimeX = textX + slicerWidth + (timeWidth + reductionWidth) * progressIndex;
				final int currentReductionX = currentTimeX + timeWidth;

				this.contentPanel.add(slicerTimeLabel);
				this.contentPanel.add(slicerReductionLabel);
				slicerTimeLabel.setBounds(currentTimeX, currentSlicerY, timeWidth, textHeight);
				slicerReductionLabel.setBounds(currentReductionX, currentSlicerY, reductionWidth, textHeight);
			}
		}

		// Panel size
		final int lastX = textX + slicerWidth + (timeWidth + reductionWidth) * progressSize;
		final int lastY = textY + textHeight + textHeight * slicersSize;
		this.contentScrollPane.setMinimumSize(new Dimension(lastX + textX + 20, lastY + textY));
		this.contentPanel.setPreferredSize(new Dimension(lastX + textX, lastY + textY));
	}

	private void reload()
	{
		this.clear();
		this.addComponents();
		this.refresh();
	}
	private void clear()
	{
		this.contentPanel.removeAll();
	}
	private void refresh()
	{
		this.revalidate();
		this.repaint();
	}

	private int getMaxProgress(List<Slicer> slicers, List<Phase1Progress> progress)
	{
		int max = 0;

		for (Slicer slicer : slicers)
		{
			final List<Phase1Progress> sliceProgress = this.getProgress(slicer, progress);
			max = Math.max(max, sliceProgress.size());
		}

		return max;
	}
	private List<Phase1Progress> getProgress(Slicer slicer, List<Phase1Progress> progress)
	{
		final List<Phase1Progress> slicerProgress = new LinkedList<Phase1Progress>();

		for (Phase1Progress singleProgress : progress)
		{
			final Slicer slicer0 = singleProgress.getSlicer();

			if (slicer == slicer0)
				slicerProgress.add(singleProgress);
		}

		return slicerProgress;
	}

	public void processStarted()
	{
		this.reload();
	}
	public void processUpdated()
	{
		this.reload();
	}
	public void processFinished()
	{
		this.reload();
	}
}