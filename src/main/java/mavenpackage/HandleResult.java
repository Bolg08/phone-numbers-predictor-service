package mavenpackage;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.net.HttpURLConnection;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.Headers;

public class HandleResult implements HttpHandler {

	private String resourceUrl;
	private CodesMatcher matcher;
	
	public HandleResult(String resourceUrl,CodesMatcher matcher) {
	
		this.resourceUrl = resourceUrl;
		this.matcher = matcher;
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Map<String,String> requestParams = null;
		var requestMethod = exchange.getRequestMethod();
		if (requestMethod.equalsIgnoreCase("GET")) {
			LogObject.info("HttpCodeHandler", "handle", "handle request");
			requestParams = handleGetRequest(exchange);
		}
		//...
		var prepare = prepareResponse(requestParams);	
		handleResponse(exchange, prepare);
	}
	private String prepareResponse(Map<String,String> params) {
		if(params.containsKey("country")) {
			var value = params.get("country");
			return JsonUtils.toJsonString(matcher.getSuitableCodes(value));
		}
		
		LogObject.info("HttpCodeHandler", "prepareResponse", "query parameter is not correct");
		return null;
	}
	private Map<String,String> handleGetRequest(HttpExchange httpExchange) {
		var requestUri = httpExchange.getRequestURI();
		if (!requestUri.toString().contains(resourceUrl)) {
		
			LogObject.info("HttpCodeHandler", "handleGetRequest", requestUri + "not served by this handler");
			return null;}	
		var params = queryToMap(requestUri.getQuery());
		return params;

	}

	private void handleResponse(HttpExchange exchange, String prepare) throws IOException {
		if(prepare==null) {
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
		}
		
		LogObject.info("HttpCodeHandler", "handleResponse", "send resonse");
		Headers responseHeaders = exchange.getResponseHeaders();
		responseHeaders.set("Content-Type", "application/json");
	
		OutputStream outputStream = exchange.getResponseBody();
		String htmlResponse = prepare;
	
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, htmlResponse.length());
		outputStream.write(htmlResponse.getBytes());
		outputStream.flush();
		outputStream.close();
	}
	private Map<String, String> queryToMap(String query) {
		Map<String, String> result = new HashMap<>();
		for (String param : query.split("&")) {
			String[] entry = param.split("=");
			if (entry.length > 1) {
				result.put(entry[0], entry[1]);
			} else {
				result.put(entry[0], "");
			}
		}
		return result;
	}

}
