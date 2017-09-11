package magicTool.logic.process;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edg.graph.EDG;
import edg.graph.Node;
import eknife.EDGFactory;
import eknife.EKnife.Language;
import magicTool.config.Config;
import magicTool.logic.Logic;
import magicTool.logic.Slicer;
import magicTool.logic.SlicingCriterion;
import magicTool.logic.mapper.EDGMapping;
import magicTool.logic.mapper.SlicingCriterionMapper;
import misc.Misc;
import misc.util.Flusher;

// Metodos
//this.logic.getSlicers();
//this.logic.getSlicingCriterion();
//this.logic.addPhase1Progress(slicer, time, reduction);
//this.logic.addPhase2Progress(xbyx, successes, current, maximum, time, reduction);
//this.logic.notifyProcessStarted();
//this.logic.notifyProcessUpdated();
//this.logic.notifyProcessFinished();

public class Process extends Thread
{
	private final Config config = Config.getConfig();
	private final Logic logic = Logic.getLogic();
	private List<Slicer> slicers;
	private boolean running;

	public void run()
	{
		this.running = true;
		this.slicers = this.logic.getSlicers();

		this.logic.notifyProcessStarted();
		this.phase1();
		this.phase2();
		this.logic.notifyProcessFinished();
	}
	public void end()
	{
		this.running = false;
	}

	// Phase 1
	private void phase1()
	{
		final String temporaryPath = this.config.getTemporaryPath();
		final String originalPath = temporaryPath + this.config.getSourceCode();
		final String completeSlicePath = temporaryPath + "CompleteSlice.txt";
		final String outputPath = temporaryPath + "CompleteSlice_temp.txt";
		final List<Slicer> pendingSlicers = new LinkedList<Slicer>(this.slicers);
		final List<Slicer> slicersDone = new LinkedList<Slicer>();
		SlicingCriterion slicingCriterion = this.logic.getSlicingCriterion();
		double bestReduction = 0.0;

		Misc.copyFile(new File(originalPath), new File(completeSlicePath), false);
		while (this.running && !pendingSlicers.isEmpty())
		{
			Misc.copyFile(new File(completeSlicePath), new File(outputPath), false);

			// Slice with the next slicer
			final Slicer slicer = pendingSlicers.remove(0);
			final long executionTime = this.executeSlicer(slicer, slicingCriterion, completeSlicePath, outputPath);

			// Adapt the slicing criterion for the next slice
			final SlicingCriterionMapper scd = new SlicingCriterionMapper(completeSlicePath, outputPath, slicingCriterion);
			slicingCriterion = scd.map();
			Misc.copyFile(new File(outputPath), new File(completeSlicePath), false);

			// Check progress
			final double reduction = this.calculateReduction(originalPath, completeSlicePath);
			this.logic.addPhase1Progress(slicer, executionTime, reduction);
			this.logic.notifyProcessUpdated();

			// Repeat until none slicer makes progress
			if (reduction > bestReduction)
			{
				bestReduction = reduction;
				pendingSlicers.addAll(slicersDone);
				slicersDone.clear();
			}
			slicersDone.add(slicer);
		}
	}
	private long executeSlicer(Slicer slicer, SlicingCriterion slicingCriterion, String inputPath, String outputPath)
	{
		try
		{
			final Runtime runtime = Runtime.getRuntime();
			final String command = slicer.getCommand(slicingCriterion, inputPath, outputPath);
			final String slicerPath = slicer.getPath();
			final File slicerFile = new File(slicerPath);
			final long startMilliseconds = System.currentTimeMillis();
			final java.lang.Process process = runtime.exec(new String[] { "/bin/sh", "-c", command }, null, slicerFile);

			new Flusher(process).start();
			process.waitFor();

			return System.currentTimeMillis() - startMilliseconds;
		}
		catch (IOException | InterruptedException e)
		{
			e.printStackTrace();
		}

		return 0;
	}
	private double calculateReduction(String originalPath, String slicePath)
	{
		final EDG originalEDG = EDGFactory.createEDG(Language.Erlang, originalPath, false);
		final EDG slicedEDG = EDGFactory.createEDG(Language.Erlang, slicePath, false);
		final List<Node> originalNodes = originalEDG.getNodes();
		final List<Node> slicedNodes = slicedEDG.getNodes();

		final EDGMapping mappingOO = new EDGMapping(originalEDG, originalEDG);
		final EDGMapping mappingSO = new EDGMapping(slicedEDG, originalEDG);
		final List<Node> originalFictitiousNodes = this.getFictitiousNodes(originalNodes, mappingOO.map());
		final List<Node> sliceFictitiousNodes = this.getFictitiousNodes(slicedNodes, mappingSO.map());

		final int originalRealNodeCount = originalNodes.size() - originalFictitiousNodes.size();
		final int sliceRealNodeCount = slicedNodes.size() - sliceFictitiousNodes.size();
		final double result = 100.0 - (sliceRealNodeCount * 100.0) / originalRealNodeCount;

		return Misc.round(result, 2);
	}
	private List<Node> getFictitiousNodes(List<Node> nodes, Map<Node, Node> map)
	{
		final List<Node> fictitious = new LinkedList<Node>();

		for (Node node : nodes)
			if (!map.containsKey(node))
				fictitious.add(node);

		return fictitious;
	}

	// Phase 2
	private void phase2()
	{
		if (!this.running)
			return;
		Misc.wait(1000);
		this.logic.addPhase2Progress(1, 6, 5, 42, 600, 600);
		this.logic.notifyProcessUpdated();
		if (!this.running)
			return;
		Misc.wait(1000);
		this.logic.addPhase2Progress(2, 2, 15, 102, 600, 600);
		this.logic.notifyProcessUpdated();
		if (!this.running)
			return;
		Misc.wait(1000);
		this.logic.addPhase2Progress(3, 1, 8, 222, 600, 600);
		this.logic.notifyProcessUpdated();
		if (!this.running)
			return;
		Misc.wait(1000);
		this.logic.addPhase2Progress(4, 0, 6, 70, 600, 600);
		this.logic.notifyProcessUpdated();
		if (!this.running)
			return;
		Misc.wait(1000);
		this.logic.addPhase2Progress(5, 1, 1, 42, 700, 600);
		this.logic.notifyProcessUpdated();
		if (!this.running)
			return;
		Misc.wait(1000);
		this.logic.addPhase2Progress(6, 1, 1, 42, 700, 600);
		this.logic.notifyProcessUpdated();
		if (!this.running)
			return;
		Misc.wait(1000);
		this.logic.addPhase2Progress(7, 1, 30, 42, 800, 600);
		this.logic.notifyProcessUpdated();
		if (!this.running)
			return;
		Misc.wait(1000);
		this.logic.addPhase2Progress(8, 1, 40, 42, 800, 600);
		this.logic.notifyProcessUpdated();
		if (!this.running)
			return;
		Misc.wait(1000);
		this.logic.addPhase2Progress(9, 1, 41, 42, 900, 600);
		this.logic.notifyProcessUpdated();
		if (!this.running)
			return;
		Misc.wait(1000);
		this.logic.addPhase2Progress(10, 1, 42, 42, 1000, 600);
		this.logic.notifyProcessUpdated();
		if (!this.running)
			return;
		Misc.wait(1000);
		this.logic.addPhase2Progress(11, 1, 42, 42, 1000, 600);
		this.logic.notifyProcessUpdated();
		if (!this.running)
			return;
		Misc.wait(1000);
		this.logic.addPhase2Progress(12, 1, 42, 42, 1000, 600);
		this.logic.notifyProcessUpdated();
	}
}