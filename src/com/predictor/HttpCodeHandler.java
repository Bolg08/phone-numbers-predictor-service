package com.predictor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class HttpCodeHandler implements HttpHandler {
	private ILogger logger;
	private String resourceUrl;
	private CodesMatcher matcher;
	
	public HttpCodeHandler(String resourceUrl,CodesMatcher matcher, ILogger logger) {
		this.logger = logger;
		this.resourceUrl = resourceUrl;
		this.matcher = matcher;
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Map<String,String> requestParams = null;
		var method = exchange.getRequestMethod();
		if (method.equals("GET")) {
			logger.log("handle request");
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
		logger.log("query parameter is not correct");
		return null;
	}
	private Map<String,String> handleGetRequest(HttpExchange httpExchange) {
		var requestUri = httpExchange.getRequestURI();
		if (!requestUri.toString().contains(resourceUrl)) {
			logger.log(requestUri + "not served by this handler");
			return null;}	
		var params = queryToMap(requestUri.getQuery());
		return params;

	}

	

	private void handleResponse(HttpExchange httpExchange, String prepare) throws IOException {
		if(prepare==null) {
			httpExchange.sendResponseHeaders(400, 0);
		}
		logger.log("send resonse");
		OutputStream outputStream = httpExchange.getResponseBody();
		String htmlResponse = prepare;
	
		//httpExchange.getResponseHeaders().set("Content-Type", "appication/json");
		httpExchange.sendResponseHeaders(200, htmlResponse.length());
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
