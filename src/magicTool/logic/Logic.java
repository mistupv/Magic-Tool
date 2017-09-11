package magicTool.logic;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import magicTool.config.Config;
import magicTool.logic.process.Process;
import magicTool.logic.process.ProcessListener;
import magicTool.logic.progress.Phase1Progress;
import magicTool.logic.progress.Phase2Progress;
import misc.Misc;

public class Logic
{
	private static Logic logic = new Logic();
	public static Logic getLogic()
	{
		return Logic.logic;
	}

	private final Config config = Config.getConfig();

	private Logic()
	{
		this.loadSlicers();
	}
	public void loadSlicers()
	{
		final String configurationPath = this.config.getConfigurationPath();
		final String slicersPath = configurationPath + "Slicers.txt";
		final File slicersFile = new File(slicersPath);
		if (!slicersFile.exists())
			return;

		final List<String> lines = Misc.readLines(slicersPath);

		for (String line : lines)
		{
			final StringTokenizer st = new StringTokenizer(line, "@");
			final String name = st.nextToken().trim();
			final String path = st.nextToken().trim();
			final String command = st.nextToken().trim();
			final String input = st.nextToken().trim();
			final String output = st.nextToken().trim();
			final String scLine = st.nextToken().trim();
			final String scName = st.nextToken().trim();
			final String scOccurrence = st.nextToken().trim();
			final String scLength = st.nextToken().trim();
			final String scStartLine = st.nextToken().trim();
			final String scStartColumn = st.nextToken().trim();
			final String scEndLine = st.nextToken().trim();
			final String scEndColumn = st.nextToken().trim();
			final String scStartOffset = st.nextToken().trim();
			final String scEndOffset = st.nextToken().trim();
			final boolean flagCombination1 = Boolean.parseBoolean(st.nextToken().trim());
			final boolean flagCombination2 = Boolean.parseBoolean(st.nextToken().trim());
			final boolean flagCombination3 = Boolean.parseBoolean(st.nextToken().trim());
			final boolean flagCombination4 = Boolean.parseBoolean(st.nextToken().trim());
			final boolean flagCombination5 = Boolean.parseBoolean(st.nextToken().trim());

			this.slicers.add(new Slicer(name, path, command, input, output,
					scLine, scName, scOccurrence, scLength, scStartLine, scStartColumn, scEndLine, scEndColumn, scStartOffset, scEndOffset,
					flagCombination1, flagCombination2, flagCombination3, flagCombination4, flagCombination5));
		}
	}
	public void saveSlicers()
	{
		String text = "";
		final String configurationPath = this.config.getConfigurationPath();
		final String slicersPath = configurationPath + "Slicers.txt";
		final String lineSeparator = System.lineSeparator();

		for (Slicer slicer : this.slicers)
		{
			text += slicer.getName() + " @ ";
			text += slicer.getPath() + " @ ";
			text += slicer.getCommand() + " @ ";
			text += slicer.getInput() + " @ ";
			text += slicer.getOutput() + " @ ";
			text += slicer.getSCLine() + " @ ";
			text += slicer.getSCName() + " @ ";
			text += slicer.getSCOccurrence() + " @ ";
			text += slicer.getSCLength() + " @ ";
			text += slicer.getSCStartLine() + " @ ";
			text += slicer.getSCStartColumn() + " @ ";
			text += slicer.getSCEndLine() + " @ ";
			text += slicer.getSCEndColumn() + " @ ";
			text += slicer.getSCStartOffset() + " @ ";
			text += slicer.getSCEndOffset() + " @ ";
			text += slicer.getFlagCombination1() + " @ ";
			text += slicer.getFlagCombination2() + " @ ";
			text += slicer.getFlagCombination3() + " @ ";
			text += slicer.getFlagCombination4() + " @ ";
			text += slicer.getFlagCombination5() + lineSeparator;
		}

		Misc.write(slicersPath, text, false);
	}

	public void addProcessListener(ProcessListener processListener)
	{
		this.processListeners.add(processListener);
	}

	public void notifyProcessStarted()
	{
		for (ProcessListener processListener : this.processListeners)
			processListener.processStarted();
	}
	public void notifyProcessUpdated()
	{
		for (ProcessListener processListener : this.processListeners)
			processListener.processUpdated();
	}
	public void notifyProcessFinished()
	{
		for (ProcessListener processListener : this.processListeners)
			processListener.processFinished();
	}

	// Slicing
	private final List<Slicer> slicers = new LinkedList<Slicer>();
	private SlicingCriterion slicingCriterion;

	public List<Slicer> getSlicers()
	{
		return new LinkedList<Slicer>(this.slicers);
	}
	public SlicingCriterion getSlicingCriterion()
	{
		return this.slicingCriterion;
	}

	public void addSlicer(int index, Slicer slicer)
	{
		this.slicers.add(index, slicer);
	}
	public void setSlicingCriterion(SlicingCriterion slicingCriterion)
	{
		this.slicingCriterion = slicingCriterion;
	}

	public void removeSlicer(Slicer slicer)
	{
		this.slicers.remove(slicer);
	}

	// Process
	private final List<ProcessListener> processListeners = new LinkedList<ProcessListener>();
	private final List<Phase1Progress> phase1Progress = new LinkedList<Phase1Progress>();
	private final List<Phase2Progress> phase2Progress = new LinkedList<Phase2Progress>();
	private Process process;

	public List<Phase1Progress> getPhase1Progress()
	{
		return new LinkedList<Phase1Progress>(this.phase1Progress);
	}
	public List<Phase2Progress> getPhase2Progress()
	{
		return new LinkedList<Phase2Progress>(this.phase2Progress);
	}

	public void addPhase1Progress(Slicer slicer, long time, double reduction)
	{
		this.phase1Progress.add(new Phase1Progress(slicer, time, reduction));
	}
	public void addPhase2Progress(int xAtATime, int successes, int current, int maximum, int time, double reduction)
	{
		final Phase2Progress progress = this.getPhase2Progress(xAtATime);
		final int progressIndex = progress != null ? this.phase2Progress.indexOf(progress) : this.phase2Progress.size();

		this.phase2Progress.remove(progress);
		this.phase2Progress.add(progressIndex, new Phase2Progress(xAtATime, successes, current, maximum, time, reduction));
	}
	private Phase2Progress getPhase2Progress(int xAtATime)
	{
		for (Phase2Progress progress : this.phase2Progress)
		{
			final int progressXAtATime = progress.getXAtATime();

			if (progressXAtATime == xAtATime)
				return progress;
		}
		return null;
	}

	public synchronized void startProcess()
	{
		this.stopProcess();
		this.resetProcess();

		this.process = new Process();
		this.process.start();
	}
	private void resetProcess()
	{
		this.phase1Progress.clear();
		this.phase2Progress.clear();
	}
	public synchronized void stopProcess()
	{
		if (this.process == null)
			return;

		try
		{
			this.process.end();
			this.process.join();
			this.process = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}