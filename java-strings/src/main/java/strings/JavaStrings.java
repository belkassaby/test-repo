package strings;

import java.util.Map;
import java.util.Map.Entry;

public class JavaStrings {

	/**
	 * Method using a Map for in-String replacements
	 * 
	 * Found on https://stackoverflow.com/questions/26735276/alternative-to-successive-string-replace
	 * 
	 * @param src
	 *         source string
	 * @param map
	 *         map of key-values pairs where keys replace the corresponding value
	 * @return replaced string
	 */
	public static String replacePattern(String src, Map<String, String> map, char character) {
		StringBuilder sb = new StringBuilder(src.length() + src.length() / 2);

		for (int pos = 0;;) {
			int ltIdx = src.indexOf(character, pos);
			if (ltIdx < 0) {
				// No more '<', we're done:
				sb.append(src, pos, src.length());
				return sb.toString();
			}

			sb.append(src, pos, ltIdx); // Copy chars before '$'
			// Check if our hit is replaceable:
			boolean mismatch = true;
			for (Entry<String, String> e : map.entrySet()) {
				String key = e.getKey();
				if (src.regionMatches(ltIdx, key, 0, key.length())) {
					// Match, append the replacement:
					sb.append(e.getValue());
					pos = ltIdx + key.length();
					mismatch = false;
					break;
				}
			}
			if (mismatch) {
				sb.append(character);
				pos = ltIdx + 1;
			}
		}
	}

	/**
	 * Replaces all occurrences of keys of the given map in the given string
	 * with the associated value in that map.
	 * 
	 * This method is semantically the same as calling
	 * {@link String#replace(CharSequence, CharSequence)} for each of the
	 * entries in the map, but may be significantly faster for many replacements
	 * performed on a short string, since
	 * {@link String#replace(CharSequence, CharSequence)} uses regular
	 * expressions internally and results in many String object allocations when
	 * applied iteratively.
	 * 
	 * The order in which replacements are applied depends on the order of the
	 * map's entry set.
	 * 
	 * Method found on https://www.cqse.eu/en/blog/string-replace-performance/
	 */
	public static String replaceFromMap(String string, Map<String, String> replacements) {
		StringBuilder sb = new StringBuilder(string);
		for (Entry<String, String> entry : replacements.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();

			int start = sb.indexOf(key, 0);
			while (start > -1) {
				int end = start + key.length();
				int nextSearchStart = start + value.length();
				sb.replace(start, end, value);
				start = sb.indexOf(key, nextSearchStart);
			}
		}
		return sb.toString();
	}
}
