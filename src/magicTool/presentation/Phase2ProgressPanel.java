package magicTool.presentation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import magicTool.logic.Logic;
import magicTool.logic.process.ProcessListener;
import magicTool.logic.progress.Phase2Progress;
import misc.Misc;

public class Phase2ProgressPanel extends JPanel implements ProcessListener
{
	private static final long serialVersionUID = 1L;

	private final Logic logic = Logic.getLogic();
	private final JPanel contentPanel = new JPanel();
	private final JScrollPane contentScrollPane = new JScrollPane(this.contentPanel);

	public Phase2ProgressPanel()
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
		final int xAtATimeWidth = 166;
		final int successesWidth = 166;
		final int progressWidth = 166;
		final int timeWidth = 100;
		final int reductionWidth = 75;

		// Header
		final JLabel xAtATimeLabel = new JLabel("X at a time", SwingConstants.CENTER);
		final JLabel successesLabel = new JLabel("Successes", SwingConstants.CENTER);
		final JLabel progressLabel = new JLabel("Progress", SwingConstants.CENTER);
		final JLabel timeLabel = new JLabel("Time spent  ", SwingConstants.RIGHT);
		final JLabel reductionLabel = new JLabel("  Reduction", SwingConstants.LEFT);
		final int successesX = textX + xAtATimeWidth;
		final int progressX = successesX + successesWidth;
		final int timeX = progressX + progressWidth;
		final int reductionX = timeX + timeWidth;

		this.contentPanel.add(xAtATimeLabel);
		this.contentPanel.add(successesLabel);
		this.contentPanel.add(progressLabel);
		this.contentPanel.add(timeLabel);
		this.contentPanel.add(reductionLabel);
		xAtATimeLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK));
		successesLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		progressLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		timeLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		reductionLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		xAtATimeLabel.setBounds(textX, textY, xAtATimeWidth, textHeight);
		successesLabel.setBounds(successesX, textY, successesWidth, textHeight);
		progressLabel.setBounds(progressX, textY, progressWidth, textHeight);
		timeLabel.setBounds(timeX, textY, timeWidth, textHeight);
		reductionLabel.setBounds(reductionX, textY, reductionWidth, textHeight);

		// Body
		final List<Phase2Progress> progress = this.logic.getPhase2Progress();

		for (int progressIndex = 0; progressIndex < progress.size(); progressIndex++)
		{
			final Phase2Progress singleProgress = progress.get(progressIndex);
			final int xAtATime = singleProgress.getXAtATime();
			final int successes = singleProgress.getSuccesses();
			final int current = singleProgress.getCurrent();
			final int maximum = singleProgress.getMaximum();
			final long time = singleProgress.getTime();
			final double reduction = singleProgress.getReduction();
			final int currentY = textY + textHeight + progressIndex * textHeight;
			final String successesText = successes == 1 ? successes + " hit" : successes + " hits";
			final String progressText = Misc.round(100.0 * current / maximum, 3) + " %";

			final JLabel xAtATimeProgressLabel = new JLabel(xAtATime + " at a time", SwingConstants.CENTER);
			final JLabel successesProgressLabel = new JLabel(successesText, SwingConstants.CENTER);
			final JLabel currentProgressLabel = new JLabel(progressText, SwingConstants.CENTER);
			final JLabel timeProgressLabel = new JLabel(time + " ms  ", SwingConstants.RIGHT);
			final JLabel reductionProgressLabel = new JLabel("  " + Misc.round(reduction, 3) + " %", SwingConstants.LEFT);

			this.contentPanel.add(xAtATimeProgressLabel);
			this.contentPanel.add(successesProgressLabel);
			this.contentPanel.add(currentProgressLabel);
			this.contentPanel.add(timeProgressLabel);
			this.contentPanel.add(reductionProgressLabel);
			xAtATimeProgressLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK));
			xAtATimeProgressLabel.setBounds(textX, currentY, xAtATimeWidth, textHeight);
			successesProgressLabel.setBounds(successesX, currentY, successesWidth, textHeight);
			currentProgressLabel.setBounds(progressX, currentY, progressWidth, textHeight);
			timeProgressLabel.setBounds(timeX, currentY, timeWidth, textHeight);
			reductionProgressLabel.setBounds(reductionX, currentY, reductionWidth, textHeight);
		}

		// Panel size
		final int lastX = reductionX + reductionWidth;
		final int lastY = textY + textHeight + textHeight * progress.size();
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