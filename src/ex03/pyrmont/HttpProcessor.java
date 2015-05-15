package ex03.pyrmont;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.sun.corba.se.spi.transport.SocketInfo;

public class HttpProcessor {
	public HttpProcessor(HttpConnector httpConnector) {
	}

	public void process(Socket socket) {
	    InputStream input = null;
		OutputStream out = null;
		
		try {
			input = socket.getInputStream();
			out = socket.getOutputStream();
			
			 HttpRequest request = new HttpRequest(input);
			 HttpResponse response = new HttpResponse(out);
		} catch (Exception e) {
		}
		
	}
}
