package magicTool.logic.mapper;

import java.util.Map;

import edg.graph.EDG;
import edg.graph.Node;
import eknife.EDGFactory;
import eknife.EKnife.Language;
import magicTool.logic.SlicingCriterion;
import magicTool.misc.Miscellanea;

public class SlicingCriterionMapper
{
	private final String srcProgramPath;
	private final String dstProgramPath;
	private final SlicingCriterion srcSC;

	public SlicingCriterionMapper(String srcProgramPath, String dstProgramPath, SlicingCriterion srcSC)
	{
		this.srcProgramPath = srcProgramPath;
		this.dstProgramPath = dstProgramPath;
		this.srcSC = srcSC;
	}

	public SlicingCriterion map()
	{
		final EDG srcEDG = EDGFactory.createEDG(Language.Erlang, this.srcProgramPath, false);
		final EDG dstEDG = EDGFactory.createEDG(Language.Erlang, this.dstProgramPath, false);
		final EDGMapping mapping = new EDGMapping(srcEDG, dstEDG);
		final Map<Node, Node> map = mapping.map();

		final Node srcSC = Miscellanea.getNode(srcEDG, this.srcSC);
		final Node dstSC = map.get(srcSC);

		return Miscellanea.getSlicingCriterion(dstEDG, dstSC, this.dstProgramPath);
	}
}