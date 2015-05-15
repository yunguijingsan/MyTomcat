package ex01.pyrmont;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;


/**
 * Created by LCF on 2015/5/13.
 */
public class Response {
	private static final int BUFFER_SIZE = 1024;
	private OutputStream output;
	private Request reqeust;

    public void sendStaticResource() {
    	byte[] bytes = new byte[BUFFER_SIZE];
    	FileInputStream fis = null;
    	try {
			File file = new File(HttpServer.WEB_ROOT,reqeust.getUri());
			if(file.exists()){
				fis  = new FileInputStream(file);
				int ch = fis.read(bytes,0,BUFFER_SIZE);
				while(ch!=-1){
					output.write(bytes,0,ch);
					ch = fis.read(bytes,0,BUFFER_SIZE);
				}
			}else{
				String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
						 "Content-Type: text/html\r\n" +
						 "Content-Length: 23\r\n" +
						 "\r\n" +
						 "<h1>File Not Found</h1>";
				output.write(errorMessage.getBytes());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
    }

    public void setRequest(Request request) {
    	this.reqeust = request;
    }
    
    public Response(OutputStream output) {
    	this.output = output;
    }
}
