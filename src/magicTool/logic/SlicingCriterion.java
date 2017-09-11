package magicTool.logic;

import java.awt.Point;

public class SlicingCriterion extends edg.slicing.SlicingCriterion
{
	protected final int column;
	protected final int offset;

	public SlicingCriterion(int line, String name, int column, int offset)
	{
		this(null, line, name, 1, column, offset);
	}
	public SlicingCriterion(int line, String name, int occurrence, int column, int offset)
	{
		this(null, line, name, occurrence, column, offset);
	}
	public SlicingCriterion(String archive, int line, String name, int column, int offset)
	{
		this(archive, line, name, 1, column, offset);
	}
	public SlicingCriterion(String archive, int line, String name, int occurrence, int column, int offset)
	{
		super(archive, line, name, occurrence);

		this.column = column;
		this.offset = offset;
	}

	public Point getStartPoint()
	{
		return new Point(this.column, super.line);
	}
	public Point getEndPoint()
	{
		return new Point(this.column + this.getLength(), super.line);
	}
	public int getStartOffset()
	{
		return this.offset;
	}
	public int getEndOffset()
	{
		return this.offset + this.getLength();
	}
	public int getLength()
	{
		return super.name.length();
	}

	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!(obj instanceof SlicingCriterion))
			return false;

		final SlicingCriterion sc = (SlicingCriterion) obj;

		if (this.line != sc.line)
			return false;
		if (!this.name.equals(sc.name))
			return false;
		if (this.occurrence != sc.occurrence)
			return false;
		if (this.column != sc.column)
			return false;
		if (this.offset != sc.offset)
			return false;
		return true;
	}
	public String toString()
	{
		return "[" + super.line + ", " + super.name + ", " + super.occurrence + ", " + this.column + ", " + this.offset + "]";
	}
}