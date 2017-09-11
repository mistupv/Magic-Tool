package magicTool.logic.progress;

import magicTool.logic.Slicer;

public class Phase1Progress extends Progress
{
	private final Slicer slicer;

	public Phase1Progress(Slicer slicer, long time, double reduction)
	{
		super(time, reduction);

		this.slicer = slicer;
	}

	public Slicer getSlicer()
	{
		return this.slicer;
	}
}