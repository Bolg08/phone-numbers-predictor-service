package com.example.demo;


import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

public class DemoApplication {
	static void run(String countriesUri,String countriesAddress,String phonesAddress, int dataCacheRefreshTimeout){
		try{
			var dataChache = new DataCache(countriesUri,countriesAddress,phonesAddress);
			new Thread(new DataCacheRefresher(dataChache,dataCacheRefreshTimeout)).start();
			
			var resourceUrl = "/rest/code";
			var matcher = new CodesMatcher(dataChache);
		 
			HttpServer server = HttpServer.create();
			server.bind(new InetSocketAddress(8080),0);
			server.createContext(resourceUrl, new HandleResult(resourceUrl,matcher));
			server.start();
			LogObject.info("Server starts...");
		}catch(IOException e) {
			LogObject.warn("Program", "run", "Server wasn't starts...");
		}
	}
	public static void main(String[] args) {
		
		var countriesUri = "http://country.io/";
		var countriesAddress = "names.json";
		var phonesAddress = "phone.json";
		var dataCacheRefreshTimeout = 10000;	//ms
		run(countriesUri,countriesAddress,phonesAddress, dataCacheRefreshTimeout);
	}

}
