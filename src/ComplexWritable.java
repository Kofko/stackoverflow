import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

import java.io.*;
import java.util.Arrays;

/**
 * http://stackoverflow.com/questions/32178437/custom-object-as-value-for-mapper-output
 */
public class ComplexWritable {
    public static final class ObjExample implements WritableComparable<ObjExample> {
        public final Text s = new Text(); // wrapped String
        public final ArrayOfArrays objArray = new ArrayOfArrays();

        @Override
        public int compareTo(ObjExample o) {
            return s.compareTo(o.s);
        }

        @Override
        public void write(DataOutput dataOutput) throws IOException {
            s.write(dataOutput);
            objArray.write(dataOutput);
        }

        @Override
        public void readFields(DataInput dataInput) throws IOException {
            s.readFields(dataInput);
            objArray.readFields(dataInput);
        }

        /**
         * set new size to the inner array
         *
         * @param n new size
         */
        public void setSize(int n) {
            objArray.set(new IntArray[n]);
        }

        /**
         * set i-th element of the objArray to an array of primitives
         */
        public void setElement(int i, IntWritable... elements) {
            IntArray subArr = new IntArray();
            subArr.set(elements);
            objArray.get()[i] = subArr;
        }

        @Override
        public String toString() {
            return "ObjExample{" +
                    "s=" + s +
                    ", objArray=" + objArray +
                    '}';
        }
    }

    /**
     * array of primitives
     */
    public static final class IntArray extends ArrayWritable {
        public IntArray() {
            super(IntWritable.class);
        }

        @Override
        public String toString() {
            return Arrays.toString(toStrings());
        }
    }

    /**
     * array of arrays
     */
    public static final class ArrayOfArrays extends ArrayWritable {
        public ArrayOfArrays() {
            super(IntArray.class);
        }

        @Override
        public String toString() {
            return Arrays.toString(toStrings());
        }
    }

    public static void main(String[] args) throws IOException {
        // construction example
        ObjExample o = new ObjExample();
        o.s.set("hello");
        o.setSize(2);
        o.setElement(0, new IntWritable(0)); // single primitive
        o.setElement(1, new IntWritable(1), new IntWritable(2)); // array of primitives
        // write to byte array
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        DataOutputStream dataOut = new DataOutputStream(byteOut);
        o.write(dataOut);
        dataOut.close();
        // read fields
        ObjExample o1 = new ObjExample();
        o1.readFields(new DataInputStream(new ByteArrayInputStream(byteOut.toByteArray())));
        // print result
        System.out.println(o1);
    }
}
