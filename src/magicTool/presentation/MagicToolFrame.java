package magicTool.presentation;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MagicToolFrame extends JFrame
{
	private static final long serialVersionUID = 1L;

	private static final Dimension minimumSize = new Dimension(750, 500);
	private static final Dimension initialSize = new Dimension(750, 500);

	private final JPanel content = new JPanel();
	private final MagicToolPanel magicTool = new MagicToolPanel();

	public MagicToolFrame()
	{
		this.initComponents();
		this.addComponents();

		this.setVisible(true);
	}
	private void initComponents()
	{
		this.setMinimumSize(MagicToolFrame.minimumSize);
		this.setSize(MagicToolFrame.initialSize);
		this.setResizable(false);

		this.setTitle("Magic Tool");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		this.add(this.content);
		this.content.setLayout(new GridBagLayout());
	}
	private void addComponents()
	{
		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridy = 0;
		constraints.gridx = 0;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.weighty = 1.0;
		constraints.weightx = 1.0;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(0, 0, 0, 0);
		this.content.add(this.magicTool, constraints);
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
		this.content.removeAll();
	}
	private void refresh()
	{
		this.content.revalidate();
		this.content.repaint();
	}
}