import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * http://stackoverflow.com/questions/32313055/find-the-biggest-palindrome-in-multiple-integers
 * <p/>
 * I must write a java app, which find the biggest palindrome for the product of a predetermined amount of numbers (l) of a predetermined number of digits (n).
 */
public class FindBiggestPalindrome {
    /**
     * combination of numbers
     */
    private static class Comb implements Comparable<Comb> {
        public int nums[];
        public int prod;

        public Comb(int[] nums) {
            this.nums = Arrays.copyOf(nums, nums.length);
            this.prod = 1;
            for (int num : nums) {
                this.prod *= num;
            }
        }

        @Override
        public int compareTo(@Nonnull Comb o) {
            return o.prod - prod; // sort in descending order
        }

        public boolean isPalindrome() {
            String str = Integer.toString(prod);
            return StringUtils.reverse(str).equals(str);
        }

        @Override
        public String toString() {
            return "Comb{" +
                    "nums=" + Arrays.toString(nums) +
                    ", prod=" + prod +
                    '}';
        }
    }

    public static void main(String[] args) {
        int n = 3;
        int l = 3;
        System.out.println("Biggest palindrome: " + getBiggestPalindrome(n, l));
    }

    public static Comb getBiggestPalindrome(int n, int l) {
        int minNum = (int) Math.pow(10, n - 1);
        int maxNum = minNum * 10 - 1;
        // create start combination
        int[] startArr = new int[l];
        Arrays.fill(startArr, maxNum);
        Comb startComb = new Comb(startArr);
        // iterate through combinations in descending order of its' products
        PriorityQueue<Comb> q = new PriorityQueue<Comb>();
        q.add(startComb);
        while (!q.isEmpty()) {
            // get combination with biggest product
            Comb comb = q.poll();
            // skip all combinations with same product
            while (!q.isEmpty() && comb.prod == q.peek().prod) {
                q.poll();
            }
            // check if product is a palindrome
            if (comb.isPalindrome()) {
                return comb;
            }
            // add dominated combinations of numbers
            for (int i = 0; i < l; ++i) {
                if (comb.nums[i] > minNum) {
                    // create new combination with decreased number in position `i`
                    --comb.nums[i];
                    Comb newComb = new Comb(comb.nums);
                    ++comb.nums[i];
                    // newComb.prod < comb.prod
                    q.add(newComb);
                }
            }
        }
        return null;
    }
}