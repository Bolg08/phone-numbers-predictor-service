package com.predictor;

public class DataCacheRefresher implements Runnable {

	private ILogger logger;
	private int dataSourceRefreshingInterval;
	private DataCache dataCache;
	public DataCacheRefresher(DataCache dataCache,int dataSourceRefreshingInterval,ILogger logger) {
		this.dataCache = dataCache;
		this.dataSourceRefreshingInterval = dataSourceRefreshingInterval;
		this.logger = logger;
	}
	@Override
	public void run() {
		synchronized(dataCache) {
			while(true) {
			dataCache.refreshDataCache();
			try {
				Thread.sleep(dataSourceRefreshingInterval);
			} catch (InterruptedException e) {
				logger.log(e.getMessage());
			}
			}
		}
	}
}
