package ex02.pyrmont;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

import javax.servlet.Servlet;

public class ServletProcessor2 {

	@SuppressWarnings("rawtypes")
	public void process(Request request, Response response) {
		
		String uri = request.getUri();
		String servletName = uri.substring(uri.lastIndexOf('/') + 1);
		URLClassLoader loader = null;
		try {
			URL[] urls = new URL[1];
			URLStreamHandler streamHandler = null;
			File classPath = new File(Constants.WEB_ROOT);
			
			String repository =
					(new URL("file",null,classPath.getCanonicalPath() + File.separator)).toString();
			urls[0] = new URL(null,repository,streamHandler);
			loader = new URLClassLoader(urls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Class myClass = null;
		try {
			RequestFacade requestFacade = new RequestFacade(request);
			ResponseFacade responseFacade = new ResponseFacade(response);
			myClass = loader.loadClass(servletName);
			Servlet servlet = null;
			servlet = (Servlet) myClass.newInstance();
			servlet.service(requestFacade, responseFacade);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
