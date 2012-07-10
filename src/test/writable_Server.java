package test;

import java.net.*;
import java.io.*;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.util.StringUtils;

public class writable_Server {

	private ServerSocket ss;
	private Socket socket;

	@SuppressWarnings("deprecation")
	public writable_Server() throws IOException {
		try {
			ss = new ServerSocket(10000);

			while (true) {
				socket = ss.accept();
				DataInput data=new DataInputStream(socket.getInputStream());
				Text t = new Text();
				IntWritable i = new IntWritable();
				BytesWritable b = new BytesWritable();
				t.readFields(data);
				b.readFields(data);
				i.readFields(data);

				System.out.println(t.toString());
				System.out.println(StringUtils.byteToHexString(b.getBytes()));
				System.out.println(i.toString());

				socket.close();
			}

		} catch (IOException e) {
		} finally {
			ss.close();
		}
	}

	public static void main(String[] args) throws IOException {
		new writable_Server();
	}
}
