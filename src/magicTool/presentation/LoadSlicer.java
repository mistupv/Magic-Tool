package magicTool.presentation;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import magicTool.logic.Slicer;

public class LoadSlicer extends JDialog
{
	private static final long serialVersionUID = 1L;

	private static final Dimension initialSize = new Dimension(365, 515);

	public enum Option { Accept, Cancel, Delete, None }

	private final Slicer inputSlicer;
	private Slicer outputSlicer;
	private Option option = Option.None;
	private final JLabel nameLabel = new JLabel("Name:");
	private final JTextField nameTextField = new JTextField();
	private final JLabel pathLabel = new JLabel("Path:");
	private final JButton pathButton = new JButton();
	private final JPanel flagsPanel = new JPanel();
	private final JScrollPane flagsScrollPane = new JScrollPane(this.flagsPanel);
	private final JLabel inputLabel = new JLabel("Input:");
	private final JTextField inputTextField = new JTextField();
	private final JLabel outputLabel = new JLabel("Output:");
	private final JTextField outputTextField = new JTextField();
	private final JLabel scLineLabel = new JLabel("Line:");
	private final JTextField scLineTextField = new JTextField();
	private final JLabel scNameLabel = new JLabel("Name:");
	private final JTextField scNameTextField = new JTextField();
	private final JLabel scOccurrenceLabel = new JLabel("Occurrence:");
	private final JTextField scOccurrenceTextField = new JTextField();
	private final JLabel scLengthLabel = new JLabel("Length:");
	private final JTextField scLengthTextField = new JTextField();
	private final JLabel scStartLineLabel = new JLabel("Start line:");
	private final JTextField scStartLineTextField = new JTextField();
	private final JLabel scStartColumnLabel = new JLabel("Start column:");
	private final JTextField scStartColumnTextField = new JTextField();
	private final JLabel scEndLineLabel = new JLabel("End line:");
	private final JTextField scEndLineTextField = new JTextField();
	private final JLabel scEndColumnLabel = new JLabel("End column:");
	private final JTextField scEndColumnTextField = new JTextField();
	private final JLabel scStartOffsetLabel = new JLabel("Start offset:");
	private final JTextField scStartOffsetTextField = new JTextField();
	private final JLabel scEndOffsetLabel = new JLabel("End offset:");
	private final JTextField scEndOffsetTextField = new JTextField();
	private final JPanel flagCombinationsPanel = new JPanel();
	private final JScrollPane flagCombinationsScrollPane = new JScrollPane(this.flagCombinationsPanel);
	private final JLabel commandLabel = new JLabel("Command:");
	private final JTextField commandTextField = new JTextField();
	private final JCheckBox flagCombination0CheckBox = new JCheckBox();
	private final JCheckBox flagCombination1CheckBox = new JCheckBox();
	private final JCheckBox flagCombination2CheckBox = new JCheckBox();
	private final JCheckBox flagCombination3CheckBox = new JCheckBox();
	private final JCheckBox flagCombination4CheckBox = new JCheckBox();
	private final JCheckBox flagCombination5CheckBox = new JCheckBox();
	private final JButton acceptButton = new JButton("Accept");
	private final JButton cancelButton = new JButton("Cancel");
	private final JButton deleteButton = new JButton("Delete");

