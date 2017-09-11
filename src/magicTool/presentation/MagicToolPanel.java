package magicTool.presentation;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import magicTool.logic.Logic;
import magicTool.logic.process.ProcessListener;

public class MagicToolPanel extends JPanel implements ProcessListener
{
	private static final long serialVersionUID = 1L;

	private final Logic logic = Logic.getLogic();
	private final JTabbedPane sectionsTabbedPane = new JTabbedPane();
	private final ConfigPanel cp = new ConfigPanel();
	private final SourceCodePanel scp = new SourceCodePanel();
	private final ProgressPanel pp = new ProgressPanel();
	private final SlicePanel sp = new SlicePanel();

	public MagicToolPanel()
	{
		this.logic.addProcessListener(this);

		this.initComponents();
		this.addComponents();

		this.sectionsTabbedPane.setSelectedIndex(1);
		this.sectionsTabbedPane.setEnabledAt(2, false);
		this.sectionsTabbedPane.setEnabledAt(3, false);
	}
	private void initComponents()
	{
		this.setLayout(new GridBagLayout());
	}
	private void addComponents()
	{
		final GridBagConstraints constraints = new GridBagConstraints();

		// Sections
		constraints.gridy = 0;
		constraints.gridx = 0;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.weighty = 1.0;
		constraints.weightx = 1.0;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(10, 0, 0, 0);
		this.sectionsTabbedPane.addTab("Config", this.cp);
		this.sectionsTabbedPane.addTab("Source code", this.scp);
		this.sectionsTabbedPane.addTab("Progress", this.pp);
		this.sectionsTabbedPane.addTab("Slice", this.sp);
		this.add(this.sectionsTabbedPane, constraints);
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

	public void processStarted()
	{
		this.sectionsTabbedPane.setEnabledAt(0, false);
		this.sectionsTabbedPane.setEnabledAt(2, true);
		this.sectionsTabbedPane.setEnabledAt(3, true);
		this.sectionsTabbedPane.setSelectedIndex(2);
	}
	public void processUpdated()
	{
		
	}
	public void processFinished()
	{
		this.sectionsTabbedPane.setEnabledAt(0, true);
	}
}