package ex03.pyrmont;

public class Bootstrap {
	public static void main(String[] args){
		HttpConnector connector = new HttpConnector();
		connector.start();
	}
}
