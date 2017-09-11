import java.util.*;

/**
 * https://stackoverflow.com/questions/45952045/how-to-approximate-the-xth-percentile-for-a-large-unknown-quantity-of-number
 */
public class ApproximatePercentile {
    public static void main(String[] args) {
        // create random stream of numbers
        Random random = new Random(0);
        List<Integer> stream = new ArrayList<Integer>();
        for (int i = 0; i < 100000; ++i) {
            stream.add((int) (random.nextGaussian() * 100 + 30));
        }
        // get approximate percentile
        int k = 1000; // sample size
        int x = 50; // percentile
        // init priority queue for sampling
        TreeMap<Double, Integer> queue = new TreeMap<Double, Integer>();
        // sample k elements from stream
        for (int val : stream) {
            queue.put(random.nextDouble(), val);
            if (queue.size() > k) {
                queue.pollFirstEntry();
            }
        }
        // get xth percentile from k samples
        List<Integer> sample = new ArrayList<Integer>(queue.values());
        Collections.sort(sample);
        int approxPercent = sample.get(sample.size() * x / 100);
        System.out.println("Approximate percentile: " + approxPercent);
        // get real value of the xth percentile
        Collections.sort(stream);
        int percent = stream.get(stream.size() * x / 100);
        System.out.println("Real percentile: " + percent);
    }
}
