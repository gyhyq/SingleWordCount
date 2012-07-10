package test;

import java.io.InputStream;
import java.net.URL;

import org.apache.hadoop.fs.FsUrlStreamHandlerFactory;
import org.apache.hadoop.io.IOUtils;

public class URLCat {
	static {  
	    URL.setURLStreamHandlerFactory(new FsUrlStreamHandlerFactory()); 
	  }  
	    
	  public static void main(String[] args) throws Exception {  
	    InputStream in = null;  
	    try {  
		      in = new URL("hdfs://10.28.1.12:9000/user/grid/CHANGES.txt").openStream();  
		      IOUtils.copyBytes(in, System.out, 4096, false);  
		    } finally {  
		      IOUtils.closeStream(in);  
		    }  
		  } 
}
