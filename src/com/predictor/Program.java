package com.predictor;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

public class Program {
	public static void main(String[] args) throws IOException {
		var countriesUri = "http://country.io/";
		var countriesAddress = "names.json";
		var phonesAddress = "phone.json";
		var dataCacheRefreshTimeout = 10000;	//ms
		ILogger logger = new ConsoleLogger();	
		var dataChache = new DataCache(countriesUri,countriesAddress,phonesAddress,logger);
		new Thread(new DataCacheRefresher(dataChache,dataCacheRefreshTimeout,logger)).start();
		
		var resourceUrl = "/rest/code";
		var matcher = new CodesMatcher(dataChache,logger);
	
		HttpServer server = HttpServer.create();
		server.bind(new InetSocketAddress(8080),0);
		server.createContext(resourceUrl, new HttpCodeHandler(resourceUrl,matcher,logger));
		server.start();
	}
}
