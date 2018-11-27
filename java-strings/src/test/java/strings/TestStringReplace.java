package strings;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestStringReplace {

	public String myString;
	public int N = 1000;
	public static long stringDuration;
	public static long map1Duration;
	public static long map2Duration;

	public Map<String, String> map = new HashMap<String, String>();

	public boolean setUpIsDone = false;

	@BeforeEach
	public void setUp() {
		if (setUpIsDone) {
			return;
		}
		// build really long string
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < N; i++) {
			sb.append("<h".concat("" + i + "").concat(">"));
			// init map
			map.put("<h".concat("" + i + "").concat(">"), "<this is new" + i + ">");
		}
		myString = sb.toString();
		setUpIsDone = true;
	}

	@Test
	public void testStringReplace() {
		long start = System.nanoTime();
		String result = "";
		for (int i = 0; i < N; i++) {
			result += myString.replace("<h".concat("" + i + "").concat(">"), "<this is new" + i + ">");
		}
		long end = System.nanoTime();
		stringDuration = end - start;
		System.out.println("String replace:" + stringDuration + " nano seconds");
//		System.out.println("String replace:" + (end - start)/1000000 + " milli seconds");

	}

	@Test
	public void testMapReplace1() {
		long start = System.nanoTime();

		String result = JavaStrings.replacePattern(myString, map, '<');
		long end = System.nanoTime();
		map1Duration = end - start;
		System.out.println("Map1 replace:" + map1Duration + " nano seconds");
//		System.out.println("Map1 replace:" + (end - start)/1000000 + " milli seconds");
	}

	@Test
	public void testMapReplace2() {
		long start = System.nanoTime();

		String result = JavaStrings.replaceFromMap(myString, map);
		long end = System.nanoTime();
		map2Duration = end - start;
		System.out.println("Map2 replace:" + map2Duration + " nano seconds");
//		System.out.println("Map2 replace:" + (end - start)/1000000 + " milli seconds");
	}

	@AfterAll
	public static void terminate() {
		System.out.println("StringReplace takes " + (double)(stringDuration)/((double)map1Duration) + "x longer tan map1Replace");
		System.out.println("Map1 Replace takes " + (double)(map1Duration)/(double)(map2Duration) + "x longer than map2Replace");
	}
	
}
