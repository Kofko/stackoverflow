import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

/**
 * http://stackoverflow.com/questions/32216179/check-if-binary-string-can-be-partitioned-such-that-each-partition-is-a-power-of
 * <p/>
 * Given a binary string, check if we can partition/split the string into 0..n parts such that each part is a power of 5. Return the minimum number of splits, if it can be done.
 */
public class BinaryStringPartition2 {
    public static void main(String[] args) {
        String input = "110000110101101100101010000001011111001";
        int bestSplit = new BinaryStringPartition2().split(input);
        System.out.printf("Best split for %s: %d\n", input, bestSplit);
    }

    public int split(String input) {
        // get list of powers of 5
        List<String> powers = BinaryStringPartition.getListOfPowers(input);
        // construct graph
        int n = input.length();
        int[][] edges = new int[n][n];
        for (String pow : powers) {
            // iterate over occurrences of pow in input
            for (int from = input.indexOf(pow);
                 from >= 0;
                 from = input.indexOf(pow, from + 1)) {
                int size = ++edges[from][0]; // first element is the number of outgoing edges
                int to = from + pow.length();
                edges[from][size] = to;
            }
        }
        // find shortest path from 0 to n with BFS
        return findShortestPath(n, edges);
    }

    private int findShortestPath(int n, int[][] edges) {
        int[] distances = new int[n + 1];
        Queue<Integer> queue = new ArrayDeque<Integer>();
        queue.add(0); // start node
        while (!queue.isEmpty()) {
            int from = queue.poll();
            int size = edges[from][0];
            for (int i = 1; i <= size; ++i) {
                int to = edges[from][i];
                if (to == n) {
                    return distances[from]; // shortest path found
                }
                if (distances[to] == 0) { // unvisited node
                    distances[to] = distances[from] + 1;
                    queue.add(to);
                }
            }
        }
        return -1; // path not found
    }
}