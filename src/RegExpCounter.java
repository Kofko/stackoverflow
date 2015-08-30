/**
 * http://stackoverflow.com/questions/27458041/java-string-matching-the-regex
 * <p/>
 * Given a string and a Regular Expression pattern, give the number of the times the pattern occurs in the string.
 */
public class RegExpCounter {
    public static void main(String[] args) throws Exception {
        String str = "aaaaaannndnnnnnnfffhfhhgjjjwkkkllclc";
        String pattern = "a*n.";
        int cnt = 0;
        for (int i = 0; i < str.length(); ++i) {
            if (startsWithPattern(str.substring(i), pattern)) {
                ++cnt;
            }
        }
        System.out.println(cnt);
    }

    private static boolean startsWithPattern(String str, String pattern) {
        int strPos = 0; // current position in str
        char prevChar = 0; // previous character in pattern, 0 at start
        for (char c : pattern.toCharArray()) {
            switch (c) {
                case '.':
                    // check 1 char in str (one has been already checked)
                    if (!checkNChars(str, prevChar, strPos, 2 - 1)) {
                        return false;
                    }
                    strPos += 2 - 1;
                    break;
                case '*':
                    // check 5 chars in str (one has been already checked)
                    if (!checkNChars(str, prevChar, strPos, 6 - 1)) {
                        return false;
                    }
                    strPos += 6 - 1;
                    // skip all prevChars
                    while (strPos < str.length() && str.charAt(strPos) == prevChar) {
                        ++strPos;
                    }
                    break;
                case '+':
                    // check 3 chars in str (one has been already checked)
                    if (!checkNChars(str, prevChar, strPos, 4 - 1)) {
                        return false;
                    }
                    strPos += 4 - 1;
                    break;
                default:
                    // current char is not a '.', '+' or '*'
                    // check one char in str and update prevChar
                    if (!checkNChars(str, c, strPos, 1)) {
                        return false;
                    }
                    strPos += 1;
                    prevChar = c;
                    break;
            }
        }
        return true; // str matches pattern
    }

    /**
     * checks that `N` chars in `str` from `start` is `c`
     */
    private static boolean checkNChars(String str, char c, int start, int N) {
        if (start + N >= str.length()) {
            return false;
        }
        for (int i = start; i < start + N; ++i) {
            if (str.charAt(i) != c) {
                return false;
            }
        }
        return true;
    }
}
