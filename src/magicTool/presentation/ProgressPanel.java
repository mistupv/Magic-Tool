package magicTool.presentation;

import java.awt.GridLayout;

import javax.swing.JPanel;

public class ProgressPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	public ProgressPanel()
	{
		this.initComponents();
		this.addComponents();
	}
	private void initComponents()
	{
		this.setLayout(new GridLayout(2, 1));
	}
	private void addComponents()
	{
		// Phase 1
		final Phase1ProgressPanel phase1Progress = new Phase1ProgressPanel();
		this.add(phase1Progress);

		// Phase 2
		final Phase2ProgressPanel phase2Progress = new Phase2ProgressPanel();
		this.add(phase2Progress);
	}

	@SuppressWarnings("unused")
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
}