package magicTool.misc;

import java.awt.Point;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import edg.LDASTNodeInfo;
import edg.graph.EDG;
import edg.graph.Node;
import edg.graph.NodeInfo;
import edg.traverser.EDGTraverser;
import magicTool.logic.SlicingCriterion;
import misc.Misc;

public class Miscellanea
{
	public static enum Language { Erlang }

	public static Node getNode(EDG edg, SlicingCriterion sc)
	{
		if (edg == null || sc == null)
			return null;

		final int scLine = sc.getLine();
		final String scName = sc.getName();
		final List<Node> nodes = edg.findNodesByData(null, new Comparator<NodeInfo>() {
			public int compare(NodeInfo o1, NodeInfo o2)
			{
				final LDASTNodeInfo ldNodeInfo = o2.getInfo();
				if (ldNodeInfo == null)
					return -1;
				if (scLine != ldNodeInfo.getLine())
					return -1;
				if (!scName.equals(o2.getName()))
					return -1;
				return 0;
			}
		});
		final int scOccurrence = sc.getOccurrence();
		if (nodes.isEmpty())
			return null;
		final Node node = nodes.get(scOccurrence - 1);
		return EDGTraverser.getResult(node);
	}
	public static SlicingCriterion getSlicingCriterion(EDG edg, Node SC, String programPath)
	{
		if (edg == null || SC == null)
			return null;

		SC = EDGTraverser.getSibling(SC, 0);
		final int scLine = (int) SC.getData().getInfo().getLine();
		final String scName = SC.getData().getName();
		final List<Node> nodes = edg.findNodesByData(null, new Comparator<NodeInfo>() {
			public int compare(NodeInfo o1, NodeInfo o2)
			{
				final LDASTNodeInfo ldNodeInfo = o2.getInfo();
				if (ldNodeInfo == null)
					return -1;
				if (scLine != ldNodeInfo.getLine())
					return -1;
				if (!scName.equals(o2.getName()))
					return -1;
				return 0;
			}
		});
		final int scOccurrence = nodes.indexOf(SC) + 1;

		final List<String> lines = Misc.readLines(programPath);
		final String line = lines.get(scLine - 1);
		final int column = magicTool.misc.Miscellanea.getColumn(magicTool.misc.Miscellanea.Language.Erlang, line, scOccurrence, scName);
		final int offset = Misc.getOffset(lines, new Point(column, scLine));

		return new SlicingCriterion(scLine, scName, scOccurrence, column, offset);
	}

	public static int getOccurrence(Language language, String line, int column, String name)
	{
		final String[] delimiters = Miscellanea.getDelimiters(language);
		final List<String> tokens = Miscellanea.getTokens(line, delimiters);

		int index = 0;
		int occurrence = 0;
		for (String token : tokens)
		{
			index = line.indexOf(token, index) + token.length();
			if (!name.equals(token))
				continue;

			occurrence++;
			if (index >= column)
				return occurrence;
		}

		return occurrence;
	}
	public static int getColumn(Language language, String line, int occurrence, String name)
	{
		final String[] delimiters = Miscellanea.getDelimiters(language);
		final List<String> tokens = Miscellanea.getTokens(line, delimiters);

		int index = 0;
		int currOccurrence = 0;
		for (String token : tokens)
		{
			index = line.indexOf(token, index) + token.length();
			if (!name.equals(token))
				continue;

			currOccurrence++;
			if (currOccurrence == occurrence)
				return index + 1 - token.length();
		}

		return -1;
	}
	public static String[] getDelimiters(Language language)
	{
		switch (language)
		{
			case Erlang:
				return new String[] {
						" ", "\t", "\n",										// Separators 3
						"=:=",													// Operators 3
						"->", "==",												// Operators 2
						"#{",													// Separators 2
						"=", "+", "-", "*", "/", "!",							// Operators
						".", ",", "{", "}", "[", "]", "(", ")", "|", ":", ";"	// Separators
				};
			default:
				throw new RuntimeException("Language not contemplated: " + language);
		}
	}
	public static List<String> getTokens(String text, String[] delimiters)
	{
		int index = 0;
		int textLength = text.length();
		final List<String> tokens = new LinkedList<String>();

		do
		{
			int bestIndex = Integer.MAX_VALUE;
			int bestDelimiterIndex = -1;

			for (int delimiterIndex = 0; delimiterIndex < delimiters.length; delimiterIndex++)
			{
				final String delimiter = delimiters[delimiterIndex];
				final int textIndex = text.indexOf(delimiter, index);

				if (textIndex != -1 && textIndex < bestIndex)
				{
					bestIndex = textIndex;
					bestDelimiterIndex = delimiterIndex;
				}
			}
			if (bestDelimiterIndex == -1)
				break;

			tokens.add(text.substring(index, bestIndex));
			index = bestIndex + delimiters[bestDelimiterIndex].length();
		}
		while (true);

		tokens.add(text.substring(index, textLength));

		return tokens;
	}
}