import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * http://stackoverflow.com/questions/32216179/check-if-binary-string-can-be-partitioned-such-that-each-partition-is-a-power-of
 * <p/>
 * Given a binary string, check if we can partition/split the string into 0..n parts such that each part is a power of 5. Return the minimum number of splits, if it can be done.
 */
public class BinaryStringPartition {
    private List<String> powers;
    private int bestSplit;

    public static void main(String[] args) {
        String input = "110000110101101100101010000001011111001";
        int bestSplit = new BinaryStringPartition().split(input);
        System.out.printf("Best split for %s: %d\n", input, bestSplit);
    }

    public int split(String input) {
        // prepare data
        powers = getListOfPowers(input);
        Collections.reverse(powers); // simple heuristics, sort powers in decreasing order
        bestSplit = -1;
        // make split
        recursiveSplit(input, 0, -1);
        return bestSplit;
    }

    /**
     * @param input input binary string
     * @return list of powers that fit to input string
     */
    public static List<String> getListOfPowers(String input) {
        List<String> powers = new ArrayList<String>();
        for (long pow = 5; ; pow *= 5) {
            String powStr = Long.toBinaryString(pow);
            if (powStr.length() <= input.length()) { // can be fit in input string
                powers.add(powStr);
            } else {
                return powers;
            }
        }
    }

    private void recursiveSplit(String inp, int start, int depth) {
        if (depth >= bestSplit && bestSplit != -1) {
            return;
        }
        if (start == inp.length()) { // split complete
            bestSplit = depth;
        } else { // try to split the rest of the input
            for (String pow : powers) {
                if (inp.startsWith(pow, start)) {
                    recursiveSplit(inp, start + pow.length(), depth + 1);
                }
            }
        }
    }
}