package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LoadTest {

	private final int clientsCount = 1000;
	private final int delay = 100;
	@Test
	void run() {
		try {
			for(int i = 0; i < clientsCount;i++) {
				new Thread(()->{
					var webClient = new WebClient();
					while(true) {
						try {
							webClient.get("http:/127.0.0.1:8080/rest/code?country=Russia");
							Thread.sleep(delay);
						} catch (InterruptedException e) {
							//ignored
						}
					}
				}).start();
			}
		}
		catch(Exception ex) {
			fail("Not yet implemented");
		}
	}

}
