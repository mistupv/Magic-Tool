package magicTool.logic.progress;

public class Progress
{
	private final long time;
	private final double reduction;

	protected Progress(long time, double reduction)
	{
		this.time = time;
		this.reduction = reduction;
	}

	public long getTime()
	{
		return this.time;
	}
	public double getReduction()
	{
		return this.reduction;
	}
}