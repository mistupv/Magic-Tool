import java.io.File;
import java.util.LinkedList;
import java.util.List;

import magicTool.logic.SlicingCriterion;
import magicTool.logic.mapper.SlicingCriterionMapper;

public class TestMapping
{
	public static void main(String[] args)
	{
		final String path = "/Users/Fenix/Desktop/Slicing/BenchmarksPrueba/";
		final List<Object[]> tests = new LinkedList<Object[]>();

		tests.add(new Object[] { "B1",	new SlicingCriterion("b1.erl",		20, "C",         2,  942),			new SlicingCriterion("b1Tama.erl",		 4, "C",         5,   56) });
		tests.add(new Object[] { "B1",	new SlicingCriterion("b1Tama.erl",	 4, "C",         5,   60),			new SlicingCriterion("b1David.erl",		 4, "C",         5,   56) });
		tests.add(new Object[] { "B1",	new SlicingCriterion("b1.erl",		20, "C",         2,  942),			new SlicingCriterion("b1David.erl",		 4, "C",         5,   56) });
		tests.add(new Object[] { "B2",	new SlicingCriterion("b2.erl",		21, "C",         2,  983),			new SlicingCriterion("b2Tama.erl",		 4, "C",         5,   52) });
		tests.add(new Object[] { "B2",	new SlicingCriterion("b2Tama.erl",	 4, "C",         5,   56),			new SlicingCriterion("b2David.erl",		 5, "C",         5,   53) });
		tests.add(new Object[] { "B2",	new SlicingCriterion("b2.erl",		21, "C",         2,  983),			new SlicingCriterion("b2David.erl",		 5, "C",         5,   53) });
		tests.add(new Object[] { "B3",	new SlicingCriterion("b3.erl",		21, "C",         2, 1022),			new SlicingCriterion("b3Tama.erl",		 4, "C",         5,   54) });
		tests.add(new Object[] { "B3",	new SlicingCriterion("b3Tama.erl",	 4, "C",         5,   58),			new SlicingCriterion("b3David.erl",		 4, "C",         5,   54) });
		tests.add(new Object[] { "B3",	new SlicingCriterion("b3.erl",		21, "C",         2, 1022),			new SlicingCriterion("b3David.erl",		 4, "C",         5,   54) });
//		tests.add(new Object[] { "B4",	new SlicingCriterion("b4.erl",		25, "Abb",       2, 1284),			new SlicingCriterion("b4Tama.erl",		  , "Abb",        ,     ) });
//		tests.add(new Object[] { "B4",	new SlicingCriterion("b4Tama.erl",	  , "Abb",        ,     ),			new SlicingCriterion("b4David.erl",		 6, "Abb",       5,  101) });
		tests.add(new Object[] { "B4",	new SlicingCriterion("b4.erl",		25, "Abb",       2, 1284),			new SlicingCriterion("b4David.erl",		 6, "Abb",       5,  101) });
		tests.add(new Object[] { "B5",	new SlicingCriterion("b5.erl",		24, "C",         2, 1260),			new SlicingCriterion("b5Tama.erl",		 5, "C",         5,   53) });
		tests.add(new Object[] { "B5",	new SlicingCriterion("b5Tama.erl",	 5, "C",         5,   57),			new SlicingCriterion("b5David.erl",		 5, "C",         5,   53) });
		tests.add(new Object[] { "B5",	new SlicingCriterion("b5.erl",		24, "C",         2, 1260),			new SlicingCriterion("b5David.erl",		 5, "C",         5,   53) });
		tests.add(new Object[] { "B6",	new SlicingCriterion("b6.erl",		24, "C",         2, 1183),			new SlicingCriterion("b6Tama.erl",		 6, "C",         5,   56) });
		tests.add(new Object[] { "B6",	new SlicingCriterion("b6Tama.erl",	 6, "C",         5,   60),			new SlicingCriterion("b6David.erl",		 4, "C",         5,   54) });
		tests.add(new Object[] { "B6",	new SlicingCriterion("b6.erl",		24, "C",         2, 1183),			new SlicingCriterion("b6David.erl",		 4, "C",         5,   54) });
		tests.add(new Object[] { "B7",	new SlicingCriterion("b7.erl",		25, "D",         2, 1195),			new SlicingCriterion("b7Tama.erl",		 5, "D",         5,   55) });
		tests.add(new Object[] { "B7",	new SlicingCriterion("b7Tama.erl",	 5, "D",         5,   59),			new SlicingCriterion("b7David.erl",		 5, "D",         5,   55) });
		tests.add(new Object[] { "B7",	new SlicingCriterion("b7.erl",		25, "D",         2, 1195),			new SlicingCriterion("b7David.erl",		 5, "D",         5,   55) });
//		tests.add(new Object[] { "B8",	new SlicingCriterion("b8.erl",		27, "C",         2, 1058),			new SlicingCriterion("b8Tama.erl",		12, "C",         5,  150) });
//		tests.add(new Object[] { "B8",	new SlicingCriterion("b8Tama.erl",	12, "C",         5,  154),			new SlicingCriterion("b8David.erl",		11, "C",         5,  149) });
//		tests.add(new Object[] { "B8",	new SlicingCriterion("b8.erl",		27, "C",         2, 1058),			new SlicingCriterion("b8David.erl",		11, "C",         5,  149) });
//		tests.add(new Object[] { "B9",	new SlicingCriterion("b9.erl",		28, "D",         2, 1062),			new SlicingCriterion("b9Tama.erl",		11, "D",         5,  149) });
//		tests.add(new Object[] { "B9",	new SlicingCriterion("b9Tama.erl",	11, "D",         5,  153),			new SlicingCriterion("b9David.erl",		11, "D",         5,  149) });
//		tests.add(new Object[] { "B9",	new SlicingCriterion("b9.erl",		28, "D",         2, 1062),			new SlicingCriterion("b9David.erl",		11, "D",         5,  149) });
		tests.add(new Object[] { "B10",	new SlicingCriterion("b10.erl",		22, "Deposits",  3, 1045),			new SlicingCriterion("b10Tama.erl",		 6, "Deposits",  6,   62) });
		tests.add(new Object[] { "B10",	new SlicingCriterion("b10Tama.erl",	 6, "Deposits",  6,   66),			new SlicingCriterion("b10David.erl",	 4, "Deposits",  6,   60) });
		tests.add(new Object[] { "B10",	new SlicingCriterion("b10.erl",		22, "Deposits",  3, 1045),			new SlicingCriterion("b10David.erl",	 4, "Deposits",  6,   60) });
		tests.add(new Object[] { "B11",	new SlicingCriterion("b11.erl",		52, "A",         2, 1859),			new SlicingCriterion("b11Tama.erl",		36, "A",         5,  634) });
		tests.add(new Object[] { "B11",	new SlicingCriterion("b11Tama.erl",	36, "A",         5,  638),			new SlicingCriterion("b11David.erl",	36, "A",         5,  640) });
		tests.add(new Object[] { "B11",	new SlicingCriterion("b11.erl",		52, "A",         2, 1859),			new SlicingCriterion("b11David.erl",	36, "A",         5,  640) });
//		tests.add(new Object[] { "B12",	new SlicingCriterion("b12.erl",		58, "Shown",    14, 2204),			new SlicingCriterion("b12Tama.erl",		  , "Shown",      ,     ) });
//		tests.add(new Object[] { "B12",	new SlicingCriterion("b12Tama.erl",	  , "Shown",      ,     ),			new SlicingCriterion("b12David.erl",	56, "Shown",    14, 1198) });
//		tests.add(new Object[] { "B12",	new SlicingCriterion("b12.erl",		58, "Shown",    14, 2204),			new SlicingCriterion("b12David.erl",	56, "Shown",    14, 1198) });
//		tests.add(new Object[] { "B13",	new SlicingCriterion("b13.erl",		28, "DB",        5, 1127),			new SlicingCriterion("b13Tama.erl",		  , "DB",         ,     ) });
//		tests.add(new Object[] { "B13",	new SlicingCriterion("b13Tama.erl",	  , "DB",         ,     ),			new SlicingCriterion("b13David.erl",	12, "DB",        5,  148) });
		tests.add(new Object[] { "B13",	new SlicingCriterion("b13.erl",		28, "DB",        5, 1127),			new SlicingCriterion("b13David.erl",	12, "DB",        5,  148) });
//		tests.add(new Object[] { "B14",	new SlicingCriterion("b14.erl",		29, "BS",       14, 1428),			new SlicingCriterion("b14Tama.erl",		  , "BS",         ,     ) });
//		tests.add(new Object[] { "B14",	new SlicingCriterion("b14Tama.erl",	  , "BS",         ,     ),			new SlicingCriterion("b14David.erl",	 7, "BS",       13,  111) });
		tests.add(new Object[] { "B14",	new SlicingCriterion("b14.erl",		29, "BS",       14, 1428),			new SlicingCriterion("b14David.erl",	 7, "BS",       13,  111) });
//		tests.add(new Object[] { "B15",	new SlicingCriterion("b15.erl",		81, "A",         6, 2412),			new SlicingCriterion("b15Tama.erl",		  , "A",          ,     ) });
//		tests.add(new Object[] { "B15",	new SlicingCriterion("b15Tama.erl",	  , "A",          ,     ),			new SlicingCriterion("b15David.erl",	29, "A",        14,  539) });
		tests.add(new Object[] { "B15",	new SlicingCriterion("b15.erl",		81, "A",         6, 2412),			new SlicingCriterion("b15David.erl",	29, "A",        14,  539) });
		tests.add(new Object[] { "B16",	new SlicingCriterion("b16.erl",		31, "NewI",      9, 1244),			new SlicingCriterion("b16Tama.erl",		11, "NewI",     13,  144) });
		tests.add(new Object[] { "B16",	new SlicingCriterion("b16Tama.erl",	11, "NewI",     13, 148),			new SlicingCriterion("b16David.erl",	11, "NewI",     13,  144) });
		tests.add(new Object[] { "B16",	new SlicingCriterion("b16.erl",		31, "NewI",      9, 1244),			new SlicingCriterion("b16David.erl",	11, "NewI",     13,  144) });
		tests.add(new Object[] { "B17",	new SlicingCriterion("b17.erl",		33, "V",         2, 1193),			new SlicingCriterion("b17Tama.erl",		 7, "V",         5,   64) });
		tests.add(new Object[] { "B17",	new SlicingCriterion("b17Tama.erl",	 7, "V",         5,   68),			new SlicingCriterion("b17David.erl",	 6, "V",         5,   63) });
		tests.add(new Object[] { "B17",	new SlicingCriterion("b17.erl",		33, "V",         2, 1193),			new SlicingCriterion("b17David.erl",	 6, "V",         5,   63) });
		tests.add(new Object[] { "B18",	new SlicingCriterion("b18.erl",		34, "W",         2, 1215),			new SlicingCriterion("b18Tama.erl",		 6, "W",         5,   53) });
		tests.add(new Object[] { "B18",	new SlicingCriterion("b18Tama.erl",	 6, "W",         5,   57),			new SlicingCriterion("b18David.erl",	 5, "W",         5,   52) });
		tests.add(new Object[] { "B18",	new SlicingCriterion("b18.erl",		34, "W",         2, 1215),			new SlicingCriterion("b18David.erl",	 5, "W",         5,   52) });
		tests.add(new Object[] { "B19",	new SlicingCriterion("b19.erl",		35, "Z",        11, 1246),			new SlicingCriterion("b19Tama.erl",		16, "Z",        10,  284) });
		tests.add(new Object[] { "B19",	new SlicingCriterion("b19Tama.erl",	16, "Z",        10,  288),			new SlicingCriterion("b19David.erl",	15, "Z",        10,  283) });
		tests.add(new Object[] { "B19",	new SlicingCriterion("b19.erl",		35, "Z",        11, 1246),			new SlicingCriterion("b19David.erl",	15, "Z",        10,  283) });
//		tests.add(new Object[] { "B20",	new SlicingCriterion("b20.erl",		49, "Year",     12, 2770),			new SlicingCriterion("b20Tama.erl",		  , "Year",       ,     ) });
//		tests.add(new Object[] { "B20",	new SlicingCriterion("b20Tama.erl",	  , "Year",       ,     ),			new SlicingCriterion("b20David.erl",	81, "Year",      8, 2432) });
		tests.add(new Object[] { "B20", new SlicingCriterion("b20.erl",		49, "Year",     12, 2770),			new SlicingCriterion("b20David.erl",	81, "Year",      8, 2432) });

		for (Object[] test : tests)
		{
			final String programPath = path + test[0];
			final SlicingCriterion srcSC = (SlicingCriterion) test[1];
			final SlicingCriterion dstSC = (SlicingCriterion) test[2];
			final String srcProgramPath = programPath + File.separator + srcSC.getArchive();
			final String dstProgramPath = programPath + File.separator + dstSC.getArchive();
			final SlicingCriterionMapper scd = new SlicingCriterionMapper(srcProgramPath, dstProgramPath, srcSC);
			final SlicingCriterion dstSCObtained = scd.map();

			System.out.println((tests.indexOf(test) + 1) + ") " + test[0] + " & " + test[1] + " => " + dstSC + ", " + dstSCObtained + " => " + dstSC.equals(dstSCObtained));
		}
	}
}