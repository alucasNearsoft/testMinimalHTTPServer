
/*
 * To echo an incoming request’s headers back to the client.
 */
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.Set;
//Compile error: Access restriccion: The type (*all below classes*) is not accessible due to restriction on required library  
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
//Converted to a Maven Project (Properties -> Configure -> Convert to Maven Project)
//And editing the pom.xml file, then added the dependency com.sun.net.httpserver (Add dependencies, searched that package)

class MinimalHTTPServer {
	public static void main(String[] args) throws IOException {
		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
		server.createContext("/echo", new Handler());
		server.start();
	}
}

class Handler implements HttpHandler {
	@Override
	public void handle(HttpExchange xchg) throws IOException {
		Headers headers = xchg.getRequestHeaders(); // returns an immutable map of an HTTP request’s headers.
		Set<Map.Entry<String, List<String>>> entries = headers.entrySet();
		StringBuffer response = new StringBuffer();
		for (Map.Entry<String, List<String>> entry : entries)
			response.append(entry.toString() + "\n");
		xchg.sendResponseHeaders(200, response.length()); // begins to send a response back to the client 
														  // using the current set of response headers and rCode’s
														  // numeric code; 200 indicates success.
		OutputStream os = xchg.getResponseBody(); // returns an output stream to which the response’s body is output.
												  // This method must be called after calling sendResponseHeaders().
		os.write(response.toString().getBytes());
		os.close();
	}
}