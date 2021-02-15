package com.example.demo;


public class DataCacheRefresher implements Runnable {

	private int dataSourceRefreshingInterval;
	private DataCache dataCache;
	public DataCacheRefresher(DataCache dataCache,int dataSourceRefreshingInterval) {
		this.dataCache = dataCache;
		this.dataSourceRefreshingInterval = dataSourceRefreshingInterval;
	}
	@Override
	public void run() {
		synchronized(dataCache) {
			while(true) {
				dataCache.refreshDataCache();
				try {
					Thread.sleep(dataSourceRefreshingInterval);
				} 
				catch (InterruptedException e) {
					LogObject.info("DataCacheRefresher", "run", e.getMessage());
				}
			}
		}
	}
}
