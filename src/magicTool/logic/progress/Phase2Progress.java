package magicTool.logic.progress;

public class Phase2Progress extends Progress
{
	private final int xAtATime;
	private final int successes;
	private final int current;
	private final int maximum;

	public Phase2Progress(int xAtATime, int successes, int current, int maximum, int time, double reduction)
	{
		super(time, reduction);

		this.xAtATime = xAtATime;
		this.successes = successes;
		this.current = current;
		this.maximum = maximum;
	}

	public int getXAtATime()
	{
		return this.xAtATime;
	}
	public int getSuccesses()
	{
		return this.successes;
	}
	public int getCurrent()
	{
		return this.current;
	}
	public int getMaximum()
	{
		return this.maximum;
	}
}