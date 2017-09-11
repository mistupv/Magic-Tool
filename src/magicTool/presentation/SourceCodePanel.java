package magicTool.presentation;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.Element;
import javax.swing.text.Utilities;

import edg.graph.EDG;
import edg.graph.Node;
import edg.graph.NodeInfo.Type;
import eknife.EDGFactory;
import eknife.EKnife.Language;
import magicTool.config.Config;
import magicTool.logic.Logic;
import magicTool.logic.SlicingCriterion;
import magicTool.logic.process.ProcessListener;
import magicTool.misc.Miscellanea;
import misc.Misc;

public class SourceCodePanel extends JPanel implements ProcessListener
{
	private static final long serialVersionUID = 1L;

	private final Config config = Config.getConfig();
	private final Logic logic = Logic.getLogic();
	private JEditorPane sourceCodeEditorPane = new JEditorPane();
	private JScrollPane sourceCodeScrollPane = new JScrollPane(this.sourceCodeEditorPane);
	private final JButton openFileButton = new JButton("Load source code");
	private final JButton runButton = new JButton("Obtain minimal slice");
	private final JFileChooser fileChooser = new JFileChooser();
	private boolean running;

	public SourceCodePanel()
	{
		this.logic.addProcessListener(this);

		this.initComponents();
		this.addComponents();
	}
	private void initComponents()
	{
		this.setLayout(new GridBagLayout());

		this.sourceCodeEditorPane.setEditorKit(new jsyntaxpane.syntaxkits.JavaSyntaxKit());
		this.sourceCodeEditorPane.setEditable(true);
		this.sourceCodeEditorPane.setMargin(new Insets(10, 10, 10, 10));
		this.sourceCodeEditorPane.setFont(new Font("Monaco", 0, 12));
		this.sourceCodeScrollPane.setBorder(BorderFactory.createLineBorder(Color.black));

		this.openFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				SourceCodePanel.this.openFile();
			}
		});
		this.runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				SourceCodePanel.this.run();
			}
		});
	}
	private void addComponents()
	{
		final GridBagConstraints constraints = new GridBagConstraints();

		// Source code
		constraints.gridy = 0;
		constraints.gridx = 0;
		constraints.gridheight = 1;
		constraints.gridwidth = 2;
		constraints.weighty = 0.95;
		constraints.weightx = 1.0;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(10, 10, 10, 10);
		this.add(this.sourceCodeScrollPane, constraints);

		// Open button
		constraints.gridy = 1;
		constraints.gridx = 0;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.weighty = 0.05;
		constraints.weightx = 0.5;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(10, 10, 10, 10);
		this.add(this.openFileButton, constraints);

		// Run button
		constraints.gridy = 1;
		constraints.gridx = 1;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.weighty = 0.05;
		constraints.weightx = 0.5;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(10, 10, 10, 10);
		this.add(this.runButton, constraints);
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

	private void openFile()
	{
		final File selectedFile;
		final int selectionMode = JFileChooser.FILES_ONLY;

		this.fileChooser.setFileFilter(null);
		this.fileChooser.setFileSelectionMode(selectionMode);

		switch (this.fileChooser.showOpenDialog(this))
		{
			case JFileChooser.APPROVE_OPTION:
				selectedFile = this.fileChooser.getSelectedFile();
				break;
			case JFileChooser.CANCEL_OPTION:
			default:
				selectedFile = null;
				break;
		}

		if (selectedFile == null || !selectedFile.exists())
			return;
		final String text = Misc.read(selectedFile);

		this.setText(this.sourceCodeEditorPane, text);
	}
	private void setText(JEditorPane editorPane, String text)
	{
		editorPane.setText(text);
		editorPane.setCaretPosition(0);
	}

	private void run()
	{
		if (!this.running)
			this.start();
		else
			this.stop();
	}
	private void start()
	{
		final SlicingCriterion slicingCriterion = this.getSlicingCriterion();
		if (slicingCriterion == null)
			return;

		this.logic.setSlicingCriterion(slicingCriterion);
		this.logic.startProcess();
	}
	private void stop()
	{
		this.logic.stopProcess();
	}

	public void processStarted()
	{
		this.openFileButton.setEnabled(false);
		this.sourceCodeEditorPane.setEditable(false);
		this.runButton.setText("Stop process");
		this.running = true;
	}
	public void processUpdated()
	{
		
	}
	public void processFinished()
	{
		this.openFileButton.setEnabled(true);
		this.sourceCodeEditorPane.setEditable(true);
		this.runButton.setText("Obtain minimal slice");
		this.running = false;
	}

	private SlicingCriterion getSlicingCriterion()
	{
		final String selectedText = this.sourceCodeEditorPane.getSelectedText();
		if (selectedText == null)
		{
			JOptionPane.showMessageDialog(null, "You must select a variable");
			return null;
		}

		final String text = this.sourceCodeEditorPane.getText();
		final String temporaryPath = this.config.getTemporaryPath();
		final String sourceCodePath = temporaryPath + this.config.getSourceCode();
		Misc.write(sourceCodePath, text, false);

		final EDG edg = EDGFactory.createEDG(Language.Erlang, sourceCodePath, false);
		if (edg == null)
		{
			JOptionPane.showMessageDialog(null, "The code is wrong");
			return null;
		}
		final SlicingCriterion slicingCriterion = this.getSlicingCriterionSelected();
		final Node node = Miscellanea.getNode(edg, slicingCriterion);
		if (slicingCriterion == null || node == null || node.getData().getType() != Type.Variable)
		{
			JOptionPane.showMessageDialog(null, "You did not select a variable");
			return null;
		}

		return slicingCriterion;
	}
	private SlicingCriterion getSlicingCriterionSelected()
	{
		final int startOffset = this.sourceCodeEditorPane.getSelectionStart();
		final Point startPoint = this.getPoint(this.sourceCodeEditorPane, startOffset);
		final int line = startPoint.y;
		final int column = startPoint.x;
		final String text = this.getText(this.sourceCodeEditorPane);
		final int occurrence = this.getOccurrence(this.sourceCodeEditorPane, startPoint, text);

		return new SlicingCriterion(line, text, occurrence, column, startOffset);
	}
	private String getText(JEditorPane editorPane)
	{
		final String selectedText = editorPane.getSelectedText();
		if (selectedText != null)
			return selectedText;

		try
		{
			final int offset = editorPane.getCaretPosition();
			final int wordStartOffset = Utilities.getWordStart(editorPane, offset);
			final int wordEndOffset = Utilities.getWordEnd(editorPane, offset);

			return editorPane.getText(wordStartOffset, wordEndOffset - wordStartOffset);
		}
		catch (Exception e)
		{
			return null;
		}
	}
	private Point getPoint(JEditorPane editorPane, int offset)
	{
		final Element map = editorPane.getDocument().getDefaultRootElement();
		final int row = map.getElementIndex(offset);
		final Element lineElem = map.getElement(row);
		final int col = offset - lineElem.getStartOffset();

		return new Point(col + 1, row + 1);
	}
	private int getOccurrence(JEditorPane editorPane, Point startPoint, String text)
	{
		final String line = this.getLine(editorPane, startPoint);

		return magicTool.misc.Miscellanea.getOccurrence(magicTool.misc.Miscellanea.Language.Erlang, line, startPoint.x, text);
	}
	private String getLine(JEditorPane editorPane, Point point)
	{
		try
		{
			final Element map = editorPane.getDocument().getDefaultRootElement();
			final Element lineElem = map.getElement(point.y - 1);
			final int lineStartOffset = lineElem.getStartOffset();
			final int lineEndOffset = lineElem.getEndOffset();

			return editorPane.getText(lineStartOffset, lineEndOffset - lineStartOffset);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}