package ex01.pyrmont;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by LCF on 2015/5/13.
 */
public class HttpServer {
    public static final  String WEB_ROOT = System.getProperty("user.dir")+ File.separator + "WebRoot";

    public static  final  String SHUTDOWN_COMMAND ="/SHUTDOWN";

    private boolean shutdown = false;

    public static void main(String[] args) {
        HttpServer server = new HttpServer();
        server.await();
    }
    public void await(){
        ServerSocket serverSocket = null;
        int port = 8888;
        try {
            serverSocket = new ServerSocket(port,1, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        } finally {
        }
        System.out.println("server start at port:" + port + "\t With webroot:" + WEB_ROOT);
        while (!shutdown){
            Socket socket = null;
            InputStream inputStream = null;
            OutputStream outputStream =null;

            try {
                socket = serverSocket.accept();
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();

                Request request = new Request(inputStream);
                request.parse();

                Response response = new Response(outputStream);
                response.setRequest(request);
                response.sendStaticResource();

                socket.close();

                shutdown = request.getUri().equals(SHUTDOWN_COMMAND);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
            }
        }

    }
}
