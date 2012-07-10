package test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

public class FileCopyWithProgress {
	public static void main(String[] args) throws Exception {  
	    String localSrc = "E:\\fwqbak\\jboss-4.2.3.GA.tar";  
	    String dst = "hdfs://10.28.1.12:9000/user/grid/jboss-4.2.3.GA.tar";  
	    InputStream in = new BufferedInputStream(new FileInputStream(localSrc));  
	      
	   
	    Configuration conf = new Configuration();  

	    FileSystem fs = FileSystem.get(URI.create(dst), conf);  
		    OutputStream out = fs.create(new Path(dst), new Progressable() { 
		      public void progress() {  
		        System.out.print(".");  
		      }  
		    });  
		      
		    IOUtils.copyBytes(in, out, 4096, true);  
		  }  
		 
}
