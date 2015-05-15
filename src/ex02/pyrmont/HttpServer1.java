package ex02.pyrmont;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class HttpServer1 {
	
	private static final String SHUTDOWN_COMMAND ="/SHUTDOWN_COMMAND";
	
	private boolean shutdown = false;
	
	public static void main(String[] args){
		HttpServer1 httpServer1 = new HttpServer1();
		httpServer1.await();
	}

	private void await() {
		ServerSocket serverSocket = null;
		int port = 8888;
		
		try {
			serverSocket = new ServerSocket(port,1,InetAddress.getByName("127.0.0.1"));
			System.out.println("server start at port:" + port + "\t With webroot:" + Constants.WEB_ROOT);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		while(!shutdown){
			Socket socket = null;
			InputStream input = null;
			OutputStream output = null;
			try {
				socket = serverSocket.accept();
				input = socket.getInputStream();
				output = socket.getOutputStream();

				System.out.println("input " + input.available());
				Request request = new Request(input);
				request.parse();
				
				Response response = new Response(output);
				response.setRequest(request);
				
				if(request.getUri().startsWith("/servlet/")){
					System.out.println("servlet StaticResourceProcessor");
					ServletProcessor1 processor = new ServletProcessor1();
					processor.process(request,response);
				}else{
					System.out.println("servlet StaticResourceProcessor");
					StaticResourceProcessor processor = new StaticResourceProcessor();
					processor.process(request,response);
				}
				
				socket.close();
				
				shutdown = request.getUri().equals(SHUTDOWN_COMMAND);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