	public LoadSlicer(Slicer slicer)
	{
		this.inputSlicer = slicer;

		this.initComponents();
		this.addComponents();
	}
	private void initComponents()
	{
		final class FlagDocument extends PlainDocument {
			private static final long serialVersionUID = 1L;

			private boolean ignoreEvents = false;

			public void replace(int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
				final String oldValue = super.getText(0, super.getLength());
				this.ignoreEvents = true;
				super.replace(offset, length, text, attrs);
				this.ignoreEvents = false;
				final String newValue = super.getText(0, super.getLength());
				LoadSlicer.this.replaceFlag(oldValue, newValue);
			}

			public void remove(int offs, int len) throws BadLocationException {
				final String oldValue = super.getText(0, super.getLength());
				super.remove(offs, len);
				final String newValue = super.getText(0, super.getLength());
				if (!this.ignoreEvents)
					LoadSlicer.this.replaceFlag(oldValue, newValue);
			}
		};
		final String commandExplanation = "This command will be executed to invoke the Program Slicer";
		final String commandExample1 = "<b>{flag}</b> will be replaced with <b>-flag flagValue</b>";
		final String commandExample2 = "<b>[flag]</b> will be replaced with <b>flagValue</b>";
		final String commandExamples = "<html>" + commandExplanation + "<br>" + commandExample1 + "<br>" + commandExample2 + "</html>";

		final TitledBorder commandBorder = new TitledBorder("Execution command");
		final TitledBorder flagsBorder = new TitledBorder("Program Slicing flags");
		final TitledBorder flagCombinationsBorder = new TitledBorder("Program Slicing flag combinations");
		commandBorder.setTitleJustification(TitledBorder.CENTER);
		commandBorder.setTitlePosition(TitledBorder.TOP);
		flagsBorder.setTitleJustification(TitledBorder.CENTER);
		flagsBorder.setTitlePosition(TitledBorder.TOP);
		flagCombinationsBorder.setTitleJustification(TitledBorder.CENTER);
		flagCombinationsBorder.setTitlePosition(TitledBorder.TOP);

		this.setSize(LoadSlicer.initialSize);
		this.setLayout(null);
		this.flagsScrollPane.setBorder(BorderFactory.createEmptyBorder());
		this.flagsPanel.setLayout(null);
		this.flagsPanel.setBorder(flagsBorder);
		this.flagCombinationsScrollPane.setBorder(BorderFactory.createEmptyBorder());
		this.flagCombinationsPanel.setLayout(new BoxLayout(this.flagCombinationsPanel, BoxLayout.Y_AXIS));
		this.flagCombinationsPanel.setBorder(flagCombinationsBorder);

		this.nameLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		this.pathLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		this.pathButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				LoadSlicer.this.loadPath();
			}
		});
		this.acceptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				LoadSlicer.this.accept();
			}
		});
		this.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				LoadSlicer.this.cancel();
			}
		});
		this.deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				LoadSlicer.this.delete();
			}
		});
		this.inputTextField.setDocument(new FlagDocument());
		this.outputTextField.setDocument(new FlagDocument());
		this.scLineTextField.setDocument(new FlagDocument());
		this.scNameTextField.setDocument(new FlagDocument());
		this.scOccurrenceTextField.setDocument(new FlagDocument());
		this.scLengthTextField.setDocument(new FlagDocument());
		this.scStartLineTextField.setDocument(new FlagDocument());
		this.scStartColumnTextField.setDocument(new FlagDocument());
		this.scEndLineTextField.setDocument(new FlagDocument());
		this.scEndColumnTextField.setDocument(new FlagDocument());
		this.scStartOffsetTextField.setDocument(new FlagDocument());
		this.scEndOffsetTextField.setDocument(new FlagDocument());
		this.commandTextField.setToolTipText(commandExamples);
		this.flagCombination0CheckBox.setSelected(true);
		this.flagCombination0CheckBox.setEnabled(false);
		this.flagCombination1CheckBox.setSelected(true);
		this.flagCombination2CheckBox.setSelected(true);
		this.flagCombination3CheckBox.setSelected(true);
		this.flagCombination4CheckBox.setSelected(true);
		this.flagCombination5CheckBox.setSelected(true);

		this.loadConfig();
		this.updateFlags();
	}
	private void addComponents()
	{
		this.add(this.nameLabel);
		this.add(this.nameTextField);
		this.add(this.pathLabel);
		this.add(this.pathButton);
		this.add(this.flagsScrollPane);
		this.flagsPanel.add(this.inputLabel);
		this.flagsPanel.add(this.inputTextField);
		this.flagsPanel.add(this.outputLabel);
		this.flagsPanel.add(this.outputTextField);
		this.flagsPanel.add(this.scLineLabel);
		this.flagsPanel.add(this.scLineTextField);
		this.flagsPanel.add(this.scNameLabel);
		this.flagsPanel.add(this.scNameTextField);
		this.flagsPanel.add(this.scOccurrenceLabel);
		this.flagsPanel.add(this.scOccurrenceTextField);
		this.flagsPanel.add(this.scLengthLabel);
		this.flagsPanel.add(this.scLengthTextField);
		this.flagsPanel.add(this.scStartLineLabel);
		this.flagsPanel.add(this.scStartLineTextField);
		this.flagsPanel.add(this.scStartColumnLabel);
		this.flagsPanel.add(this.scStartColumnTextField);
		this.flagsPanel.add(this.scEndLineLabel);
		this.flagsPanel.add(this.scEndLineTextField);
		this.flagsPanel.add(this.scEndColumnLabel);
		this.flagsPanel.add(this.scEndColumnTextField);
		this.flagsPanel.add(this.scStartOffsetLabel);
		this.flagsPanel.add(this.scStartOffsetTextField);
		this.flagsPanel.add(this.scEndOffsetLabel);
		this.flagsPanel.add(this.scEndOffsetTextField);
		this.add(this.commandLabel);
		this.add(this.commandTextField);
		this.add(this.flagCombinationsScrollPane);
		this.flagCombinationsPanel.add(this.flagCombination0CheckBox);
		this.flagCombinationsPanel.add(this.flagCombination1CheckBox);
		this.flagCombinationsPanel.add(this.flagCombination2CheckBox);
		this.flagCombinationsPanel.add(this.flagCombination3CheckBox);
		this.flagCombinationsPanel.add(this.flagCombination4CheckBox);
		this.flagCombinationsPanel.add(this.flagCombination5CheckBox);
		this.add(this.acceptButton);
		this.add(this.cancelButton);
		this.add(this.deleteButton);

		final int size = this.getWidth();
		final int buttonWidth = 100;
		final int buttons = this.inputSlicer == null ? 2 : 3;
		final int regionSize = size / buttons;

		this.nameLabel.setBounds(10, 10, 40, 20);
		this.nameTextField.setBounds(55, 10, 300, 20);
		this.pathLabel.setBounds(10, 35, 40, 20);
		this.pathButton.setBounds(55, 35, 300, 20);
		this.flagsScrollPane.setBounds(10, 60, 345, 180);
		this.inputLabel.setBounds(10, 20, 85, 20);
		this.inputTextField.setBounds(100, 20, 70, 20);
		this.outputLabel.setBounds(180, 20, 85, 20);
		this.outputTextField.setBounds(265, 20, 70, 20);
		this.scLineLabel.setBounds(10, 45, 85, 20);
		this.scLineTextField.setBounds(100, 45, 70, 20);
		this.scNameLabel.setBounds(180, 45, 85, 20);
		this.scNameTextField.setBounds(265, 45, 70, 20);
		this.scOccurrenceLabel.setBounds(10, 70, 85, 20);
		this.scOccurrenceTextField.setBounds(100, 70, 70, 20);
		this.scLengthLabel.setBounds(180, 70, 85, 20);
		this.scLengthTextField.setBounds(265, 70, 70, 20);
		this.scStartLineLabel.setBounds(10, 95, 85, 20);
		this.scStartLineTextField.setBounds(100, 95, 70, 20);
		this.scStartColumnLabel.setBounds(180, 95, 85, 20);
		this.scStartColumnTextField.setBounds(265, 95, 70, 20);
		this.scEndLineLabel.setBounds(10, 120, 85, 20);
		this.scEndLineTextField.setBounds(100, 120, 70, 20);
		this.scEndColumnLabel.setBounds(180, 120, 85, 20);
		this.scEndColumnTextField.setBounds(265, 120, 70, 20);
		this.scStartOffsetLabel.setBounds(10, 145, 85, 20);
		this.scStartOffsetTextField.setBounds(100, 145, 70, 20);
		this.scEndOffsetLabel.setBounds(180, 145, 85, 20);
		this.scEndOffsetTextField.setBounds(265, 145, 70, 20);
		this.commandLabel.setBounds(10, 245, 70, 20);
		this.commandTextField.setBounds(85, 245, 270, 20);
		this.flagCombinationsScrollPane.setBounds(10, 270, 345, 180);
		this.acceptButton.setBounds(0 * regionSize + regionSize / 2 - buttonWidth / 2, 460, buttonWidth, 20);
		this.cancelButton.setBounds(1 * regionSize + regionSize / 2 - buttonWidth / 2, 460, buttonWidth, 20);
		if (this.inputSlicer != null)
			this.deleteButton.setBounds(2 * regionSize + regionSize / 2 - buttonWidth / 2, 460, buttonWidth, 20);
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

	public Option start()
	{
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setModal(true);
		this.setVisible(true);

		return this.option;
	}
	private void accept()
	{
		final String name = this.nameTextField.getText();
		final String path = this.pathButton.getText();
		final String command = this.commandTextField.getText();
		final String input = this.inputTextField.getText();
		final String output = this.outputTextField.getText();
		final String scLine = this.scLineTextField.getText();
		final String scName = this.scNameTextField.getText();
		final String scOccurrence = this.scOccurrenceTextField.getText();
		final String scLength = this.scLengthTextField.getText();
		final String scStartLine = this.scStartLineTextField.getText();
		final String scStartColumn = this.scStartColumnTextField.getText();
		final String scEndLine = this.scEndLineTextField.getText();
		final String scEndColumn = this.scEndColumnTextField.getText();
		final String scStartOffset = this.scStartOffsetTextField.getText();
		final String scEndOffset = this.scEndOffsetTextField.getText();
		final boolean flagCombination1 = this.flagCombination1CheckBox.isSelected();
		final boolean flagCombination2 = this.flagCombination2CheckBox.isSelected();
		final boolean flagCombination3 = this.flagCombination3CheckBox.isSelected();
		final boolean flagCombination4 = this.flagCombination4CheckBox.isSelected();
		final boolean flagCombination5 = this.flagCombination5CheckBox.isSelected();
		final String[] flags = {
				input, output, scLine, scName, scOccurrence, scLength,
				scStartLine, scStartColumn, scEndLine, scEndColumn, scStartOffset, scEndOffset
		};
		final boolean[] flagCombinations = { flagCombination1, flagCombination2, flagCombination3, flagCombination4, flagCombination5 };

		if (!this.checkData(name, path, command, flags, flagCombinations))
			return;

		final Slicer outputSlicer = new Slicer(name, path, command, input, output, scLine, scName, scOccurrence, scLength,
									scStartLine, scStartColumn, scEndLine, scEndColumn, scStartOffset, scEndOffset,
									flagCombination1, flagCombination2, flagCombination3, flagCombination4, flagCombination5);
		final String title = "Validate command";
		final String header = "Have a look at the preview of the command. Is it correct?";
		final String previewCommand = outputSlicer.getCommandPreview();
		final int confirmation = JOptionPane.showConfirmDialog(null, header + "\n" + previewCommand, title, JOptionPane.YES_NO_OPTION);
		if (confirmation != JOptionPane.YES_OPTION)
			return;

		this.outputSlicer = outputSlicer;
		this.option = Option.Accept;
		this.exit();
	}
	private boolean checkData(String name, String path, String command, String[] flags, boolean[] flagCombinations)
	{
		if (!this.checkValidName(name))
			return false;
		if (!this.checkValidPath(path))
			return false;
		if (!this.checkValidCommand(command, flagCombinations))
			return false;
		if (!this.checkValidFlags(flags))
			return false;
		if (!this.checkRepeatedFlags(flags))
			return false;
		if (!this.checkFlagCombinations(flagCombinations))
			return false;
		return true;
	}
	private boolean checkValidName(String name)
	{
		final boolean nameEmpty = name.isEmpty();

		if (nameEmpty)
			JOptionPane.showMessageDialog(null, "The name cannot be empty");

		return !nameEmpty;
	}
	private boolean checkValidPath(String path)
	{
		final File file = new File(path);
		final boolean fileExists = file.exists();

		if (!fileExists)
			JOptionPane.showMessageDialog(null, "The path does not exist");

		return fileExists;
	}
	private boolean checkValidCommand(String command, boolean[] flagCombinations)
	{
		final boolean commandEmpty = command.isEmpty();

		if (commandEmpty)
			JOptionPane.showMessageDialog(null, "Enter a command to execute the slicer");

		boolean flagsUsed = true;
		final String inputFlag = this.inputTextField.getText();
		final String outputFlag = this.outputTextField.getText();
		final String lineFlag = this.scLineTextField.getText();
		final String nameFlag = this.scNameTextField.getText();
		final String occurrenceFlag = this.scOccurrenceTextField.getText();
		final String lengthFlag = this.scLengthTextField.getText();
		final String startLineFlag = this.scStartLineTextField.getText();
		final String startColumnFlag = this.scStartColumnTextField.getText();
		final String endLineFlag = this.scEndLineTextField.getText();
		final String endColumnFlag = this.scEndColumnTextField.getText();
		final String startOffsetFlag = this.scStartOffsetTextField.getText();
		final String endOffsetFlag = this.scEndOffsetTextField.getText();

		command = " " + command + " ";
		if ((command.indexOf(" {" + inputFlag + "} ") == -1 && command.indexOf(" [" + inputFlag + "] ") == -1) ||
			(command.indexOf(" {" + outputFlag + "} ") == -1 && command.indexOf(" [" + outputFlag + "] ") == -1))
			flagsUsed = false;
		if (flagCombinations[0] && (
			(command.indexOf(" {" + lineFlag + "} ") == -1 && command.indexOf(" [" + lineFlag + "] ") == -1) ||
			(command.indexOf(" {" + nameFlag + "} ") == -1 && command.indexOf(" [" + nameFlag + "] ") == -1) ||
			(command.indexOf(" {" + occurrenceFlag + "} ") == -1 && command.indexOf(" [" + occurrenceFlag + "] ") == -1)))
			flagsUsed = false;
		if (flagCombinations[1] && (
			(command.indexOf(" {" + startLineFlag + "} ") == -1 && command.indexOf(" [" + startLineFlag + "] ") == -1) ||
			(command.indexOf(" {" + startColumnFlag + "} ") == -1 && command.indexOf(" [" + startColumnFlag + "] ") == -1) ||
			(command.indexOf(" {" + endLineFlag + "} ") == -1 && command.indexOf(" [" + endLineFlag + "] ") == -1) ||
			(command.indexOf(" {" + endColumnFlag + "} ") == -1 && command.indexOf(" [" + endColumnFlag + "] ") == -1)))
			flagsUsed = false;
		if (flagCombinations[2] && (
			(command.indexOf(" {" + startLineFlag + "} ") == -1 && command.indexOf(" [" + startLineFlag + "] ") == -1) ||
			(command.indexOf(" {" + startColumnFlag + "} ") == -1 && command.indexOf(" [" + startColumnFlag + "] ") == -1) ||
			(command.indexOf(" {" + lengthFlag + "} ") == -1 && command.indexOf(" [" + lengthFlag + "] ") == -1)))
			flagsUsed = false;
		if (flagCombinations[3] && (
			(command.indexOf(" {" + startOffsetFlag + "} ") == -1 && command.indexOf(" [" + startOffsetFlag + "] ") == -1) ||
			(command.indexOf(" {" + endOffsetFlag + "} ") == -1 && command.indexOf(" [" + endOffsetFlag + "] ") == -1)))
			flagsUsed = false;
		if (flagCombinations[4] && (
			(command.indexOf(" {" + startOffsetFlag + "} ") == -1 && command.indexOf(" [" + startOffsetFlag + "] ") == -1) ||
			(command.indexOf(" {" + lengthFlag + "} ") == -1 && command.indexOf(" [" + lengthFlag + "] ") == -1)))
			flagsUsed = false;

		if (!flagsUsed)
			JOptionPane.showMessageDialog(null, "The command must contain all the flags of the flag combinations selected\nExample: ./slicer {" + inputFlag + "} {" + outputFlag + "} {" + lineFlag + "} {" + nameFlag + "} {" + occurrenceFlag + "}");

		return !commandEmpty && flagsUsed;
	}
	private boolean checkValidFlags(String[] flags)
	{
		for (String flag : flags)
		{
			if (flag.isEmpty())
				JOptionPane.showMessageDialog(null, "A flag cannot be empty");
			if (flag.contains(" "))
				JOptionPane.showMessageDialog(null, "A flag cannot contain any space");
			if (flag.contains("\t"))
				JOptionPane.showMessageDialog(null, "A flag cannot contain any tabulation");

			if (flag.isEmpty() || flag.contains(" ") || flag.contains("\t"))
				return false;
		}
		return true;
	}
	private boolean checkRepeatedFlags(String[] flags)
	{
		for (int flagIndex = 0; flagIndex < flags.length - 1; flagIndex++)
		{
			final String flag1 = flags[flagIndex];

			for (int flag2Index = flagIndex + 1; flag2Index < flags.length; flag2Index++)
			{
				final String flag2 = flags[flag2Index];

				if (flag1.equals(flag2))
				{
					JOptionPane.showMessageDialog(null, "The " + flag1 + " flag is repeated");
					return false;
				}
			}
		}

		return true;
	}
	private boolean checkFlagCombinations(boolean[] flagCombinations)
	{
		for (boolean flagCombination : flagCombinations)
			if (flagCombination)
				return true;

		JOptionPane.showMessageDialog(null, "At least one additional flag combination must be selected");
		return false;
	}
	private void cancel()
	{
		this.outputSlicer = null;
		this.option = Option.Cancel;
		this.exit();
	}
	private void delete()
	{
		this.outputSlicer = null;
		this.option = Option.Delete;
		this.exit();
	}
	private void exit()
	{
		this.dispose();
	}
	public Slicer getOutput()
	{
		return this.outputSlicer;
	}

	private void loadConfig()
	{
		final String title = this.inputSlicer == null ? "Add Program Slicer" : "Edit " + this.inputSlicer.getName();
		final String name = this.inputSlicer == null ? "Slicer" : this.inputSlicer.getName();
		final String path = this.inputSlicer == null ? "Path of the program slicer" : this.inputSlicer.getPath();
		final String command = this.inputSlicer == null ? "./slicer {ip} {op} {li} {na} {oc}" : this.inputSlicer.getCommand();
		final String input = this.inputSlicer == null ? "ip" : this.inputSlicer.getInput();
		final String output = this.inputSlicer == null ? "op" : this.inputSlicer.getOutput();
		final String scLine = this.inputSlicer == null ? "li" : this.inputSlicer.getSCLine();
		final String scName = this.inputSlicer == null ? "na" : this.inputSlicer.getSCName();
		final String scOccurrence = this.inputSlicer == null ? "oc" : this.inputSlicer.getSCOccurrence();
		final String scLength = this.inputSlicer == null ? "le" : this.inputSlicer.getSCLength();
		final String scStartLine = this.inputSlicer == null ? "sl" : this.inputSlicer.getSCStartLine();
		final String scStartColumn = this.inputSlicer == null ? "sc" : this.inputSlicer.getSCStartColumn();
		final String scEndLine = this.inputSlicer == null ? "el" : this.inputSlicer.getSCEndLine();
		final String scEndColumn = this.inputSlicer == null ? "ec" : this.inputSlicer.getSCEndColumn();
		final String scStartOffset = this.inputSlicer == null ? "so" : this.inputSlicer.getSCStartOffset();
		final String scEndOffset = this.inputSlicer == null ? "eo" : this.inputSlicer.getSCEndOffset();
		final boolean flagCombination1 = this.inputSlicer == null ? true : this.inputSlicer.getFlagCombination1();
		final boolean flagCombination2 = this.inputSlicer == null ? false : this.inputSlicer.getFlagCombination2();
		final boolean flagCombination3 = this.inputSlicer == null ? false : this.inputSlicer.getFlagCombination3();
		final boolean flagCombination4 = this.inputSlicer == null ? false : this.inputSlicer.getFlagCombination4();
		final boolean flagCombination5 = this.inputSlicer == null ? false : this.inputSlicer.getFlagCombination5();
		final String accept = this.inputSlicer == null ? "Add slicer" : "Accept";

		this.setTitle(title);
		this.nameTextField.setText(name);
		this.pathButton.setText(path);
		this.commandTextField.setText(command);
		this.inputTextField.setText(input);
		this.outputTextField.setText(output);
		this.scLineTextField.setText(scLine);
		this.scNameTextField.setText(scName);
		this.scOccurrenceTextField.setText(scOccurrence);
		this.scLengthTextField.setText(scLength);
		this.scStartLineTextField.setText(scStartLine);
		this.scStartColumnTextField.setText(scStartColumn);
		this.scEndLineTextField.setText(scEndLine);
		this.scEndColumnTextField.setText(scEndColumn);
		this.scStartOffsetTextField.setText(scStartOffset);
		this.scEndOffsetTextField.setText(scEndOffset);
		this.flagCombination1CheckBox.setSelected(flagCombination1);
		this.flagCombination2CheckBox.setSelected(flagCombination2);
		this.flagCombination3CheckBox.setSelected(flagCombination3);
		this.flagCombination4CheckBox.setSelected(flagCombination4);
		this.flagCombination5CheckBox.setSelected(flagCombination5);
		this.acceptButton.setText(accept);
	}
	private void loadPath()
	{
		final JFileChooser fileChooser = new JFileChooser();

		fileChooser.setFileFilter(null);
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		switch (fileChooser.showOpenDialog(this))
		{
			case JFileChooser.APPROVE_OPTION:
				final File slicerFile = fileChooser.getSelectedFile();
				this.pathButton.setText(slicerFile.getAbsolutePath());
				break;
			case JFileChooser.CANCEL_OPTION:
			default:
				break;
		}
	}

	private void replaceFlag(String oldFlag, String newFlag)
	{
		final String command = this.commandTextField.getText();
		final String newCommand1 = command.replace("{" + oldFlag + "}", "{" + newFlag + "}");
		final String newCommand2 = newCommand1.replace("[" + oldFlag + "]", "[" + newFlag + "]");

		this.commandTextField.setText(newCommand2);
		this.updateFlags();
	}
	private void updateFlags()
	{
		final String inputFlag = "-" + this.inputTextField.getText();
		final String outputFlag = "-" + this.outputTextField.getText();
		final String lineFlag = "-" + this.scLineTextField.getText();
		final String nameFlag = "-" + this.scNameTextField.getText();
		final String occurrenceFlag = "-" + this.scOccurrenceTextField.getText();
		final String lengthFlag = "-" + this.scLengthTextField.getText();
		final String startLineFlag = "-" + this.scStartLineTextField.getText();
		final String startColumnFlag = "-" + this.scStartColumnTextField.getText();
		final String endLineFlag = "-" + this.scEndLineTextField.getText();
		final String endColumnFlag = "-" + this.scEndColumnTextField.getText();
		final String startOffsetFlag = "-" + this.scStartOffsetTextField.getText();
		final String endOffsetFlag = "-" + this.scEndOffsetTextField.getText();

		final String srcPath = inputFlag + " srcPath";
		final String dstPath = outputFlag + " dstPath";
		final String line = lineFlag + " 42";
		final String name = nameFlag + " VarX";
		final String occurrence = occurrenceFlag + " 3";
		final String length = lengthFlag + " 4";
		final String startLine = startLineFlag + " 42";
		final String startColumn = startColumnFlag + " 12";
		final String endLine = endLineFlag + " 42";
		final String endColumn = endColumnFlag + " 16";
		final String startOffset = startOffsetFlag + " 123";
		final String endOffset = endOffsetFlag + " 127";
		final String flagCombination0 = srcPath + " " + dstPath;
		final String flagCombination1 = line + " " + name + " " + occurrence;
		final String flagCombination2 = startLine + " " + startColumn + " " + endLine + " " + endColumn;
		final String flagCombination3 = startLine + " " + startColumn + " " + length;
		final String flagCombination4 = startOffset + " " + endOffset;
		final String flagCombination5 = startOffset + " " + length;

		this.flagCombination0CheckBox.setText(flagCombination0);
		this.flagCombination1CheckBox.setText(flagCombination1);
		this.flagCombination2CheckBox.setText(flagCombination2);
		this.flagCombination3CheckBox.setText(flagCombination3);
		this.flagCombination4CheckBox.setText(flagCombination4);
		this.flagCombination5CheckBox.setText(flagCombination5);
	}
}