package test;

import java.io.*;
import java.net.*;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

public class writable_client {
	Socket socket;
	BufferedReader rev_in;
	PrintWriter out;

	public writable_client() {
		try {
			socket = new Socket("127.0.0.1", 10000);
			DataOutput data=new DataOutputStream(socket.getOutputStream());
			Text t = new Text("hadoop");			
			IntWritable i = new IntWritable(163);
			BytesWritable b = new BytesWritable(new byte[] { 3, 5 ,6});
			t.write(data);
			b.write(data);
			i.write(data);

			
			socket.close();
		} catch (IOException e) {
		}
	}

	public static void main(String[] args) {
		new writable_client();
	}
}
