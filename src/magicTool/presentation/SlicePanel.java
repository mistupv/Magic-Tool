package magicTool.presentation;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import magicTool.config.Config;
import magicTool.logic.Logic;
import magicTool.logic.process.ProcessListener;
import misc.Misc;

public class SlicePanel extends JPanel implements ProcessListener
{
	private static final long serialVersionUID = 1L;

	private final Config config = Config.getConfig();
	private final Logic logic = Logic.getLogic();
	private final JTabbedPane sourceCodeTabbedPane = new JTabbedPane();
	private JEditorPane sourceCodeEditorPane = new JEditorPane();
	private JScrollPane sourceCodeScrollPane = new JScrollPane(this.sourceCodeEditorPane);
	private JEditorPane completeSliceEditorPane = new JEditorPane();
	private JScrollPane completeSliceScrollPane = new JScrollPane(this.completeSliceEditorPane);
	private JEditorPane quasiMinimalSliceEditorPane = new JEditorPane();
	private JScrollPane quasiMinimalSliceScrollPane = new JScrollPane(this.quasiMinimalSliceEditorPane);

	public SlicePanel()
	{
		this.logic.addProcessListener(this);

		this.initComponents();
		this.addComponents();
	}
	private void initComponents()
	{
		this.setLayout(new GridBagLayout());

		this.initSourceCode();
	}
	private void initSourceCode()
	{
		this.initSourceCodeScrollPane();
		this.initCompleteSliceScrollPane();
		this.initQuasiMinimalSliceScrollPane();
	}
	private void initSourceCodeScrollPane()
	{
		final JEditorPane sourceCodeEditorPane = new JEditorPane();
		final JScrollPane sourceCodeScrollPane = new JScrollPane(sourceCodeEditorPane);

		this.initEditorPane(sourceCodeEditorPane, false);
		sourceCodeScrollPane.setBorder(BorderFactory.createLineBorder(Color.black));
		this.sourceCodeEditorPane = sourceCodeEditorPane;
		this.sourceCodeScrollPane = sourceCodeScrollPane;
		this.sourceCodeTabbedPane.addTab("Source code", this.sourceCodeScrollPane);
	}
	private void initCompleteSliceScrollPane()
	{
		final JEditorPane completeSliceEditorPane = new JEditorPane();
		final JScrollPane completeSliceScrollPane = new JScrollPane(completeSliceEditorPane);

		this.initEditorPane(completeSliceEditorPane, false);
		completeSliceScrollPane.setBorder(BorderFactory.createLineBorder(Color.black));
		this.completeSliceEditorPane = completeSliceEditorPane;
		this.completeSliceScrollPane = completeSliceScrollPane;
		this.sourceCodeTabbedPane.addTab("Complete Slice", this.completeSliceScrollPane);
	}
	private void initQuasiMinimalSliceScrollPane()
	{
		final JEditorPane quasiMinimalSliceEditorPane = new JEditorPane();
		final JScrollPane quasiMinimalSliceScrollPane = new JScrollPane(quasiMinimalSliceEditorPane);

		this.initEditorPane(quasiMinimalSliceEditorPane, false);
		quasiMinimalSliceScrollPane.setBorder(BorderFactory.createLineBorder(Color.black));
		this.quasiMinimalSliceEditorPane = quasiMinimalSliceEditorPane;
		this.quasiMinimalSliceScrollPane = quasiMinimalSliceScrollPane;
		this.sourceCodeTabbedPane.addTab("Quasi-Minimal Slice", this.quasiMinimalSliceScrollPane);
	}
	private void initEditorPane(JEditorPane editorPane, boolean editable)
	{
		editorPane.setEditorKit(new jsyntaxpane.syntaxkits.JavaSyntaxKit());
		editorPane.setEditable(editable);
		editorPane.setMargin(new Insets(10, 10, 10, 10));
		editorPane.setFont(new Font("Monaco", 0, 12));
	}
	private void addComponents()
	{
		final GridBagConstraints constraints = new GridBagConstraints();

		// Source code
		constraints.gridy = 0;
		constraints.gridx = 0;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.weighty = 1.0;
		constraints.weightx = 1.0;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(10, 10, 10, 10);
		this.add(this.sourceCodeTabbedPane, constraints);
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

	private void setText(JEditorPane editorPane, String text)
	{
		editorPane.setText(text);
		editorPane.setCaretPosition(0);
	}

	public void processStarted()
	{
		final String temporaryPath = this.config.getTemporaryPath();
		final String sourceCodePath = temporaryPath + this.config.getSourceCode();
		final String text = Misc.read(sourceCodePath);

		this.setText(this.sourceCodeEditorPane, text);
		this.setText(this.completeSliceEditorPane, text);
		this.setText(this.quasiMinimalSliceEditorPane, text);
	}
	public void processUpdated()
	{
		final String temporaryPath = this.config.getTemporaryPath();

		final String completeSlicePath = temporaryPath + "CompleteSlice.txt";
		final File completeSliceFile = new File(completeSlicePath);
		final String completeSlice = completeSliceFile.exists() ? Misc.read(completeSliceFile) : "";
		this.setText(this.completeSliceEditorPane, completeSlice);

		final String quasiMinimalSlicePath = temporaryPath + "QuasiMinimalSlice.txt";
		final File quasiMinimalSliceFile = new File(quasiMinimalSlicePath);
		final String quasiMinimalSlice = quasiMinimalSliceFile.exists() ? Misc.read(quasiMinimalSliceFile) : "";
		this.setText(this.quasiMinimalSliceEditorPane, quasiMinimalSlice);
	}
	public void processFinished()
	{
		
	}
}