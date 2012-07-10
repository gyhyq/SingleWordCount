package test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.VIntWritable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.io.WritableUtils;
import org.apache.hadoop.io.BooleanWritable.Comparator;
import org.apache.hadoop.util.StringUtils;


public class writeable_test {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Text t = new Text("hadoop");
		System.out.println(t.toString());
//		System.out.println(t.getLength());
//		System.out.println((char) t.charAt(2));
//		IntWritable writable = new IntWritable(163);
//		byte[] bytes_1 = serialize(writable);
//		System.out.println(bytes_1.length);
		BytesWritable b = new BytesWritable(new byte[] { 3, 5 ,6});
		System.out.println(StringUtils.byteToHexString(b.getBytes()));
//		byte[] bytes = serialize(b);
//		System.out.println(bytes.length);
		VIntWritable w1 = new VIntWritable(163);
		IntWritable w2 = new IntWritable(163);
//		IntWritable w2 = new IntWritable(63);
//		Comparator c = new Comparator();
//		System.out.println(c.compare(w1,w2));
		byte [] data = serialize(w1);
		byte [] data2 = serialize(w2);
		System.out.println(StringUtils.byteToHexString(data));
		System.out.println(StringUtils.byteToHexString(data2));
		int length = WritableUtils.decodeVIntSize(data[0])+WritableComparator.readVInt(data, 0);
		System.out.println(length);
	}

	public static byte[] serialize(Writable writable) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DataOutputStream dataout = new DataOutputStream(out);
		writable.write(dataout);
		dataout.close();
		return out.toByteArray();
	}

	public static byte[] deserialize(Writable writable, byte[] bytes)
			throws IOException {
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		DataInputStream dataIn = new DataInputStream(in);
		writable.readFields(dataIn);
		dataIn.close();
		return bytes;
	}
}
